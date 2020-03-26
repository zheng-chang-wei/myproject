/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.decode.kafka.consumer;

import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.listener.BatchMessageListener;

import com.hirain.phm.bd.decode.service.MessageDecodeService;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年4月19日 上午11:49:41
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年4月19日 jianwen.xin@hirain.com 1.0 create file
 */
public class DoorMessageListener implements BatchMessageListener<String, Object> {

	@Autowired
	private MessageDecodeService decodeService;

	/**
	 * @see org.springframework.kafka.listener.GenericMessageListener#onMessage(java.lang.Object)
	 */
	@Override
	public void onMessage(List<ConsumerRecord<String, Object>> data) {
		decodeService.push(data);
	}

}
