/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.bigdata.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.hadoop.fs.FsStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hirain.phm.bd.ground.bigdata.HadoopService;
import com.hirain.phm.bd.ground.bigdata.domain.BigProjectEntity;
import com.hirain.phm.bd.ground.bigdata.domain.BigSpaceEntity;
import com.hirain.phm.bd.ground.bigdata.domain.BigTrainEntity;
import com.hirain.phm.bd.ground.bigdata.param.DeleteDataParam;
import com.hirain.phm.bd.ground.bigdata.param.GroundData;
import com.hirain.phm.bd.ground.bigdata.param.GroundDataParam;
import com.hirain.phm.bd.ground.bigdata.service.BigProjectService;
import com.hirain.phm.bd.ground.bigdata.service.BigSpaceService;
import com.hirain.phm.bd.ground.bigdata.service.BigTrainService;
import com.hirain.phm.bd.ground.bigdata.service.DataManageService;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年6月6日 下午1:58:00
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年6月6日 jianwen.xin@hirain.com 1.0 create file
 */
@Service
public class DataManageServiceImpl implements DataManageService {

	static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

	@Autowired
	private BigSpaceService spaceService;

	@Autowired
	private HadoopService hadoopService;

	@Autowired
	private BigTrainService trainService;

	@Value("${hdfs.root}")
	private String rootPath;

	@Value("${hdfs.source.root}")
	private String sourcePath;

	@Autowired
	private BigProjectService projectService;

	/**
	 * @see com.hirain.phm.bd.ground.bigdata.service.DataManageService#getSpace()
	 */
	@Override
	public Integer getSpace() {
		BigSpaceEntity entity = getSpaceEntity();
		return (int) (entity.getUsed() * 100 / entity.getCapacity());
	}

	/**
	 * @see com.hirain.phm.bd.ground.bigdata.service.DataManageService#getProjectSpace()
	 */
	@Override
	public List<BigProjectEntity> getProjectSpace() {
		Long used = getSpaceEntity().getUsed();
		List<BigProjectEntity> list = projectService.selectAll();
		list.forEach(t -> {
			Long v = t.getUsed();
			if (used != 0) {
				t.setUsed(v * 100 / used);
			} else {
				t.setUsed(0L);
			}
		});
		return list;
	}

	/**
	 * @return
	 */
	private BigSpaceEntity getSpaceEntity() {
		List<BigSpaceEntity> list = spaceService.selectAll();
		BigSpaceEntity entity;
		if (list == null || list.isEmpty()) {
			FsStatus status = hadoopService.getStatus();
			entity = new BigSpaceEntity();
			entity.setUsed(status.getUsed() / 1024 / 1024);
			entity.setCapacity(status.getCapacity() / 1024 / 1024);
			spaceService.save(entity);
		} else {
			entity = list.get(0);
		}
		return entity;
	}

	/**
	 * @see com.hirain.phm.bd.ground.bigdata.service.DataManageService#selectTrainData(com.hirain.phm.bd.ground.bigdata.domain.BigTrainEntity)
	 */
	@Override
	public List<GroundData> selectTrainData(GroundDataParam param) {
		return trainService.findBigTrainByParam(param);
	}

	@Override
	public String delete(DeleteDataParam param) throws Exception {
		Date date = param.getDate();
		for (GroundDataParam groundData : param.getParams()) {
			List<GroundData> datas = trainService.findBigTrainByParam(groundData);
			if (datas != null && !datas.isEmpty()) {
				GroundData data = datas.get(0);
				if (inRange(date, data)) {
					String path = generatePath(data);
					String foler = rootPath + "/" + path;
					String sourceFolder = sourcePath + "/" + path;
					try {
						deleteGroundDatas(foler, sourceFolder, data.getStartDay(), date);
						BigTrainEntity entity = new BigTrainEntity();
						entity.setId(data.getId());
						entity.setStartDay(date);
						trainService.updateNotNull(entity);
					} catch (Exception e) {
						throw new Exception("删除失败", e);
					}
				}
			}
		}
		return "删除成功";
	}

	private Date deleteGroundDatas(String path, String sourcePath, Date start, Date end) throws Exception {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(start);
		while (calendar.getTime().compareTo(end) < 0) {
			String folder = sdf.format(calendar.getTime());
			hadoopService.deleteFile(path + "/" + folder, true);
			hadoopService.deleteFile(sourcePath + "/" + folder, true);
			calendar.add(Calendar.DAY_OF_MONTH, 1);
		}
		return calendar.getTime();
	}

	private String generatePath(GroundData data) {
		StringBuilder sb = new StringBuilder();
		sb.append(data.getProject());
		sb.append("/");
		sb.append(data.getTrain());
		return sb.toString();
	}

	private boolean inRange(Date date, GroundData data) {
		return date.compareTo(data.getStartDay()) >= 0 && date.compareTo(data.getEndDay()) <= 0;
	}

}
