/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.comm;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hirain.phm.synapsis.SynapsisCApplication;
import com.hirain.phm.synapsis.communication.Message;
import com.hirain.phm.synapsis.communication.impl.TransportMessage;
import com.hirain.phm.synapsis.udp.codec.MessageCodec;
import com.hirain.phm.synapsis.udp.packet.UDPPacket;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 4, 2019 3:44:21 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 4, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@SpringBootTest(classes = SynapsisCApplication.class)
@RunWith(SpringRunner.class)
public class TestMessageCodec {

	@Value("${synapsis.control.port}")
	private int controlPort;

	private String controlIp = "127.0.0.1";

	@Autowired
	private MessageCodec codec;

	@Test
	public void test() {
		TransportMessage<TestSynapsisMessage> reqMsg = new TransportMessage<>();
		reqMsg.setSid(1);
		reqMsg.setTarget(1);
		reqMsg.setSource(2);
		reqMsg.setCounter(0);
		TestSynapsisMessage message = new TestSynapsisMessage();
		message.setMsg(1);
		reqMsg.setData(message);
		UDPPacket packet = codec.encode(reqMsg);
		UDPPacket expected = new UDPPacket();
		expected.setSid(reqMsg.getSid());
		expected.setIp(controlIp);
		expected.setPort(controlPort);
		expected.setData(new byte[] { 1, 1, 1, 0, 0, 0, 0, 0, 4, 0, 0, 0, 1, 0, 0, 0 });
		assertEquals(expected, packet);

		Message<?> result = codec.decode(packet);
		assertEquals(message, result.getData());
	}

}
