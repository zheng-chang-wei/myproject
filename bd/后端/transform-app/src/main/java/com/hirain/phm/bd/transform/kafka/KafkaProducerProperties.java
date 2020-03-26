/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.transform.kafka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年4月4日 上午9:54:00
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年4月4日 jianwen.xin@hirain.com 1.0 create file
 */
@Component
public class KafkaProducerProperties {

	@Value("${kafka.producer.servers}")
	private String servers;

	@Value("${kafka.producer.retries}")
	private int retries;

	@Value("${kafka.producer.batch.size}")
	private int batchSize;

	@Value("${kafka.producer.linger}")
	private int linger;

	@Value("${kafka.producer.buffer.memory}")
	private int bufferMemory;

	@Value("${kafka.producer.acks}")
	private String acks;

	@Value("${kafka.producer.key.serializer}")
	private String keySerializer;

	@Value("${kafka.producer.value.serializer}")
	private String valueSerializer;

	/**
	 * @return the servers
	 */
	public String getServers() {
		return servers;
	}

	/**
	 * @return the retries
	 */
	public int getRetries() {
		return retries;
	}

	/**
	 * @return the batchSize
	 */
	public int getBatchSize() {
		return batchSize;
	}

	/**
	 * @return the linger
	 */
	public int getLinger() {
		return linger;
	}

	/**
	 * @return the bufferMemory
	 */
	public int getBufferMemory() {
		return bufferMemory;
	}

	/**
	 * @return the acks
	 */
	public String getAcks() {
		return acks;
	}

	/**
	 * @return the keySerializer
	 */
	public String getKeySerializer() {
		return keySerializer;
	}

	/**
	 * @return the valueSerializer
	 */
	public String getValueSerializer() {
		return valueSerializer;
	}
}
