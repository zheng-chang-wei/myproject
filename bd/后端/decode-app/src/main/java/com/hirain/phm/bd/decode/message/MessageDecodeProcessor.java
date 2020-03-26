/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.decode.message;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.concurrent.ListenableFuture;

import com.hirain.phm.bd.common.ZipUtil;
import com.hirain.phm.bd.common.serialize.JsonUtil;
import com.hirain.phm.bd.decode.service.MessageFutureCallback;
import com.hirain.phm.bd.message.CarriagePacket;
import com.hirain.phm.bd.message.CommonMessage;
import com.hirain.phm.bd.message.DoorPacket;
import com.hirain.phm.bd.message.TrainPacket;
import com.hirain.phm.bd.message.decode.DecodePacket;
import com.hirain.phm.bd.message.decode.KeyValueDecoder;
import com.hirain.phm.bd.message.decode.RunDataFrame;

import lombok.extern.slf4j.Slf4j;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年4月22日 下午3:01:11
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年4月22日 jianwen.xin@hirain.com 1.0 create file
 */
@Slf4j
public class MessageDecodeProcessor {

	// @Autowired
	private KeyValueDecoder decoder = new KeyValueDecoder();

	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;

	private ExecutorService executor;

	private BlockingQueue<ConsumerRecord<String, Object>> queue = new LinkedBlockingQueue<>();

	private String topicPrefix;

	public MessageDecodeProcessor(String topicPrefix) {
		this.topicPrefix = topicPrefix;
		executor = Executors.newSingleThreadExecutor();
		process();
	}

	public void push(ConsumerRecord<String, Object> record) {
		// queue.add(record);
		try {
			queue.put(record);
		} catch (InterruptedException e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * 
	 */
	private void process() {
		executor.submit(() -> {
			while (true) {
				try {

					ConsumerRecord<String, Object> record = null;
					try {
						record = queue.take();
					} catch (Throwable e) {
						log.error(e.getMessage(), e);
					}
					if (record == null) {
						log.error("record==null");
						continue;
					}
					log.info("into while");
					if (record.value() == null) {
						log.error("record.value()==null");
						continue;
					}
					byte[] compress = (byte[]) record.value();
					if (compress.length == 0) {
						log.error("compress.length==0");
						continue;
					}
					byte[] uncompress = ZipUtil.uncompress(compress);
					String value = new String(uncompress, Charset.forName("utf-8"));
					List<RunDataFrame> frames = new ArrayList<>();
					TrainPacket packet = JsonUtil.fromString(value, TrainPacket.class);
					if (packet == null || packet.getPackets() == null) {
						log.error("packet==null||packet.getPackets()==null");
						continue;
					}
					for (CarriagePacket cpacket : packet.getPackets()) {
						for (DoorPacket dpacket : cpacket.getPackets()) {
							for (CommonMessage message : dpacket.getMessages()) {
								RunDataFrame frame = decoder.decode(message);
								if (frame != null) {
									frame.setDebug(message.isDebug());
									frames.add(frame);
								} else {
									log.error("frame==null");
								}
							}
						}
					}
					DecodePacket decodePacket = new DecodePacket(0x21, packet.getProject(), packet.getTrain());
					decodePacket.setFrames(frames);
					decodePacket.setKeys(KeyValueDecoder.keys);
					send(record.topic(), record.key(), decodePacket);
					// log.info("{},{}", record.topic(), record.key());
				} catch (Throwable e) {
					log.error(e.getMessage(), e);
				}
			}
		});
	}

	@Async
	public void send(String topic, String key, DecodePacket decodePacket) {
		String value = JsonUtil.toString(decodePacket);
		byte[] datas = value.getBytes(Charset.forName("utf-8"));
		String decodeTopic = topicPrefix + topic;
		ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send(decodeTopic, key, datas);
		future.addCallback(new MessageFutureCallback(decodeTopic, key, datas) {

			@Override
			public void onFailure(Throwable ex) {
				log.error(getTopic() + "," + getKey() + "," + getMessage() + "send failed", ex);
			}

			@Override
			public void onSuccess(SendResult<String, Object> result) {
				log.info("{},{}", getTopic(), getKey());
			}
		});
	}
}
