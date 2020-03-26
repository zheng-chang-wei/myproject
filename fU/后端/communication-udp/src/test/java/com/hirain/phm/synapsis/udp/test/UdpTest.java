/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.udp.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hirain.phm.synapsis.communication.Communication;
import com.hirain.phm.synapsis.communication.Message;
import com.hirain.phm.synapsis.communication.impl.TransportMessage;
import com.hirain.phm.synapsis.udp.Request;
import com.hirain.phm.synapsis.udp.Response;
import com.hirain.phm.synapsis.udp.TestApplication;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 4, 2019 11:29:44 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 4, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@SpringBootTest(classes = TestApplication.class)
@RunWith(SpringRunner.class)
public class UdpTest {

	@Autowired
	private Communication communication;

	@Test
	public void testSend() {
		Request request = new Request();
		request.setMsg(255);
		TransportMessage<Request> message = new TransportMessage<>();
		message.setSid(0);
		message.setTarget(1);
		message.setSource(2);
		message.setCounter(0);
		message.setData(request);
		try {
			Message<?> result = communication.send(message, 2);
			assertTrue(result.getData() instanceof Response);
			Response response = (Response) result.getData();
			assertEquals(request.getMsg(), response.getCode());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
