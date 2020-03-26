/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.train;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.hirain.phm.bd.common.serialize.JsonUtil;
import com.hirain.phm.bd.ground.common.event.TrainInitEvent;
import com.hirain.phm.bd.ground.common.event.TrainOnlineEvent;
import com.hirain.phm.bd.ground.kafka.IKafkaProducer;
import com.hirain.phm.bd.ground.train.domain.Project;
import com.hirain.phm.bd.ground.train.domain.Train;
import com.hirain.phm.bd.ground.train.service.ProjectService;
import com.hirain.phm.bd.ground.train.service.TrainService;
import com.hirain.phm.bd.ground.util.GroundAccessHelper;
import com.hirain.phm.bd.message.ground.RegisterResponse;
import com.hirain.phm.bd.message.header.MessageHeader;
import com.hirain.phm.bd.message.train.RegisterMessage;

import lombok.extern.slf4j.Slf4j;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Apr 25, 2019 5:54:12 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Apr 25, 2019 zepei.tao@hirain.com 1.0 create file
 */
@Component
@Slf4j
public class RegisterHandlerImpl implements IRegisterHandler {

	@Autowired
	private TrainService trainService;

	@Autowired
	private ProjectService projectService;

	@Autowired
	private IKafkaProducer producer;

	@Autowired
	private ApplicationEventPublisher publisher;

	@Value("${mqtt.ssl.file.cert}")
	private String caCert;

	@Value("${mqtt.ssl.file.client}")
	private String clientCert;

	@Value("${mqtt.ssl.file.key}")
	private String key;

	@Value("${mqtt.ssl.password}")
	private String password;

	private class VerifyResult {

		boolean verifyResult;

		String error;

		public boolean isVerifyResult() {
			return verifyResult;
		}

		public void setVerifyResult(boolean verifyResult) {
			this.verifyResult = verifyResult;
		}

		public String getError() {
			return error;
		}

		public void setError(String error) {
			this.error = error;
		}

	}

	/**
	 * @see com.hirain.phm.bd.ground.train.IRegisterHandler#handle(com.hirain.phm.bd.message.train.RegisterMessage)
	 */
	@Override
	public void handle(RegisterMessage message) {
		VerifyResult result = verify(message, insertAndGet(message));
		RegisterResponse response = getResponse(message, result);
		sendResponse(message, response);
		if (result.isVerifyResult()) {
			notifyTrainOnline(message);
		}
	}

	private Train insertAndGet(RegisterMessage message) {
		String trainNo = message.getTrain();

		Project project = projectService.selectByName(message.getProject());
		if (project == null) {
			project = insertProject(message);
		}
		Train train = trainService.select(message.getProject(), trainNo);
		if (train == null) {// 未找到该列车，说明此列车没有被注册过
			train = insertTrain(message, project);
			notifyTrainInit(message);
		}
		return train;
	}

	private VerifyResult verify(RegisterMessage message, Train train) {
		VerifyResult result = new VerifyResult();
		String mac1 = message.getMac1();
		String mac2 = message.getMac2();
		result.setVerifyResult(true);
		if (mac1.equals(train.getMac1()) && mac2.equals(train.getMac2())) {// 比较两个mac地址，若一致，则注册成功
			result.setVerifyResult(true);
		} else {
			result.setVerifyResult(false);
			result.setError("MAC地址不一致");
		}
		return result;
	}

	private RegisterResponse getResponse(RegisterMessage message, VerifyResult result) {
		RegisterResponse response = new RegisterResponse();
		response.setSid(GroundAccessHelper.GT_REGISTER_SID);
		response.setSuccess(result.isVerifyResult());
		response.setError(result.getError());
		if (message.isSsl()) {
			setSSLFile(response);
			response.setPassword(password);
		}
		Train train = trainService.select(message.getProject(), message.getTrain());
		response.setState(train.getTrainStatus());
		response.setOffset(train.getOffset() == null ? 0 : train.getOffset());

		response.setProject(message.getProject());
		response.setCity(message.getCity());
		response.setLine(message.getLine());
		response.setTrain(message.getTrain());

		return response;
	}

	/**
	 * @param reponse
	 * @see com.hirain.phm.bd.ground.train.IRegisterHandler#replyRegister()
	 */
	private void sendResponse(RegisterMessage message, RegisterResponse response) {
		byte[] bs = JsonUtil.toString(response).getBytes(Charset.forName("utf-8"));
		producer.send(GroundAccessHelper.GT_KAFKA_TOPIC, getKafkaKey(message), bs);
	}

	private String getKafkaKey(RegisterMessage message) {
		return "ca/" + message.getCity() + "/" + message.getLine() + "/" + message.getTrain();
	}

	private Project insertProject(MessageHeader header) {
		Project project = new Project();
		project.setName(header.getProject());
		project.setCity(header.getCity());
		project.setLine(header.getLine());
		projectService.save(project);
		return project;
	}

	private Train insertTrain(RegisterMessage message, Project project) {
		Train train = new Train();
		train.setTrainNo(message.getTrain());
		train.setMac1(message.getMac1());
		train.setMac2(message.getMac2());
		train.setTrainStatus((byte) message.getState());
		train.setProjectId(project.getId());
		trainService.insertTrain(train);
		return train;
	}

	private void setSSLFile(RegisterResponse response) {
		try {
			response.setCaCert(FileUtils.readFileToString(new File(caCert), Charset.defaultCharset()));
			response.setCert(FileUtils.readFileToString(new File(clientCert), Charset.defaultCharset()));
			response.setKey(FileUtils.readFileToString(new File(key), Charset.defaultCharset()));
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}

	private void notifyTrainOnline(RegisterMessage message) {
		TrainOnlineEvent event = new TrainOnlineEvent();
		event.setProject(message.getProject());
		event.setCity(message.getCity());
		event.setLine(message.getLine());
		event.setTrain(message.getTrain());
		publisher.publishEvent(event);
	}

	private void notifyTrainInit(RegisterMessage message) {
		TrainInitEvent event = new TrainInitEvent();
		event.setProject(message.getProject());
		event.setTrain(message.getTrain());
		publisher.publishEvent(event);
	}

}
