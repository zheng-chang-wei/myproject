/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.transform.service;

import java.nio.charset.Charset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.hirain.phm.bd.common.serialize.JsonUtil;
import com.hirain.phm.bd.message.header.MessageHeader;
import com.hirain.phm.bd.transform.mqtt.MqttEncryptGateWay;
import com.hirain.phm.bd.transform.mqtt.MqttPublicGateway;
import com.hirain.phm.bd.transform.topic.TopicGenerator;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年4月4日 下午2:41:53
 * @Description
 *              <p>
 *              解析消息，获取车辆信息
 *              <p>
 *              调用TopicGenerator获得Mqtt主题，发布出去
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年4月4日 jianwen.xin@hirain.com 1.0 create file
 */
@Component
public class KafkaToMqttService {

	@Autowired
	private TopicGenerator generator;

	@Autowired
	private MqttPublicGateway mqttGateway;

	@Autowired
	private MqttEncryptGateWay encryptGateWay;

	@Async(value = "kafkaPool")
	public void transform(Object message, String key) {
		process(message, key);
	}


	private void process(Object message, String key) {
		String value;
		value = new String((byte[]) message, Charset.forName("utf-8"));
		MessageHeader header = JsonUtil.fromString(value, MessageHeader.class);
		String mqttTopic = key;
		if (mqttTopic == null) {
			mqttTopic = generator.kafka2Mqtt(header);
		}
		if (header.getSid() == 0x11) {
			mqttGateway.sendToMqtt(value, mqttTopic);
		} else {
			encryptGateWay.sendToMqtt(value, mqttTopic);
		}
	}
}
