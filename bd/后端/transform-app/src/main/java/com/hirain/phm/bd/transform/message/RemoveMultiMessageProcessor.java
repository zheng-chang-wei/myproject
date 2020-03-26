/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.transform.message;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

import com.hirain.phm.bd.common.serialize.JsonUtil;
import com.hirain.phm.bd.message.CarriagePacket;
import com.hirain.phm.bd.message.CommonMessage;
import com.hirain.phm.bd.message.DoorPacket;
import com.hirain.phm.bd.message.TrainPacket;
import com.hirain.phm.bd.transform.redis.RedisUtil;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年4月8日 下午4:22:49
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年4月8日 jianwen.xin@hirain.com 1.0 create file
 */
public class RemoveMultiMessageProcessor implements IMessageProcessor {

	@Autowired
	private RedisUtil redis;

	@Autowired
	private ApplicationEventPublisher eventBroker;

	private BlockingQueue<List<String>> queue = new LinkedBlockingQueue<>();

	private static DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyyMMddHHmmss SSS");

	private ExecutorService executor = Executors.newSingleThreadExecutor();

	@Override
	public void push(String topic, byte[] payload) {
		queue.offer(Arrays.asList(topic, new String(payload, Charset.forName("utf-8"))));
	}

	@Override
	public void process() {
		executor.submit(() -> {
			while (true) {
				try {
					List<String> list = null;
					try {
						list = queue.take();
					} catch (Exception e) {
						e.printStackTrace();
					}
					if (list == null || list.size() < 2) {
						continue;
					}
					String topic = list.get(0);
					String json = list.get(1);
					TrainPacket trainPacket = JsonUtil.fromString(json, TrainPacket.class);
					int index = trainPacket.getIndex();
					for (CarriagePacket carriagePacket : trainPacket.getPackets()) {
						int carriageId = carriagePacket.getCarriageId();
						for (DoorPacket packet : carriagePacket.getPackets()) {
							int doorId = packet.getDoorId();
							String rkey = topic + "-" + carriageId + "-" + doorId;
							Map<Object, Object> map = redis.hmget(rkey);
							for (CommonMessage message : packet.getMessages()) {
								boolean send = handle(index, message, map);
								if (!send) {
									packet.getMessages().remove(message);
								}
							}
							if (packet.getMessages().size() == 0) {
								carriagePacket.getPackets().remove(packet);
							}
							redis.hset(rkey, map);
						}
						if (carriagePacket.getPackets().size() == 0) {
							trainPacket.getPackets().remove(carriagePacket);
						}
					}
					if (trainPacket.getPackets().size() == 0) {
						// do nothing
					} else {
						eventBroker.publishEvent(new MessageEvent(this, trainPacket, topic));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * @param index
	 * @param message
	 * @param map
	 * @return
	 */
	private boolean handle(int index, CommonMessage message, Map<Object, Object> map) {
		boolean send = true;
		byte[] datas = message.getDatas();
		int seq = seq(datas);
		LocalDateTime time1 = time(message);
		Object object = map.get(String.valueOf(seq));
		String value = null;
		if (object != null) {
			value = object.toString();
		}
		if (value == null) {
			String newValue = time1.format(pattern);
			map.put(String.valueOf(seq), index + newValue);
		} else {
			int sindex = Integer.parseInt(value.substring(0, 1));
			LocalDateTime time2 = LocalDateTime.parse(value.substring(1), pattern);
			if (index == sindex) {
				String newValue = time1.format(pattern);
				map.put(String.valueOf(seq), index + newValue);
			} else {
				if (time1.isAfter(time2)) {
					String newValue = time1.format(pattern);
					map.put(String.valueOf(seq), index + newValue);
				} else {
					send = false;
				}
			}
		}
		return send;
	}

	/**
	 * @param datas
	 * @return 车门报文序号
	 */
	private int seq(byte[] datas) {
		return datas[0];
	}

	private LocalDateTime time(CommonMessage message) {
		ByteBuffer buffer = ByteBuffer.wrap(message.getDatas());
		int day = buffer.get(21);
		int hour = buffer.get(22);
		int minute = buffer.get(23);
		int second = buffer.get(25);
		LocalDateTime time = LocalDateTime.of(message.getYear(), message.getMonth(), day, hour, minute, second, message.getMilli() * 1000000);
		return time;
	}

}
