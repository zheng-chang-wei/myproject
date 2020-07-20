/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.transform.mqtt.client;

import java.nio.charset.Charset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.SmartLifecycle;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import com.hirain.phm.bd.common.serialize.JsonUtil;
import com.hirain.phm.bd.message.train.RegisterMessage;
import com.hirain.phm.bd.transform.kafka.KafkaTopicProperties;
import com.hirain.phm.bd.transform.mqtt.MqttProperties;
import com.hirain.phm.bd.transform.service.MessageFutureCallback;
import com.hirain.phm.bd.transform.topic.TopicGenerator;

import lombok.extern.slf4j.Slf4j;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jun 17, 2020 2:38:06 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jun 17, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Component
@Slf4j
public class MqttClientStarter implements SmartLifecycle {

	@Autowired
	private MqttClientManager mqttManager;

	@Autowired
	private KafkaTopicProperties topicProps;

	private MqttProperties mqttProperties;

	@Autowired
	@Qualifier("command")
	private KafkaTemplate<String, Object> kafkaTemplate;

	@Autowired
	private TopicGenerator topicGenerator;

	@Value("${mqtt.ssl.topic}")
	public String kafkaTopic;

	/**
	 * @see org.springframework.context.Lifecycle#start()
	 */
	@Override
	public void start() {
		log.info("mqtt client starter");
		String project = "深圳/7/#";
		mqttManager.createClient(mqttProperties.getClientId() + project, topicProps.getRealtime() + project, topicProps.getHistory() + project);
		log.info("create client:" + project);
		RegisterMessage header = new RegisterMessage();
		header.setCity("深圳");
		header.setLine("7");
		header.setProject("深圳7号线一期");
		header.setTrain("717");
		header.setSid(0x01);
		header.setMac1("52-54-00-ea-36-a9");
		header.setMac2("ff-ff-ff-ff-ff-ff");
		header.setSsl(false);
		header.setState(1);
		byte[] bs = JsonUtil.toString(header).getBytes(Charset.forName("utf-8"));
		String key = topicGenerator.kafkaKey(header);
		ListenableFuture<SendResult<String, Object>> future;
		future = kafkaTemplate.send(kafkaTopic, key, bs);
		future.addCallback(new MessageFutureCallback(kafkaTopic, key, JsonUtil.toString(header)) {

			@Override
			public void onFailure(Throwable ex) {
				log.error(getTopic() + "," + getKey() + "," + getMessage() + "send failed", ex);
			}

			@Override
			public void onSuccess(SendResult<String, Object> result) {
				log.info("send:" + result.getProducerRecord().topic());
			}
		});
	}

	/**
	 * @see org.springframework.context.Lifecycle#stop()
	 */
	@Override
	public void stop() {

	}

	/**
	 * @see org.springframework.context.Lifecycle#isRunning()
	 */
	@Override
	public boolean isRunning() {
		return false;
	}

}
