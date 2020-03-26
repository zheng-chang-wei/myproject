package com.hirain.phm.bd.transform.message;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

import com.hirain.phm.bd.transform.service.MessageFutureCallback;
import com.hirain.phm.bd.transform.topic.TopicGenerator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DefaultMessageProcessor implements IMessageProcessor {

	@Autowired
	@Qualifier("message")
	private KafkaTemplate<String, Object> kafkaTemplate;

	@Autowired
	private TopicGenerator topicGenerator;

	private ExecutorService executor = Executors.newSingleThreadExecutor();

	private BlockingQueue<Entry> queue = new LinkedBlockingQueue<>();

	@Override
	public void push(String topic, byte[] payload) {
		queue.offer(new Entry(topic, payload));
	}

	@Override
	public void process() {
		executor.submit(() -> {
			while (true) {
				try {
					Entry entry = queue.take();
					if (entry == null) {
						continue;
					}
					String topic = topicGenerator.mqtt2KafkaWithMsg(entry.topic);
					String key = topicGenerator.kafkaKey(entry.topic);
					ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topic, key, entry.payload);
					future.addCallback(new MessageFutureCallback(topic, key, entry.payload) {

						@Override
						public void onFailure(Throwable ex) {
							log.error(getTopic() + "," + getKey() + " send fail", ex);
						}

						@Override
						public void onSuccess(SendResult<String, Object> result) {
						}
					});
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	class Entry {

		String topic;

		byte[] payload;

		Entry(String topic, byte[] payload) {
			this.topic = topic;
			this.payload = payload;
		}

	}
}
