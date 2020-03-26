/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
/**
 * @Version 1.0   
 * @Author weijia.kong@hirain.com
 * @Created Jun 6, 2019 4:09:30 PM
 * @Description
 * <p>
 * @Modification
 * <p>Date         Author      Version     Description  
 * <p>Jun 6, 2019     weijia.kong@hirain.com            1.0        create file 
 */
package com.hirain.phm.bd.ground.train.service.impl;

import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirain.phm.bd.common.serialize.JsonUtil;
import com.hirain.phm.bd.ground.common.event.TrainOnlineEvent;
import com.hirain.phm.bd.ground.common.service.BaseService;
import com.hirain.phm.bd.ground.kafka.IKafkaProducer;
import com.hirain.phm.bd.ground.train.dao.TrainStorageInfoMapper;
import com.hirain.phm.bd.ground.train.domain.Project;
import com.hirain.phm.bd.ground.train.domain.Train;
import com.hirain.phm.bd.ground.train.domain.TrainStorageInfo;
import com.hirain.phm.bd.ground.train.param.DeleteDataParam;
import com.hirain.phm.bd.ground.train.param.TrainDMResponse;
import com.hirain.phm.bd.ground.train.param.TrainParamHeader;
import com.hirain.phm.bd.ground.train.service.ProjectService;
import com.hirain.phm.bd.ground.train.service.TrainService;
import com.hirain.phm.bd.ground.train.service.TrainStorageInfoService;
import com.hirain.phm.bd.ground.util.GroundAccessHelper;
import com.hirain.phm.bd.message.ground.DeleteTrainDataMessage;
import com.hirain.phm.bd.message.train.StorageMessage;

@Service
public class TrainStorageInfoServiceImpl extends BaseService<TrainStorageInfo> implements TrainStorageInfoService {

	@Autowired
	private TrainService trainService;

	@Autowired
	private TrainStorageInfoMapper mapper;

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	@Autowired
	private IKafkaProducer kafkaProducer;

	private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

	@Autowired
	private ProjectService projectService;;

	/*
	 * (non-Javadoc)
	 * @see
	 * com.hirain.phm.bd.ground.train.datamanagement.service.TrainStorageInfoService
	 * #handleStorageMessage(com.hirain.phm.bd.message.train.StorageMessage)
	 */
	@Override
	public void handleStorageMessage(StorageMessage packet) {
		TrainStorageInfo trainStorageInfo = new TrainStorageInfo();
		double storageRatio = packet.getUsed() / packet.getTotal();
		double faultStorageRatio = packet.getFaultSpace() / packet.getTotal();
		int trainId = trainService.select(packet.getProject(), packet.getTrain()).getId();
		trainStorageInfo.setTrainId(trainId);
		trainStorageInfo.setStorageRatio((int) (storageRatio * 100));
		trainStorageInfo.setFaultStorageRatio((int) (faultStorageRatio * 100));
		try {
			trainStorageInfo.setStartDate(sdf.parse(packet.getStartDate()));
			trainStorageInfo.setEndDate(sdf.parse(packet.getEndDate()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		TrainStorageInfo info = selectByKey(trainId);
		if (null == info) {
			save(trainStorageInfo);
		} else {
			if (info.getDeleteDate() != null && trainStorageInfo.getStartDate().compareTo(info.getDeleteDate()) < 0) {
				trainStorageInfo.setDeleteDate(info.getDeleteDate());
			}
			updateAll(trainStorageInfo);
		}
		System.out.println(trainStorageInfo);
	}

	@Override
	public List<TrainDMResponse> findByTrainParam(TrainParamHeader trainParam) {
		return mapper.findByTrainParam(trainParam);
	}

	@Override
	public String deleteTrainData(DeleteDataParam param) {
		TrainParamHeader[] params = param.getTrainParams();
		Date deleteDate = param.getDate();
		for (TrainParamHeader train : params) {
			Train trainObject = trainService.select(train.getProject(), train.getTrainNo());
			TrainStorageInfo info = selectByKey(trainObject.getId());
			Date startDate = info.getStartDate();
			Date endDate = info.getEndDate();
			if (deleteDate.compareTo(startDate) < 0) {
				deleteDate = startDate;
			} else if (deleteDate.compareTo(endDate) > 0) {
				deleteDate = endDate;
			}
			info.setDeleteDate(deleteDate);
			updateNotNull(info);
			sendToTrain(train, deleteDate);
		}
		return "删除结果将在车辆下一次上线时生效";
	}

	/**
	 * 发送删除指令给车载端
	 * 
	 * @param train
	 * @param deleteDate
	 */
	private void sendToTrain(TrainParamHeader train, Date deleteDate) {
		DeleteTrainDataMessage message = new DeleteTrainDataMessage();
		Project project = projectService.selectByName(train.getProject());
		message.setCity(project.getCity());
		message.setLine(project.getLine());
		message.setProject(train.getProject());
		message.setTrain(train.getTrainNo());
		message.setSid(GroundAccessHelper.GT_DELETE_SID);
		message.setDeleteDate(deleteDate);
		String json = JsonUtil.toString(message);
		String key = "response/" + message.getCity() + "/" + message.getLine() + "/" + message.getTrain();
		kafkaProducer.send(GroundAccessHelper.GT_KAFKA_TOPIC, key, json.getBytes(Charset.forName("utf-8")));
	}

	@Override
	public void updateTrainInfo(TrainOnlineEvent message) {
		executor.schedule(() -> {
			Integer trainId = trainService.select(message.getProject(), message.getTrain()).getId();
			TrainStorageInfo storageInfo = selectByKey(trainId);
			if (storageInfo.getDeleteDate() != null) {
				Date deleteDate = storageInfo.getDeleteDate();
				Date startDate = storageInfo.getStartDate();
				if (deleteDate.compareTo(startDate) > 0) {
					DeleteTrainDataMessage deleteMessage = new DeleteTrainDataMessage();
					deleteMessage.setCity(message.getCity());
					deleteMessage.setLine(message.getLine());
					deleteMessage.setTrain(message.getTrain());
					deleteMessage.setDeleteDate(deleteDate);
					deleteMessage.setSid(GroundAccessHelper.GT_DELETE_SID);
					String json = JsonUtil.toString(deleteMessage);
					String key = "response/" + message.getCity() + "/" + message.getLine() + "/" + message.getTrain();
					kafkaProducer.send(GroundAccessHelper.GT_KAFKA_TOPIC, key, json.getBytes(Charset.forName("utf-8")));
				}
			}
		}, 60, TimeUnit.SECONDS);
	}
}
