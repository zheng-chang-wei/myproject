/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.bigdata.service.impl;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import org.apache.hadoop.fs.FsStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirain.phm.bd.data.DataDefinitionService;
import com.hirain.phm.bd.data.bean.DataParam;
import com.hirain.phm.bd.ground.bigdata.HadoopService;
import com.hirain.phm.bd.ground.bigdata.domain.BigSpaceEntity;
import com.hirain.phm.bd.ground.bigdata.param.GroundData;
import com.hirain.phm.bd.ground.bigdata.param.GroundDataParam;
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
	private HadoopService hadoopService;

	@Autowired
	private DataDefinitionService service;

	/**
	 * @see com.hirain.phm.bd.ground.bigdata.service.DataManageService#getSpace()
	 */
	@Override
	public Integer getSpace() {
		BigSpaceEntity entity = getSpaceEntity();
		return (int) (entity.getUsed() * 100 / entity.getCapacity());
	}

	/**
	 * @return
	 */
	private BigSpaceEntity getSpaceEntity() {
		BigSpaceEntity entity;
		FsStatus status = hadoopService.getStatus();
		entity = new BigSpaceEntity();
		entity.setUsed(status.getUsed() / 1024 / 1024);
		entity.setCapacity(status.getCapacity() / 1024 / 1024);
		return entity;
	}

	/**
	 * @see com.hirain.phm.bd.ground.bigdata.service.DataManageService#selectTrainData(com.hirain.phm.bd.ground.bigdata.domain.BigTrainEntity)
	 */
	@Override
	public List<GroundData> selectTrainData(GroundDataParam param) {
		DataParam dataParam = new DataParam();
		dataParam.setProject(param.getProject());
		dataParam.setTrain(param.getTrain());
		String[] range = service.selectRange(dataParam);
		GroundData data = new GroundData();
		data.setProject(param.getProject());
		data.setTrain(param.getTrain());
		data.setStartDay(range[0]);
		data.setEndDay(range[1]);
		return Arrays.asList(data);
	}

	@Override
	public String delete(DataParam param) throws Exception {
		service.delete(param);
		return "删除成功";
	}

}
