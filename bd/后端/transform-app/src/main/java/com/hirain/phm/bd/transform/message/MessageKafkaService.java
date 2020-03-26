/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.transform.message;

import java.nio.charset.Charset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.concurrent.ListenableFuture;

import com.hirain.phm.bd.common.serialize.JsonUtil;
import com.hirain.phm.bd.message.TrainPacket;
import com.hirain.phm.bd.transform.service.MessageFutureCallback;
import com.hirain.phm.bd.transform.topic.TopicGenerator;

import lombok.extern.slf4j.Slf4j;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年4月9日 下午3:16:01
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年4月9日 jianwen.xin@hirain.com 1.0 create file
 */
@Configuration
@Slf4j
public class MessageKafkaService {

	@Autowired
	@Qualifier("message")
	private KafkaTemplate<String, Object> kafkaTemplate;

	@Autowired
	private TopicGenerator topicGenerator;

	@EventListener
	@Async("kafkaPool")
	public void handMessageEvent(MessageEvent event) {
		TrainPacket packet = event.getPacket();
		String topic = topicGenerator.mqtt2KafkaWithMsg(event.getTopic());
		String key = topicGenerator.kafkaKey(event.getTopic());
		String json = JsonUtil.toString(packet);
		ListenableFuture<SendResult<String, Object>> future;
		future = kafkaTemplate.send(topic, key, json.getBytes(Charset.forName("utf-8")));
		future.addCallback(new MessageFutureCallback(topic, key, json) {

			@Override
			public void onFailure(Throwable ex) {
				log.error(getTopic() + "," + getKey() + "," + getMessage() + "send failed", ex);
				// TODO handle failure event
			}

			@Override
			public void onSuccess(SendResult<String, Object> result) {
			}
		});
	}
}
