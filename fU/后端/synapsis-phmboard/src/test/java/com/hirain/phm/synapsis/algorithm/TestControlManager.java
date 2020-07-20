/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.algorithm;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.hirain.phm.synapsis.algorithm.message.AlgoControlMessage;
import com.hirain.phm.synapsis.algorithm.message.StatusInquireMessage;
import com.hirain.phm.synapsis.algorithm.message.VersionInquireMessage;
import com.hirain.phm.synapsis.algorithm.util.PHMProgram;
import com.hirain.phm.synapsis.algorithm.util.SidConstant;
import com.hirain.phm.synapsis.communication.impl.TransportMessage;
import com.hirain.phm.synapsis.connection.Connection;

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
@Component
public class TestControlManager extends BaseTest {

	@Autowired
	@Qualifier("PHM_SERVICE")
	private Connection connection;

	private int timeout = 2;

	@Test
	public void testInqiureVersion() throws Exception {
		TransportMessage<VersionInquireMessage> inquireMessage = new TransportMessage<>();
		inquireMessage.setSid(SidConstant.PHM_VERSION_COMMAND);
		inquireMessage.setTarget(PHMProgram.PHM_SERVICE.getCode());
		inquireMessage.setSource(PHMProgram.PHM_CONTROL.getCode());
		inquireMessage.setCounter(0);//TODO 计数器
		VersionInquireMessage response = new VersionInquireMessage();
		inquireMessage.setData(response);
		sendMessage(inquireMessage);
	}

	@Test
	public void testAlgorithmControl() {
		TransportMessage<AlgoControlMessage> controlMessage = new TransportMessage<>();
		controlMessage.setSid(SidConstant.PHM_ALGOCONTROL_COMMAND);
		controlMessage.setTarget(PHMProgram.PHM_SERVICE.getCode());
		controlMessage.setSource(PHMProgram.PHM_CONTROL.getCode());
		controlMessage.setCounter(0);//TODO 计数器
		AlgoControlMessage response = new AlgoControlMessage();
		controlMessage.setData(response);
		sendMessage(controlMessage);
	}

	@Test
	public void testInquireStatus() {
		TransportMessage<StatusInquireMessage> inquireMessage = new TransportMessage<>();
		inquireMessage.setSid(SidConstant.PHM_ALGOSTATUS_COMMAND);
		inquireMessage.setTarget(PHMProgram.PHM_SERVICE.getCode());
		inquireMessage.setSource(PHMProgram.PHM_CONTROL.getCode());
		inquireMessage.setCounter(0);//TODO 计数器
		StatusInquireMessage response = new StatusInquireMessage();
		inquireMessage.setData(response);
		sendMessage(inquireMessage);
	}

	private void sendMessage(TransportMessage<?> message) {
		try {
			connection.send(message, timeout);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
