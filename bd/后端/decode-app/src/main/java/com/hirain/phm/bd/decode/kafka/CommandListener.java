package com.hirain.phm.bd.decode.kafka;

import java.nio.charset.Charset;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

import com.hirain.phm.bd.common.serialize.JsonUtil;
import com.hirain.phm.bd.decode.kafka.consumer.ConsumerManager;
import com.hirain.phm.bd.message.header.MessageHeader;

import lombok.extern.slf4j.Slf4j;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年4月4日 上午10:36:18
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年4月4日 jianwen.xin@hirain.com 1.0 create file
 */
@Slf4j
public class CommandListener {

	@Autowired
	private ConsumerManager consumerManager;

	private ExecutorService executor = Executors.newSingleThreadExecutor();

	@KafkaListener(topics = { "train-ground" })
	public void listen(ConsumerRecord<?, ?> record) {
		executor.submit(() -> {
			String string = new String((byte[]) record.value(), Charset.forName("utf-8"));
			log.info("{} {}", record.topic(), string);
			MessageHeader header = JsonUtil.fromString(string, MessageHeader.class);
			if (header.getSid() == 0x01) {
				// 处理注册报文
				consumerManager.createAndStart(header);
			}
		});
	}
}
