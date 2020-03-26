/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.comm;

import java.nio.ByteBuffer;

import com.hirain.phm.synapsis.message.MessageConstant;
import com.hirain.phm.synapsis.message.SynapsisRequest;
import com.hirain.phm.synapsis.message.SynapsisResponse;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 4, 2019 3:45:44 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 4, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Data
public class TestSynapsisMessage implements SynapsisRequest, SynapsisResponse {

	private int msg;

	/**
	 * @see com.hirain.phm.synapsis.message.SynapsisRequest#toBytes()
	 */
	@Override
	public byte[] toBytes() {
		ByteBuffer buffer = ByteBuffer.allocate(4).order(MessageConstant.MESSAGE_ORDER);
		buffer.putInt(msg);
		return buffer.array();
	}

	/**
	 * @see com.hirain.phm.synapsis.message.SynapsisResponse#parse(byte[])
	 */
	@Override
	public void parse(byte[] datas) {
		ByteBuffer buffer = ByteBuffer.wrap(datas).order(MessageConstant.MESSAGE_ORDER);
		msg = buffer.getInt();
	}
}
