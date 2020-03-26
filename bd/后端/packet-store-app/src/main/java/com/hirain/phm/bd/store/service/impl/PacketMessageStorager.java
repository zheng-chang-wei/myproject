/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.store.service.impl;

import java.nio.charset.Charset;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.beans.factory.annotation.Autowired;

import com.hirain.phm.bd.common.serialize.JsonUtil;
import com.hirain.phm.bd.data.DataStoreService;
import com.hirain.phm.bd.data.bean.DataRecord;
import com.hirain.phm.bd.message.decode.DecodePacket;
import com.hirain.phm.bd.store.service.IMessageStorager;
import com.hirain.phm.bd.store.service.MessageUnpacker;

import lombok.extern.slf4j.Slf4j;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Mar 5, 2020 10:26:13 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Mar 5, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Slf4j
public class PacketMessageStorager implements IMessageStorager {

	private BlockingQueue<DecodePacket> queue = new LinkedBlockingQueue<>();

	private ExecutorService executor;

	@Autowired
	private MessageUnpacker unpacker;

	@Autowired
	private DataStoreService service;

	/**
	 * @see com.hirain.phm.bd.store.service.IMessageStorager#push(byte[])
	 */
	@Override
	public void push(byte[] payload) {
		String message = new String(payload, Charset.forName("utf-8"));
		try {
			DecodePacket packet = JsonUtil.fromString(message, DecodePacket.class);
			queue.offer(packet);
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * @see com.hirain.phm.bd.store.service.IMessageStorager#init(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public void init(String project, String train) {
		start();
	}

	/**
	 * 
	 */
	private void start() {
		if (executor == null) {
			executor = Executors.newSingleThreadExecutor(r -> new Thread(r, PacketMessageStorager.class.getName()));
		}
		executor.submit(() -> {
			while (true) {
				DecodePacket packet = queue.take();
				List<DataRecord> records = unpacker.unpack(packet);
				service.insertList(records);
			}
		});
	}

}
