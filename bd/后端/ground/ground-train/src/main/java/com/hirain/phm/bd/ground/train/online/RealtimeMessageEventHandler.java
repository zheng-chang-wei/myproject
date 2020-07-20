/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.train.online;

import java.nio.charset.Charset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

import com.hirain.phm.bd.common.serialize.JsonUtil;
import com.hirain.phm.bd.ground.common.event.MessageEvent;
import com.hirain.phm.bd.message.header.MessageHeader;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jul 16, 2020 6:21:26 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jul 16, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Configuration
public class RealtimeMessageEventHandler {

	@Autowired
	private HeartHandler handler;

	@EventListener
	@Async
	public void on(MessageEvent event) {
		String json = new String(event.getDatas(), Charset.forName("UTF-8"));
		MessageHeader header = JsonUtil.fromString(json, MessageHeader.class);
		handler.heart(header.getProject(), header.getTrain());
	}
}
