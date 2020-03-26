/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.store.kafka.consumer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.AbstractMessageListenerContainer;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.hirain.phm.bd.common.pinyin.PinyinUtil;
import com.hirain.phm.bd.message.header.MessageHeader;
import com.hirain.phm.bd.store.kafka.KafkaConsumerProperties;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年5月17日 下午4:49:35
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年5月17日 jianwen.xin@hirain.com 1.0 create file
 */
@Configuration
@Component
public class ConsumerManager {

	@Autowired
	private KafkaConsumerProperties properties;

	@Autowired
	private ConsumerFactory<String, Object> consumerFactory;

	@Value("${kafka.original.topic.prefix}")
	private String topicPrefix;

	private Map<String, AbstractMessageListenerContainer<?, ?>> kafkaContainerMap = new ConcurrentHashMap<>();

	@EventListener
	@Async
	public void onRegisterEvent(MessageHeader header) {
		createAndStart(header);
	}

	public void createAndStart(MessageHeader header) {
		String pinyin = PinyinUtil.getFullSpell(header.getCity());
		String topic = topicPrefix + pinyin + "-" + header.getLine();
		System.err.println(topic);
		if (kafkaContainerMap.keySet().contains(topic)) {
			return;
		}
		String groupId = properties.getGroupPrefix() + pinyin + "-" + header.getLine();
		AbstractMessageListenerContainer<String, Object> container = kafkaContainer(topic, groupId);
		kafkaContainerMap.put(topic, container);
		System.err.println("groupId:" + groupId + ",topic:" + topic + " start");
		container.start();
	}

	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public AbstractMessageListenerContainer<String, Object> kafkaContainer(String topic, String groupId) {
		ContainerProperties containerProperties = new ContainerProperties(topic);
		containerProperties.setGroupId(properties.getGroupPrefix() + groupId);
		containerProperties.setMessageListener(listener());
		ConcurrentMessageListenerContainer<String, Object> kafkaContainer = new ConcurrentMessageListenerContainer<>(consumerFactory,
				containerProperties);
		kafkaContainer.setConcurrency(properties.getConcurrency());
		return kafkaContainer;
	}

	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public Object listener() {
		return new MessageListener();
	}

}
