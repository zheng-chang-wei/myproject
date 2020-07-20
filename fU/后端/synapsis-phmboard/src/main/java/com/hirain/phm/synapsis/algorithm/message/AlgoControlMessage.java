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
 * @Created Dec 14, 2019 2:41:19 PM  
 * @Description
 * <p>  控制管理-->壳子    算法启动、停止报文
 * @Modification
 * <p>Date         Author      Version     Description  
 * <p>Dec 14, 2019     zepei.tao@hirain.com             1.0        create file 
 */
@Data
public class AlgoControlMessage implements SynapsisResponse {

	private boolean start;

	/** 
	 * @see com.hirain.phm.synapsis.message.SynapsisResponse#parse(byte[])
	 */
	@Override
	public void parse(byte[] datas) {
		final ByteBuffer buffer = ByteBuffer.wrap(datas).order(MessageConstant.MESSAGE_ORDER);
		start = buffer.get() == (byte) 1 ? true : false;
	}

}
