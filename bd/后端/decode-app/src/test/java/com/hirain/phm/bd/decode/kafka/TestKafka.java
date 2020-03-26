/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.decode.kafka;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.hirain.phm.bd.common.ZipUtil;
import com.hirain.phm.bd.common.serialize.JsonUtil;
import com.hirain.phm.bd.decode.DecodeAppApplication;
import com.hirain.phm.bd.decode.TestNewDecoder;
import com.hirain.phm.bd.message.CarriagePacket;
import com.hirain.phm.bd.message.CommonMessage;
import com.hirain.phm.bd.message.DoorPacket;
import com.hirain.phm.bd.message.TrainPacket;
import com.hirain.phm.bd.message.decode.DecodePacket;
import com.hirain.phm.bd.message.train.RegisterMessage;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Nov 12, 2019 9:58:33 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Nov 12, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@SpringBootTest(classes = DecodeAppApplication.class)
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@EmbeddedKafka(count = 1, ports = { 9092 })
public class TestKafka {

	@Autowired
	@Qualifier("c_properties")
	private Map<String, Object> cprops;

	@Autowired
	@Qualifier("p_properties")
	private Map<String, Object> pprops;

	@Autowired
	private EmbeddedKafkaBroker kafkaEmbedded;

	@Test
	public void testProps() {
		assertNotNull(cprops);
		assertNotNull(pprops);
		assertFalse(cprops.isEmpty());
		assertFalse(pprops.isEmpty());
		assertFalse(cprops.equals(pprops));
		for (String key : pprops.keySet()) {
			System.out.println(key + ":" + pprops.get(key));
		}

		for (String key : cprops.keySet()) {
			System.out.println(key + ":" + cprops.get(key));
		}
	}

	@Test
	public void testSendAndReceive() throws Exception {
		String decodeTopic = "decode-message-beijing-1";
		kafkaEmbedded.addTopics(decodeTopic);
		@SuppressWarnings("unchecked")
		KafkaConsumer<String, Object> consumer = new KafkaConsumer<>((Map<String, Object>) cprops.get("c_properties"));
		kafkaEmbedded.consumeFromEmbeddedTopics(consumer, decodeTopic);
		TimeUnit.SECONDS.sleep(1);
		@SuppressWarnings("unchecked")
		KafkaProducer<String, Object> producer = new KafkaProducer<>((Map<String, Object>) pprops.get("p_properties"));
		sendRegister(producer);

		TimeUnit.SECONDS.sleep(10);

		sendMessage(producer);

		ConsumerRecords<String, Object> records = KafkaTestUtils.getRecords(consumer);
		assertNotNull(records);
		assertFalse(records.isEmpty());
		ConsumerRecord<String, Object> record = records.iterator().next();
		assertEquals("北京地铁1号线-10", record.key());
		byte[] bs = (byte[]) record.value();
		String json = new String(bs, Charset.forName("utf-8"));
		DecodePacket packet = JsonUtil.fromString(json, DecodePacket.class);
		assertEquals("北京地铁1号线", packet.getProject());
		assertEquals("10", packet.getTrain());
		packet.getFrames().forEach(System.out::println);
	}

	/**
	 * @param producer
	 */
	public void sendMessage(KafkaProducer<String, Object> producer) {
		ProducerRecord<String, Object> record = new ProducerRecord<>("message-beijing-1", "北京地铁1号线-10", messages());
		producer.send(record);
	}

	private void sendRegister(KafkaProducer<String, Object> producer) throws InterruptedException, ExecutionException {
		RegisterMessage message = getMessage();
		String json = JsonUtil.toString(message);
		ProducerRecord<String, Object> producerRecord = new ProducerRecord<>("train-ground", message.getProject() + "-" + message.getTrain(),
				json.getBytes(Charset.forName("utf-8")));
		Future<RecordMetadata> future = producer.send(producerRecord);
		RecordMetadata metadata = future.get();
		System.err.println("send:" + metadata.topic() + "," + metadata.offset());
	}

	private RegisterMessage getMessage() {
		RegisterMessage message = new RegisterMessage();
		message.setSid(0x01);
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

	private byte[] messages() {
		TrainPacket packet = new TrainPacket();
		packet.setProject("北京地铁1号线");
		packet.setTrain("10");
		CarriagePacket cp = new CarriagePacket();
		cp.setCarriageId(1);
		DoorPacket dp = new DoorPacket();
		dp.setDoorId(1);
		CommonMessage message = new CommonMessage();
		message.setDatas(TestNewDecoder.messages());
		message.setMilli(0);
		message.setMonth(11);
		message.setYear(2019);
		dp.setMessages(Arrays.asList(message));
		cp.setPackets(Arrays.asList(dp));
		packet.setPackets(Arrays.asList(cp));

		String json = JsonUtil.toString(packet);
		byte[] bs = json.getBytes(Charset.forName("utf-8"));
		return ZipUtil.compress(bs);
	}
}
