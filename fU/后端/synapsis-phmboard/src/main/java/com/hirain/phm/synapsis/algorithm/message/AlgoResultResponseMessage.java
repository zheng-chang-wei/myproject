/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.algorithm.message;

import java.nio.ByteBuffer;

import com.hirain.phm.synapsis.message.MessageConstant;
import com.hirain.phm.synapsis.message.SynapsisRequest;
import com.hirain.phm.synapsis.message.SynapsisResponse;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Version 1.0   
 * @Author zepei.tao@hirain.com  
 * @Created Dec 14, 2019 3:17:42 PM  
 * @Description
 * <p>  控制管理-->壳子      算法结果确认；
   *                  壳子-->算法   算法结果确认
 * @Modification
 * <p>Date         Author      Version     Description  
 * <p>Dec 14, 2019     zepei.tao@hirain.com             1.0        create file 
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AlgoResultResponseMessage extends Case2AlgoMessage implements SynapsisResponse, SynapsisRequest {

	private boolean success;

	/** 
	 * @see com.hirain.phm.synapsis.message.SynapsisResponse#parse(byte[])
	 */
	@Override
	public void parse(byte[] datas) {
		final ByteBuffer buffer = ByteBuffer.wrap(datas).order(MessageConstant.MESSAGE_ORDER);
		success = buffer.get() == (byte) 0 ? true : false;
	}

	/** 
	 * @see com.hirain.phm.synapsis.message.SynapsisRequest#toBytes()
	 */
	@Override
	public byte[] toBytes() {
		if (success) {
			return new byte[] { 0 };
		} else {
			return new byte[] { 1 };
		}
	}

}
