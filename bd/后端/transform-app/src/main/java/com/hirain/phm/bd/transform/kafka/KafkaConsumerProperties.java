/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.transform.kafka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年4月4日 上午10:24:34
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年4月4日 jianwen.xin@hirain.com 1.0 create file
 */
@Component
public class KafkaConsumerProperties {

	@Value("${kafka.consumer.servers}")
	private String servers;

	@Value("${kafka.consumer.enable.auto.commit}")
	private boolean enableAutoCommit;

	@Value("${kafka.consumer.session.timeout}")
	private String sessionTimeout;

	@Value("${kafka.consumer.auto.commit.interval}")
	private String autoCommitInterval;

	@Value("${kafka.consumer.group.id}")
	private String groupId;

	@Value("${kafka.consumer.auto.offset.reset}")
	private String autoOffsetReset;

	@Value("${kafka.consumer.concurrency}")
	private int concurrency;

	@Value("${kafka.consumer.key.deserializer}")
	private String keyDeserializer;

	@Value("${kafka.consumer.value.deserializer}")
	private String valueDeserializer;

	/**
	 * @return the servers
	 */
	public String getServers() {
		return servers;
	}

	/**
	 * @return the enableAutoCommit
	 */
	public boolean isEnableAutoCommit() {
		return enableAutoCommit;
	}

	/**
	 * @return the sessionTimeout
	 */
	public String getSessionTimeout() {
		return sessionTimeout;
	}

	/**
	 * @return the autoCommitInterval
	 */
	public String getAutoCommitInterval() {
		return autoCommitInterval;
	}

	/**
	 * @return the groupId
	 */
	public String getGroupId() {
		return groupId;
	}

	/**
	 * @return the autoOffsetReset
	 */
	public String getAutoOffsetReset() {
		return autoOffsetReset;
	}

	/**
	 * @return the concurrency
	 */
	public int getConcurrency() {
		return concurrency;
	}

	/**
	 * @return the keySerializer
	 */
	public String getKeyDeserializer() {
		return keyDeserializer;
	}

	/**
	 * @return the valueSerializer
	 */
	public String getValueDeserializer() {
		return valueDeserializer;
	}
}
