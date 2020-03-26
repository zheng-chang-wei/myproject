/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.runtime.message;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import com.hirain.phm.synapsis.message.MessageConstant;
import com.hirain.phm.synapsis.message.SynapsisResponse;

import lombok.Getter;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 8, 2020 5:24:05 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 8, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public class LaunchProgressResponse implements SynapsisResponse {

	@Getter
	private int stepNum;

	@Getter
	private int stepIndex;

	@Getter
	private String message;

	/**
	 * @see com.hirain.phm.synapsis.message.SynapsisResponse#parse(byte[])
	 */
	@Override
	public void parse(byte[] datas) {
		ByteBuffer buffer = ByteBuffer.wrap(datas).order(MessageConstant.MESSAGE_ORDER);
		stepNum = Byte.toUnsignedInt(buffer.get());
		stepIndex = Byte.toUnsignedInt(buffer.get());
		int length = Byte.toUnsignedInt(buffer.get());
		byte[] bs = new byte[length];
		buffer.get(bs);
		message = new String(bs, Charset.forName("utf-8"));
	}

}
