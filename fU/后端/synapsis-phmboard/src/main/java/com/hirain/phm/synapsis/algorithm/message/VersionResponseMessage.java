/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.algorithm.message;

import java.nio.ByteBuffer;

import com.hirain.phm.synapsis.message.MessageConstant;
import com.hirain.phm.synapsis.message.SynapsisRequest;

import lombok.Data;

/**
 * @Version 1.0   
 * @Author zepei.tao@hirain.com  
 * @Created Dec 14, 2019 2:35:26 PM  
 * @Description
 * <p>  算法管理-->控制管理    版本查询回复
 * @Modification
 * <p>Date         Author      Version     Description  
 * <p>Dec 14, 2019     zepei.tao@hirain.com             1.0        create file 
 */
@Data
public class VersionResponseMessage implements SynapsisRequest {

	private String version;

	/** 
	 * @see com.hirain.phm.synapsis.message.SynapsisRequest#toBytes()
	 */
	@Override
	public byte[] toBytes() {
		ByteBuffer buffer = ByteBuffer.allocate(4).order(MessageConstant.MESSAGE_ORDER);
		String[] split = version.split("\\.");
		buffer.put((byte) 0);
		for (String str : split) {
			int valueOf = Integer.valueOf(str);
			buffer.put((byte) valueOf);
		}
		return buffer.array();
	}

}
