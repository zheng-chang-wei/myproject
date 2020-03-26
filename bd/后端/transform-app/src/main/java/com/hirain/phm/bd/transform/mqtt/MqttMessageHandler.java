/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.transform.mqtt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

import com.hirain.phm.bd.transform.service.MqttToKafkaService;

import lombok.extern.slf4j.Slf4j;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年4月4日 下午1:55:06
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年4月4日 jianwen.xin@hirain.com 1.0 create file
 */
@Slf4j
public class MqttMessageHandler implements MessageHandler {

	@Autowired
	private MqttToKafkaService service;

	/**
	 * @see org.springframework.messaging.MessageHandler#handleMessage(org.springframework.messaging.Message)
	 */
	@Override
	public void handleMessage(Message<?> message) throws MessagingException {
		String topic = message.getHeaders().get("mqtt_receivedTopic").toString();
		log.info("topic:{},message:{}", topic, message.getPayload().toString());
		service.transform(topic, message.getPayload());
	}

}
