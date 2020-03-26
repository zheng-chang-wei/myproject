/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.store.kafka.consumer;

import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.listener.BatchMessageListener;

import com.hirain.phm.bd.store.service.IMessageDispatcher;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年5月17日 下午5:09:22
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年5月17日 jianwen.xin@hirain.com 1.0 create file
 */
public class MessageListener implements BatchMessageListener<String, Object> {

	@Autowired
	private IMessageDispatcher dispatcher;

	/**
	 * @see org.springframework.kafka.listener.GenericMessageListener#onMessage(java.lang.Object)
	 */
	@Override
	public void onMessage(List<ConsumerRecord<String, Object>> data) {
		for (ConsumerRecord<String, Object> record : data) {
			// System.err.println(record.topic());
			byte[] bs = (byte[]) record.value();
			dispatcher.push(record.key(), bs);
		}
	}

}
