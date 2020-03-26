/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.communication.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hirain.phm.synapsis.communication.Communication;
import com.hirain.phm.synapsis.communication.Message;
import com.hirain.phm.synapsis.connection.Connection;
import com.hirain.phm.synapsis.connection.TargetConnSelector;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 2, 2019 4:35:47 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 2, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Component
public class CommunicationImpl implements Communication {

	@Autowired(required = false)
	private Map<String, Connection> connectionMap;

	@Autowired(required = false)
	private TargetConnSelector selector;

	/**
	 * @see com.hirain.phm.synapsis.communication.Communication#send(com.hirain.phm.synapsis.communication.Message, int)
	 */
	@Override
	public Message<?> send(Message<?> message, int timeout) throws Exception {
		Connection connection = selector.select(message.getTarget(), connectionMap);
		if (connection != null) {
			return connection.send(message, timeout);
		}
		throw new Exception("target connection does not exist");
	}

	/**
	 * @see com.hirain.phm.synapsis.communication.Communication#sendAsync(com.hirain.phm.synapsis.communication.Message)
	 */
	@Override
	public void sendAsync(Message<?> message) {
		Connection connection = selector.select(message.getTarget(), connectionMap);
		if (connection != null) {
			connection.sendAsync(message);
		}
	}

}
