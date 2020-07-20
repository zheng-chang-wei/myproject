/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.SmartLifecycle;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.hirain.phm.bd.ground.common.kafka.KafkaConsumerManager;
import com.hirain.phm.bd.message.header.MessageHeader;

import lombok.extern.slf4j.Slf4j;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jun 17, 2020 2:48:30 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jun 17, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Profile("dev")
@Component
@Slf4j
public class KafkaConsumerStarter implements SmartLifecycle {

	@Autowired
	private KafkaConsumerManager manager;

	/**
	 * @see org.springframework.context.Lifecycle#start()
	 */
	@Override
	public void start() {
		MessageHeader header = new MessageHeader();
		header.setCity("深圳");
		header.setLine("7");
		header.setProject("深圳7号线一期");
		header.setTrain("717");
		header.setSid(0x01);
		log.info("create and start:" + header);
		manager.createAndStart(header.getCity(), header.getLine());
	}

	/**
	 * @see org.springframework.context.Lifecycle#stop()
	 */
	@Override
	public void stop() {

	}

	/**
	 * @see org.springframework.context.Lifecycle#isRunning()
	 */
	@Override
	public boolean isRunning() {
		return false;
	}

}
