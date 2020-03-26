/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.algorithm.message;

import java.nio.ByteBuffer;

import com.hirain.phm.synapsis.message.MessageConstant;
import com.hirain.phm.synapsis.message.SynapsisRequest;

import lombok.ToString;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 17, 2019 2:03:25 PM
 * @Description
 *              <p>
 *              算法状态查询报文
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 17, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@ToString
public class AlgorithmStatusRequest implements SynapsisRequest {

	private int slotId;

	private String ip;

	/**
	 * 
	 */
	public AlgorithmStatusRequest(String ip, int slotId) {
		this.ip = ip;
		this.slotId = slotId;
	}

	/**
	 * @see com.hirain.phm.synapsis.message.SynapsisRequest#toBytes()
	 */
	@Override
	public byte[] toBytes() {
		ByteBuffer buffer = ByteBuffer.allocate(5).order(MessageConstant.MESSAGE_ORDER);
		String[] ips = ip.split("\\.");
		for (int i = 0; i < ips.length; i++) {
			buffer.put((byte) Integer.parseInt(ips[i]));
		}
		buffer.put((byte) slotId);
		return buffer.array();
	}

}
