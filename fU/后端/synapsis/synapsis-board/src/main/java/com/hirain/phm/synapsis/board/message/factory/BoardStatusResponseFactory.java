/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.board.message.factory;

import org.springframework.stereotype.Component;

import com.hirain.phm.synapsis.board.message.BoardStatusResponseMessage;
import com.hirain.phm.synapsis.constant.SidConstant;
import com.hirain.phm.synapsis.message.ResponseFactory;
import com.hirain.phm.synapsis.message.SynapsisResponse;

/**
 * @Version 1.0   
 * @Author zepei.tao@hirain.com  
 * @Created Dec 5, 2019 11:47:31 AM  
 * @Description
 * <p>
 * @Modification
 * <p>Date         Author      Version     Description  
 * <p>Dec 5, 2019     zepei.tao@hirain.com             1.0        create file 
 */
@Component("Response-" + SidConstant.STATUS_COMMAND)
public class BoardStatusResponseFactory implements ResponseFactory {

	/** 
	 * @see com.hirain.phm.synapsis.message.ResponseFactory#create(byte[])
	 */
	@Override
	public SynapsisResponse create(byte[] datas) {
		BoardStatusResponseMessage message = new BoardStatusResponseMessage();
		message.parse(datas);
		return message;
	}

}
