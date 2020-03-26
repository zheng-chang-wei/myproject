/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.kafka;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Nov 1, 2019 9:20:04 AM
 * @Description
 *              <p>
 *              测试使用，不影响正线运行的车辆
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Nov 1, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Profile({ "dev", "test" })
@Component
@Slf4j
public class TestProducer implements IKafkaProducer {

	/**
	 * @see com.hirain.phm.bd.ground.kafka.IKafkaProducer#send(java.lang.String, java.lang.String, byte[])
	 */
	@Override
	public void send(String topic, String key, byte[] payload) {
		log.info("send to kafka:{}-{}", topic, key);
	}

}
