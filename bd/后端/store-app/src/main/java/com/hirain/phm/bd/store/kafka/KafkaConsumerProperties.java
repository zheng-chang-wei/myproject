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
 * @Created 2019年4月4日 上午10:24:34
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
@ConfigurationProperties("kafka.consumer")
public class KafkaConsumerProperties {

	private String servers;

	private boolean enableAutoCommit;

	private String sessionTimeout;

	private String autoCommitInterval;

	private String groupId;

	private String autoOffsetReset;

	private int concurrency;

	private String keyDeserializer;

	private String valueDeserializer;

	private String groupPrefix;

}
