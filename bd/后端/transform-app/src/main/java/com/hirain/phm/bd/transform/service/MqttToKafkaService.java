/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.transform.service;

import java.nio.charset.Charset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import com.hirain.phm.bd.common.serialize.JsonUtil;
import com.hirain.phm.bd.message.header.MessageHeader;
import com.hirain.phm.bd.transform.mqtt.MqttProperties;
import com.hirain.phm.bd.transform.mqtt.client.MqttClientManager;
import com.hirain.phm.bd.transform.topic.TopicGenerator;

import lombok.extern.slf4j.Slf4j;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年4月4日 下午2:46:45
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年4月4日 jianwen.xin@hirain.com 1.0 create file
 */
@Component
@Configuration
@Slf4j
public class MqttToKafkaService {

	@Autowired
	private TopicGenerator topicGenerator;

	@Autowired
	private MqttProperties mqttProps;

	@Autowired
	@Qualifier("command")
	private KafkaTemplate<String, Object> kafkaTemplate;

	@Autowired
	private MqttClientManager mqttManager;

	@Value("${kafka.topic.realtime}")
	private String realtimeTopicPreix;

	@Value("${kafka.topic.history}")
	private String historyTopicPrefix;

	@Value("${mqtt.client.id}")
	private String mqttClientId;

	@Async(value = "mqttPool")
	public void transform(String topic, Object message) {
		process(topic, message);
	}


	private void process(String topic, Object message) {
		// kafkaTopic = train-ground
		String kafkaTopic = topicGenerator.mqtt2Kafka(topic);
		MessageHeader header = JsonUtil.fromString(message.toString(), MessageHeader.class);
		String key = topicGenerator.kafkaKey(header);
		ListenableFuture<SendResult<String, Object>> future;
		future = kafkaTemplate.send(kafkaTopic, key, message.toString().getBytes(Charset.forName("utf-8")));
		future.addCallback(new MessageFutureCallback(topic, key, message.toString()) {

			@Override
			public void onFailure(Throwable ex) {
				log.error(getTopic() + "," + getKey() + "," + getMessage() + "send failed", ex);
			}

			@Override
			public void onSuccess(SendResult<String, Object> result) {
				log.info("send:" + result.getProducerRecord().topic());
			}
		});
		if (topic.equals(mqttProps.getPublicTopic())) {
			String clientId = mqttClientId + header.getCity() + "/" + header.getLine() + "/#";
			String realtimeTopic = historyTopicPrefix + header.getCity() + "/" + header.getLine() + "/#";
			String historyTopic = realtimeTopicPreix + header.getCity() + "/" + header.getLine() + "/#";
			mqttManager.createClient(clientId, realtimeTopic, historyTopic);
		}
	}

}
