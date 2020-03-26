/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.kafka.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.hirain.phm.bd.common.serialize.JsonUtil;
import com.hirain.phm.bd.ground.GroundWebApplication;
import com.hirain.phm.bd.ground.kafka.KafkaConsumerProperties;
import com.hirain.phm.bd.ground.kafka.KafkaProducerProperties;
import com.hirain.phm.bd.ground.train.controller.TrainGateWay;
import com.hirain.phm.bd.ground.train.domain.Train;
import com.hirain.phm.bd.ground.util.GroundAccessHelper;
import com.hirain.phm.bd.message.header.MessageHeader;
import com.hirain.phm.bd.message.train.RegisterMessage;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Nov 6, 2019 9:09:43 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Nov 6, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@ActiveProfiles("test")
@SpringBootTest(classes = GroundWebApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@EmbeddedKafka(count = 1, ports = { 9092 })
public class TestRegister {

	@Autowired
	private KafkaProducerProperties producerProps;

	@Autowired
	private KafkaConsumerProperties consumerProps;

	@Autowired
	private TrainGateWay gw;

	@Autowired
	private EmbeddedKafkaBroker kafkaEmbedded;

	@Test
	public void test() throws InterruptedException {
		kafkaEmbedded.addTopics("ground-train");
		KafkaProducer<String, Object> producer = producer();
		RegisterMessage message = getMessage();
		String json = JsonUtil.toString(message);
		ProducerRecord<String, Object> record = new ProducerRecord<>("train-ground", json.getBytes(Charset.forName("utf-8")));
		producer.send(record);

		producer.close();

		consume();

		Train train = gw.selectTrain(message.getProject(), message.getTrain());
		assertNotNull(train);
	}

	private void consume() {
		KafkaConsumer<String, Object> consumer = consumer();
		kafkaEmbedded.consumeFromAnEmbeddedTopic(consumer, GroundAccessHelper.GT_KAFKA_TOPIC);
		ConsumerRecords<String, Object> records = KafkaTestUtils.getRecords(consumer);
		assertNotNull(records);
		assertFalse(records.isEmpty());
		Iterator<ConsumerRecord<String, Object>> iterator = records.iterator();
		boolean hasReplay = false;
		while (iterator.hasNext()) {
			ConsumerRecord<String, Object> record = iterator.next();
			byte[] value = (byte[]) record.value();
			String jsonRes = new String(value, Charset.forName("utf-8"));
			System.err.println("test:" + jsonRes);
			MessageHeader response = JsonUtil.fromString(jsonRes, MessageHeader.class);
			assertEquals("北京地铁1号线", response.getProject());
			if (response.getSid() == GroundAccessHelper.GT_REGISTER_SID) {
				hasReplay = true;
			}
		}
		assertTrue(hasReplay);
		consumer.close();
	}

	private RegisterMessage getMessage() {
		RegisterMessage message = new RegisterMessage();
		message.setSid(GroundAccessHelper.TG_REGISTER_SID);
		message.setProject("北京地铁1号线");
		message.setCity("北京");
		message.setLine("1");
		message.setTrain("10");
		message.setMac1("ff-ff-ff-ff-ff-ff");
		message.setMac2("ff-ff-ff-ff-ff-ff");
		message.setSsl(false);
		message.setState(0);
		return message;
	}

	public KafkaProducer<String, Object> producer() {
		Map<String, Object> props = new HashMap<>();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, producerProps.getServers());
		props.put(ProducerConfig.RETRIES_CONFIG, producerProps.getRetries());
		props.put(ProducerConfig.BATCH_SIZE_CONFIG, producerProps.getBatchSize());
		props.put(ProducerConfig.LINGER_MS_CONFIG, producerProps.getLinger());
		props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, producerProps.getBufferMemory());
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, producerProps.getKeySerializer());
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, producerProps.getValueSerializer());
		return new KafkaProducer<>(props);
	}

	public KafkaConsumer<String, Object> consumer() {
		Map<String, Object> map = new HashMap<>();
		map.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, consumerProps.getServers());
		map.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, consumerProps.isEnableAutoCommit());
		map.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, consumerProps.getAutoCommitInterval());
		map.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, consumerProps.getSessionTimeout());
		map.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, consumerProps.getKeyDeserializer());
		map.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, consumerProps.getValueDeserializer());
		map.put(ConsumerConfig.GROUP_ID_CONFIG, "kafka-test");
		map.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, consumerProps.getAutoOffsetReset());
		map.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, consumerProps.getMaxRecords());
		return new KafkaConsumer<>(map);
	}

	public List<String> topics(String... topics) {
		List<String> result = new ArrayList<>();
		for (String topic : topics) {
			result.add(topic);
		}
		return result;
	}
}
