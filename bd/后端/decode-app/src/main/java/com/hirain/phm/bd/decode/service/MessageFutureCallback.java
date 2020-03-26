/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.decode.service;

import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFutureCallback;

import lombok.Getter;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年4月18日 下午1:53:17
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年4月18日 jianwen.xin@hirain.com 1.0 create file
 */
@Getter
public abstract class MessageFutureCallback implements ListenableFutureCallback<SendResult<String, Object>> {

	private String topic;

	private String key;

	private Object message;

	/**
	 * @param topic
	 * @param key
	 * @param message
	 */
	public MessageFutureCallback(String topic, String key, Object message) {
		this.topic = topic;
		this.key = key;
		this.message = message;
	}

}
