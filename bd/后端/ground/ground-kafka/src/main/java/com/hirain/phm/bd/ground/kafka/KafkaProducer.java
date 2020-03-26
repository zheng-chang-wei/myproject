package com.hirain.phm.bd.ground.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import lombok.extern.slf4j.Slf4j;

@Component
@Profile({ "local" })
@Slf4j
public class KafkaProducer implements IKafkaProducer {

	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;

	@Override
	public void send(String topic, String key, byte[] payload) {
		ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topic, key, payload);
		future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {

			@Override
			public void onSuccess(SendResult<String, Object> result) {
			}

			@Override
			public void onFailure(Throwable ex) {
				log.error(ex.getMessage(), ex);
			}
		});
	}

}
