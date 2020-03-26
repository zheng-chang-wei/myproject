/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.board.message;

import com.hirain.phm.synapsis.message.SynapsisRequest;

import lombok.Data;

/**
 * @Version 1.0   
 * @Author zepei.tao@hirain.com  
 * @Created Dec 4, 2019 2:31:30 PM  
 * @Description
 * <p>   服务管理——>控制管理   板卡状态查询报文
 * @Modification
 * <p>Date         Author      Version     Description  
 * <p>Dec 4, 2019     zepei.tao@hirain.com             1.0        create file 
 */
@Data
public class BoardStatusInquireMessage implements SynapsisRequest {

	/** 
	 * @see com.hirain.phm.synapsis.message.SynapsisRequest#toBytes()
	 */
	@Override
	public byte[] toBytes() {
		return new byte[0];
	}

}
