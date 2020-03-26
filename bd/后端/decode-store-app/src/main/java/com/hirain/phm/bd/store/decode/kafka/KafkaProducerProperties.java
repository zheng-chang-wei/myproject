package com.hirain.phm.bd.store.decode.kafka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

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
@Getter
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

}
