/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.transform.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

import com.hirain.phm.bd.transform.service.KafkaToMqttService;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年4月4日 上午10:36:18
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年4月4日 jianwen.xin@hirain.com 1.0 create file
 */
public class CommandListener {

	@Autowired
	private KafkaToMqttService service;

	@KafkaListener(topics = { "ground-train" })
	public void listen(ConsumerRecord<?, ?> record) {
		service.transform(record.value(), record.key().toString());
	}
}
