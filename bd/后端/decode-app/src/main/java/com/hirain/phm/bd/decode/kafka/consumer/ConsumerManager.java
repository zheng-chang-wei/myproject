/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.decode.kafka.consumer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.AbstractMessageListenerContainer;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.stereotype.Component;

import com.hirain.phm.bd.common.pinyin.PinyinUtil;
import com.hirain.phm.bd.decode.kafka.KafkaConsumerProperties;
import com.hirain.phm.bd.decode.kafka.KafkaTopicProperties;
import com.hirain.phm.bd.message.header.MessageHeader;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年4月19日 上午11:02:40
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年4月19日 jianwen.xin@hirain.com 1.0 create file
 */
@Component
@Configuration
public class ConsumerManager {

	@Autowired
	private KafkaConsumerProperties properties;

	@Autowired
	private ConsumerFactory<String, Object> consumerFactory;

	@Autowired
	private KafkaTopicProperties topicProperties;

	private Map<String, AbstractMessageListenerContainer<?, ?>> kafkaContainerMap = new ConcurrentHashMap<>();

	public void createAndStart(MessageHeader header) {
		String pinyin = PinyinUtil.getFullSpell(header.getCity());
		String topic = topicProperties.getHistory() + pinyin + "-" + header.getLine();
		String realTopic = topicProperties.getRealtime() + pinyin + "-" + header.getLine();
		if (kafkaContainerMap.keySet().contains(topic)) {
			return;
		}
		String groupId = properties.getGroupPrefix() + pinyin + "-" + header.getLine();
		AbstractMessageListenerContainer<String, Object> container = kafkaContainer(groupId, topic, realTopic);
		kafkaContainerMap.put(topic, container);
		container.start();
	}

	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public AbstractMessageListenerContainer<String, Object> kafkaContainer(String groupId, String... topics) {
		ContainerProperties containerProperties = new ContainerProperties(topics);
		containerProperties.setGroupId(properties.getGroupPrefix() + groupId);
		containerProperties.setMessageListener(listener());
		ConcurrentMessageListenerContainer<String, Object> kafkaContainer = new ConcurrentMessageListenerContainer<>(consumerFactory,
				containerProperties);
		kafkaContainer.setConcurrency(properties.getConcurrency());
		return kafkaContainer;
	}

	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public DoorMessageListener listener() {
		return new DoorMessageListener();
	}
}
