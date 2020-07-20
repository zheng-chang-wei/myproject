/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.kafka;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
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
import com.hirain.phm.bd.ground.common.kafka.KafkaConsumerManager;
import com.hirain.phm.bd.message.header.MessageHeader;
import com.hirain.phm.bd.message.train.RegisterMessage;

import lombok.Getter;
import lombok.Setter;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年5月13日 上午9:17:28
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年5月13日 jianwen.xin@hirain.com 1.0 create file
 */
@Component
@Configuration
@ConfigurationProperties("kafka")
public class ConsumerManager implements KafkaConsumerManager {

	@Autowired
	private KafkaConsumerProperties properties;

	@Autowired
	private ConsumerFactory<String, Object> consumerFactory;

	@Getter
	@Setter
	private String topicPrefix;

	private Map<String, AbstractMessageListenerContainer<?, ?>> kafkaContainerMap = new ConcurrentHashMap<>();

	@EventListener
	@Async
	public void onRegister(RegisterMessage message) {
		synchronized (this) {
			createAndStart(message);
		}
	}

	public void createAndStart(MessageHeader header) {
		createAndStart(header.getCity(), header.getLine());
	}

	@Override
	public void createAndStart(String city, String line) {
		String pingYin = PinyinUtil.getFullSpell(city);
		String topic = topicPrefix + pingYin + "-" + line;
		if (kafkaContainerMap.keySet().contains(topic)) {
			return;
		}
		String groupId = properties.getGroupPrefix() + pingYin + "-" + line;
		AbstractMessageListenerContainer<String, Object> container = kafkaContanier(topic, groupId);
		kafkaContainerMap.put(topic, container);
		container.start();
		System.out.println(groupId + " start");
	}

	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public AbstractMessageListenerContainer<String, Object> kafkaContanier(String topic, String groupId) {
		ContainerProperties containerProperties = new ContainerProperties(topic);
		containerProperties.setGroupId(properties.getGroupPrefix() + groupId);
		containerProperties.setMessageListener(listener());
		ConcurrentMessageListenerContainer<String, Object> kafkaContainer = new ConcurrentMessageListenerContainer<>(consumerFactory,
				containerProperties);
		return kafkaContainer;
	}

	/**
	 * @return
	 */
	@Bean("doorMessageListener")
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public DoorMessageListener listener() {
		return new DoorMessageListener();
	}
}
