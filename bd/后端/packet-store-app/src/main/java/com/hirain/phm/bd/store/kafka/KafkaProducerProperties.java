/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.store.kafka;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

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
@Data
@Component
@ConfigurationProperties("kafka.producer")
public class KafkaProducerProperties {

	private String servers;

	private int retries;

	private int batchSize;

	private int linger;

	private int bufferMemory;

	private String acks;

	private String keySerializer;

	private String valueSerializer;

}
