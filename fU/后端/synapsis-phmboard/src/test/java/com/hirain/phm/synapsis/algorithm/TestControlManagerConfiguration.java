/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.algorithm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import com.hirain.phm.synapsis.algorithm.message.AlgoResultMessage;
import com.hirain.phm.synapsis.algorithm.message.AlgoResultResponseMessage;
import com.hirain.phm.synapsis.algorithm.util.PHMProgram;
import com.hirain.phm.synapsis.algorithm.util.SidConstant;
import com.hirain.phm.synapsis.communication.impl.TransportMessage;
import com.hirain.phm.synapsis.connection.Connection;
import com.hirain.phm.synapsis.message.SynapsisRequest;
import com.hirain.phm.synapsis.udp.UDPServer;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec Dec 16, 2019 6:40:19 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 16, 2019     zepei.tao@hirain.com    1.0   create file   
 */
@Configuration
public class TestControlManagerConfiguration {

	@Value("${synapsis.control.port}")
	private int targetPort;

	private Connection connection;

	@Bean("PHM_SERVICE")
	public Connection connection() {
		connection = new UDPServer(targetPort);
		return connection;
	}

	@EventListener
	public void messageArrived(SynapsisRequest request) {
		if (request instanceof AlgoResultMessage) {
			TransportMessage<AlgoResultResponseMessage> responseMessage = new TransportMessage<>();
			responseMessage.setSid(SidConstant.PHM_ALGORESULT_COMMAND);
			responseMessage.setTarget(PHMProgram.PHM_SERVICE.getCode());
			responseMessage.setSource(PHMProgram.PHM_CONTROL.getCode());
			responseMessage.setCounter(0);//TODO  计数器
			AlgoResultResponseMessage response = new AlgoResultResponseMessage();
			responseMessage.setData(response);
			connection.sendAsync(responseMessage);
		}
	}
}
