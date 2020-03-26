/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.train.communication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirain.phm.bd.common.serialize.JsonUtil;
import com.hirain.phm.bd.ground.kafka.IKafkaProducer;
import com.hirain.phm.bd.ground.train.TrainStatusEunm;
import com.hirain.phm.bd.ground.train.domain.Project;
import com.hirain.phm.bd.ground.train.domain.Train;
import com.hirain.phm.bd.ground.train.param.TrainParam;
import com.hirain.phm.bd.ground.train.service.ProjectService;
import com.hirain.phm.bd.ground.train.service.TrainService;
import com.hirain.phm.bd.ground.util.GroundAccessHelper;
import com.hirain.phm.bd.message.ground.TrainStateMessage;

import lombok.extern.slf4j.Slf4j;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created May 20, 2019 5:17:10 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               May 20, 2019 zepei.tao@hirain.com 1.0 create file
 */
@Service
@Slf4j
public class CommunicationServiceImpl implements CommunicationService {

	@Autowired
	private TrainService trainService;

	@Autowired
	private IKafkaProducer producer;

	@Autowired
	private ProjectService projectService;

	/**
	 * @see com.hirain.phm.bd.ground.communication.service.CommunicationService#stopReceived(com.hirain.phm.bd.ground.train.param.TrainParam)
	 */
	@Override
	public void stopReceived(TrainParam trainParam) {
		TrainStateMessage message = initTrainStateMessage(trainParam);
		message.setState(TrainStatusEunm.DISCONNECT.getStatus());

		updateDatabase(trainParam, TrainStatusEunm.DISCONNECT.getStatus());
		sendCommunicationCommand(message);
		log.info("stop received:" + trainParam);
	}

	/**
	 * @see com.hirain.phm.bd.ground.communication.service.CommunicationService#resumeReceived(com.hirain.phm.bd.ground.train.param.TrainParam)
	 */
	@Override
	public void resumeReceived(TrainParam trainParam) {
		TrainStateMessage message = initTrainStateMessage(trainParam);
		message.setState(TrainStatusEunm.ACTIVATE.getStatus());

		updateDatabase(trainParam, TrainStatusEunm.ACTIVATE.getStatus());
		sendCommunicationCommand(message);
		log.info("resume received:" + trainParam);
	}

	/**
	 * @see com.hirain.phm.bd.ground.communication.service.CommunicationService#logOff(com.hirain.phm.bd.ground.train.param.TrainParam)
	 */
	@Override
	public void logOff(TrainParam trainParam) {
		TrainStateMessage message = initTrainStateMessage(trainParam);
		message.setState(TrainStatusEunm.DEACTIVATE.getStatus());

		updateDatabase(trainParam, TrainStatusEunm.DEACTIVATE.getStatus());
		sendCommunicationCommand(message);
		log.info("log off:" + trainParam);
	}

	private TrainStateMessage initTrainStateMessage(TrainParam trainParam) {
		TrainStateMessage message = new TrainStateMessage();
		Project project = projectService.selectByName(trainParam.getProject());
		message.setCity(project.getCity());
		message.setLine(project.getLine());
		message.setTrain(trainParam.getTrainNo());
		message.setSid(GroundAccessHelper.GT_TRAINSTATUSUPADTE_SID);
		return message;
	}

	/**
	 * 更新数据库中该车的状态信息train_status
	 */
	private void updateDatabase(TrainParam trainParam, int state) {
		int trainID = trainService.select(trainParam.getProject(), trainParam.getTrainNo()).getId();
		Train train = new Train();
		train.setId(trainID);
		train.setTrainStatus((byte) state);
		trainService.updateNotNull(train);
	}

	/**
	 * 向kafka发送控制列车的相应命令报文：开始接收，停止接收，注销车辆
	 */
	private void sendCommunicationCommand(TrainStateMessage message) {
		String key = "response/" + message.getCity() + "/" + message.getLine() + "/" + message.getTrain();
		producer.send(GroundAccessHelper.GT_KAFKA_TOPIC, key, getCommandBytes(message));
	}

	/**
	 * 通信内容json字符串对应的byte数组
	 */
	private byte[] getCommandBytes(TrainStateMessage message) {
		String json = JsonUtil.toString(message);
		return json.getBytes();
	}

}
