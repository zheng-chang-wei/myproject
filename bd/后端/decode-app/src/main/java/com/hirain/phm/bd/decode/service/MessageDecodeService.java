/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.decode.service;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import javax.annotation.PostConstruct;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hirain.phm.bd.common.partition.IPartitioner;
import com.hirain.phm.bd.decode.message.MessageDecodeProcessor;

import lombok.extern.slf4j.Slf4j;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年4月19日 下午2:56:19
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年4月19日 jianwen.xin@hirain.com 1.0 create file
 */
@Component
@Scope("prototype")
@Slf4j
@Configuration
public class MessageDecodeService {

	@Value("${kafka.producer.topic.prefix}")
	private String topicPrefix;

	@Value("${message.decode.processor.size}")
	private int processorSize;

	@Autowired
	private IPartitioner partitioner;

	private ExecutorService executor;

	private BlockingQueue<ConsumerRecord<String, Object>> queue = new LinkedBlockingQueue<>();

	private MessageDecodeProcessor[] processors;

	@PostConstruct
	public void init() {
		executor = Executors.newSingleThreadExecutor(r -> new Thread(r, "decode service"));
		processors = new MessageDecodeProcessor[processorSize];
		process();
	}

	public void push(List<ConsumerRecord<String, Object>> records) {
		// queue.addAll(records);
		for (ConsumerRecord<String, Object> record : records) {
			try {
				queue.put(record);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 */
	private void process() {
		executor.submit(() -> {
			while (true) {
				try {
					ConsumerRecord<String, Object> record = queue.take();
					String key = record.topic() + "-" + record.key();
					System.out.println(record.topic());
					int partition = partitioner.partition(key, processorSize);
					MessageDecodeProcessor processor = processors[partition];
					if (processor == null) {
						processor = processor();
						processors[partition] = processor;
					}
					processor.push(record);
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
			}
		});
	}

	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public MessageDecodeProcessor processor() {
		return new MessageDecodeProcessor(topicPrefix);
	}

}
