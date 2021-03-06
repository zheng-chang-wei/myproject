package com.hirain.phm.bd.store.kafka;

import java.nio.charset.Charset;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.kafka.annotation.KafkaListener;

import com.hirain.phm.bd.common.serialize.JsonUtil;
import com.hirain.phm.bd.message.header.MessageHeader;
import com.hirain.phm.bd.store.filter.ProjectFilter;

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
public class CommandListener {

	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	private ProjectFilter filter;

	@KafkaListener(topics = { "train-ground" })
	public void listen(ConsumerRecord<?, ?> record) {
		String string = new String((byte[]) record.value(), Charset.forName("utf-8"));
		MessageHeader header = JsonUtil.fromString(string, MessageHeader.class);
		if (filter.filter(header.getProject())) {
			System.err.println(string);
			publisher.publishEvent(header);
		}
	}
}
