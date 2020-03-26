/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.bigdata.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.hirain.phm.bd.ground.bigdata.service.BigDataService;
import com.hirain.phm.bd.ground.bigdata.service.BigProjectService;
import com.hirain.phm.bd.ground.bigdata.service.BigSpaceService;
import com.hirain.phm.bd.ground.bigdata.service.BigTrainService;
import com.hirain.phm.bd.ground.train.controller.TrainGateWay;
import com.hirain.phm.bd.ground.train.domain.Project;
import com.hirain.phm.bd.ground.train.domain.Train;
import com.hirain.phm.bd.message.big.DataRecord;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年6月5日 下午5:32:15
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年6月5日 jianwen.xin@hirain.com 1.0 create file
 */
@Service
public class BigDataServiceImpl implements BigDataService {

	static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

	@Autowired
	private TrainGateWay trainGateWay;

	@Autowired
	private BigTrainService bigTrainService;

	@Autowired
	private HadoopService hadoopService;

	@Autowired
	private BigSpaceService bigService;

	@Value("${hdfs.root}")
	private String hdfsRoot;

	@Autowired
	private BigProjectService bigProjectService;

	@Override
	public void insertDataRecord(List<DataRecord> records) {
		for (DataRecord record : records) {
			try {
				Train train = trainGateWay.selectTrain(record.getProject(), record.getTrain());
				BigTrainEntity bigTrainEntity = bigTrainService.selectByTrainId(train.getId());
				Date date = sdf.parse(record.getDate());
				if (bigTrainEntity == null) {
					bigTrainEntity = new BigTrainEntity();
					bigTrainEntity.setTrainId(train.getId());
					bigTrainEntity.setStartDay(date);
					bigTrainEntity.setEndDay(date);
					bigTrainService.save(bigTrainEntity);
				} else {
					if (date.compareTo(bigTrainEntity.getStartDay()) < 0) {
						bigTrainEntity.setStartDay(date);
					} else if (date.compareTo(bigTrainEntity.getEndDay()) > 0) {
						bigTrainEntity.setEndDay(date);
					}
					bigTrainService.updateAll(bigTrainEntity);
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @see com.hirain.phm.bd.ground.bigdata.service.BigDataService#updateStorageInfo()
	 */
	@Override
	public void updateStorageInfo() {
		updateBigDataSpace();
		updateProjectDataSpace();
	}

	/**
	 * 
	 */
	private void updateProjectDataSpace() {
		List<Project> projects = trainGateWay.selectProjects();
		for (Project project : projects) {
			String path = hdfsRoot + "/" + project.getName();
			long capacity = hadoopService.getCapacity(path);
			if (capacity == -1) {
				continue;
			}
			BigProjectEntity entity = new BigProjectEntity();
			entity.setUsed(capacity);
			entity.setProject(project.getName());
			BigProjectEntity old = bigProjectService.selectByKey(project.getName());
			if (old != null) {
				bigProjectService.updateAll(entity);
			} else {
				bigProjectService.save(entity);
			}
		}
	}

	private void updateBigDataSpace() {
		FsStatus status = hadoopService.getStatus();
		BigSpaceEntity entity = new BigSpaceEntity();
		entity.setUsed(status.getUsed() / 1024 / 1024);
		entity.setCapacity(status.getCapacity() / 1024 / 1024);
		List<BigSpaceEntity> all = bigService.selectAll();
		if (all.size() > 0) {
			entity.setId(all.get(0).getId());
			bigService.updateAll(entity);
		} else {
			bigService.save(entity);
		}
	}

}
