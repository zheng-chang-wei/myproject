/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;

import com.hirain.phm.synapsis.board.message.BoardControlResponseMessage;
import com.hirain.phm.synapsis.board.message.BoardLaunchMessage;
import com.hirain.phm.synapsis.board.message.BoardStatusInquireMessage;
import com.hirain.phm.synapsis.board.message.BoardStatusResponseMessage;
import com.hirain.phm.synapsis.board.message.BoardStoppingMessage;
import com.hirain.phm.synapsis.communication.impl.TransportMessage;
import com.hirain.phm.synapsis.connection.Connection;
import com.hirain.phm.synapsis.constant.Program;
import com.hirain.phm.synapsis.constant.SidConstant;
import com.hirain.phm.synapsis.message.SynapsisRequest;
import com.hirain.phm.synapsis.udp.UDPServer;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec 8, 2019 2:23:00 PM
 * @Description
 *              <p>
 *              模拟控制管理器
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 8, 2019 zepei.tao@hirain.com 1.0 create file
 */
// @Configuration
// @Component
public class TestControlManager {

	@Autowired
	@Qualifier("CPU_CONTROL")
	private Connection connection;

	@Value("${synapsis.control.port}")
	private int targetPort;

	@Bean("CPU_SERVICE")
	public Connection connection() {
		return new UDPServer(targetPort);
	}

	/**
	 * @see com.hirain.phm.synapsis.udp.UdpCallback#messageArrived(com.hirain.phm.synapsis.udp.packet.UDPPacket)
	 */
	@EventListener
	public void messageArrived(SynapsisRequest request) {
		System.err.println(request);
		if (request instanceof BoardLaunchMessage || request instanceof BoardStoppingMessage) {
			sendBoardControlResponse();
		}
		if (request instanceof BoardStatusInquireMessage) {
			sendBoardStatusResponse();
		}
	}

	private void sendBoardControlResponse() {
		TransportMessage<BoardControlResponseMessage> responseMessage = new TransportMessage<>();
		responseMessage.setSid(SidConstant.CONTROL_COMMAND);
		responseMessage.setTarget(Program.CPU_SERVICE.getCode());
		responseMessage.setSource(Program.CPU_CONTROL.getCode());
		responseMessage.setCounter(0);
		BoardControlResponseMessage response = new BoardControlResponseMessage();
		responseMessage.setData(response);
		connection.sendAsync(responseMessage);
	}

	private void sendBoardStatusResponse() {
		TransportMessage<BoardStatusResponseMessage> responseMessage = new TransportMessage<>();
		responseMessage.setSid(SidConstant.STATUS_COMMAND);
		responseMessage.setTarget(Program.CPU_SERVICE.getCode());
		responseMessage.setSource(Program.CPU_CONTROL.getCode());
		responseMessage.setCounter(0);
		BoardStatusResponseMessage response = new BoardStatusResponseMessage();
		responseMessage.setData(response);
		connection.sendAsync(responseMessage);
	}
}
