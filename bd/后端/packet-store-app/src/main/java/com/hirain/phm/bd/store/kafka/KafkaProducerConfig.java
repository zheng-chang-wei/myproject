package com.hirain.phm.bd.store.kafka;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年4月4日 上午9:49:45
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年4月4日 jianwen.xin@hirain.com 1.0 create file
 */
@Configuration
@EnableKafka
public class KafkaProducerConfig {

	@Autowired
	private KafkaProducerProperties properties;

	@Bean
	public Map<String, Object> producerOptions() {
		Map<String, Object> props = new HashMap<>();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getServers());
		props.put(ProducerConfig.RETRIES_CONFIG, properties.getRetries());
		props.put(ProducerConfig.BATCH_SIZE_CONFIG, properties.getBatchSize());
		props.put(ProducerConfig.LINGER_MS_CONFIG, properties.getLinger());
		props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, properties.getBufferMemory());
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, properties.getKeySerializer());
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, properties.getValueSerializer());
		return props;
	}

	@Bean
	public ProducerFactory<String, Object> producerFactory() {
		return new DefaultKafkaProducerFactory<>(producerOptions());
	}

	/**
	 * send common service message
	 *
	 * @return
	 */
	@Bean
	public KafkaTemplate<String, Object> template() {
		return new KafkaTemplate<>(producerFactory());
	}

}
