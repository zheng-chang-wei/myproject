/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.kafka;

import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.kafka.listener.BatchMessageListener;

import com.hirain.phm.bd.ground.common.event.MessageEvent;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年5月13日 上午9:38:31
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年5月13日 jianwen.xin@hirain.com 1.0 create file
 */
public class DoorMessageListener implements BatchMessageListener<String, Object> {

	@Autowired
	private ApplicationEventPublisher publisher;

	/**
	 * @see org.springframework.kafka.listener.GenericMessageListener#onMessage(java.lang.Object)
	 */
	@Override
	public void onMessage(List<ConsumerRecord<String, Object>> data) {
		for (ConsumerRecord<String, Object> record : data) {
			System.out.println(record.topic());
			byte[] value = (byte[]) record.value();
			publisher.publishEvent(new MessageEvent(record.key(), value));
		}
	}

}
