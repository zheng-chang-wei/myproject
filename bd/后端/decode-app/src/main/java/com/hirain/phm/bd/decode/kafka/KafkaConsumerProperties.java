package com.hirain.phm.bd.decode.kafka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

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
@Getter
public class KafkaConsumerProperties {

	@Value("${kafka.consumer.servers}")
	private String servers;

	@Value("${kafka.consumer.enable.auto.commit}")
	private boolean enableAutoCommit;

	@Value("${kafka.consumer.session.timeout}")
	private String sessionTimeout;

	@Value("${kafka.consumer.auto.commit.interval}")
	private String autoCommitInterval;

	@Value("${kafka.consumer.group.register}")
	private String groupId;

	@Value("${kafka.consumer.auto.offset.reset}")
	private String autoOffsetReset;

	@Value("${kafka.consumer.concurrency}")
	private int concurrency;

	@Value("${kafka.consumer.key.deserializer}")
	private String keyDeserializer;

	@Value("${kafka.consumer.value.deserializer}")
	private String valueDeserializer;

	@Value("${kafka.consumer.group.prefix}")
	private String groupPrefix;

}
