/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.board.message;

import java.nio.ByteBuffer;

import com.hirain.phm.synapsis.message.MessageConstant;
import com.hirain.phm.synapsis.message.SynapsisRequest;

import lombok.Data;

/**
 * @Version 1.0   
 * @Author zepei.tao@hirain.com  
 * @Created Dec 4, 2019 11:14:03 AM  
 * @Description
 * <p>   服务管理——>控制管理    启动完成确认报文
 * @Modification
 * <p>Date         Author      Version     Description  
 * <p>Dec 4, 2019     zepei.tao@hirain.com             1.0        create file 
 */
@Data
public class BoardStartupConfirmMessage implements SynapsisRequest {

	private byte data = 0;

	/** 
	 * @see com.hirain.phm.synapsis.message.SynapsisMessage#toBytes()
	 */
	@Override
	public byte[] toBytes() {
		ByteBuffer buffer = ByteBuffer.allocate(1).order(MessageConstant.MESSAGE_ORDER);
		buffer.put(data);
		return buffer.array();
	}
}
