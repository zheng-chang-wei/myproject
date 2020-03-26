/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.udp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.hirain.phm.synapsis.communication.impl.TransportMessage;
import com.hirain.phm.synapsis.connection.Connection;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 4, 2019 1:43:31 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 4, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Configuration
@Component
public class TestCallback {

	@Autowired
	@Qualifier("Send")
	private Connection connection;

	/**
	 * @see com.hirain.phm.synapsis.udp.UdpCallback#messageArrived(com.hirain.phm.synapsis.udp.packet.UDPPacket)
	 */
	@EventListener
	public void messageArrived(Request request) {
		System.err.println(request);
		TransportMessage<Response> responseMessage = new TransportMessage<>();
		responseMessage.setSid(0);
		responseMessage.setTarget(2);
		responseMessage.setSource(1);
		responseMessage.setCounter(0);
		Response response = new Response();
		response.setCode(request.getMsg());
		responseMessage.setData(response);
		connection.sendAsync(responseMessage);
	}

}
