package com.hirain.phm.bd.ground.kafka;

import java.nio.charset.Charset;
import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.kafka.annotation.KafkaListener;

import com.hirain.phm.bd.ground.common.event.NoSidEvent;
import com.hirain.phm.bd.ground.realtime.service.IDecoderService;
import com.hirain.phm.bd.ground.util.GroundAccessHelper;

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
	private IDecoderService decoder;

	@Autowired
	private ApplicationEventPublisher eventBroker;

	@KafkaListener(topics = { GroundAccessHelper.TG_KAFKA_TOPIC, GroundAccessHelper.DATA_RECORD_TOPIC, GroundAccessHelper.SUBHEALTH_KAFKA_TOPIC,
			GroundAccessHelper.DIGITAL_TWIN_KAFKA_TOPIC })
	public void listen(List<ConsumerRecord<?, ?>> records) {
		for (ConsumerRecord<?, ?> record : records) {
			log.info("key:{},value:{}", record.key(), record.value());
			String json = new String((byte[]) record.value(), Charset.forName("utf-8"));
			System.err.println(json);
			Object message = decoder.decode(json);
			if (message != null) {
				eventBroker.publishEvent(message);
			} else {
				NoSidEvent event = new NoSidEvent();
				event.setTopic(record.topic());
				event.setValue(json);
				eventBroker.publishEvent(event);
			}
		}
	}

}
