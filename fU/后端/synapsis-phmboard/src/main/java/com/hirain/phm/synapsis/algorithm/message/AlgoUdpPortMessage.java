/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.algorithm.message;

import java.nio.ByteBuffer;

import com.hirain.phm.synapsis.message.MessageConstant;
import com.hirain.phm.synapsis.message.SynapsisResponse;

import lombok.Data;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec Dec 21, 2019 11:43:35 AM
 * @Description
 *              <p>  算法-->壳子     算法对应(udpPort + 算法进程PID + 算法ID)的信息  
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 21, 2019     zepei.tao@hirain.com    1.0   create file   
 */
@Data
public class AlgoUdpPortMessage implements SynapsisResponse {

	private int udpPort;

	private int algoPid;

	private int algoID;

	/** 
	 * @see com.hirain.phm.synapsis.message.SynapsisResponse#parse(byte[])
	 */
	@Override
	public void parse(byte[] datas) {
		final ByteBuffer buffer = ByteBuffer.wrap(datas).order(MessageConstant.MESSAGE_ORDER);
		udpPort = buffer.getInt();
		algoPid = buffer.getInt();
		algoID = buffer.getInt();
	}

}
