/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.algorithm.message.factory;

import org.springframework.stereotype.Component;

import com.hirain.phm.synapsis.algorithm.message.VersionInquireMessage;
import com.hirain.phm.synapsis.algorithm.util.SidConstant;
import com.hirain.phm.synapsis.message.ResponseFactory;
import com.hirain.phm.synapsis.message.SynapsisResponse;

/**
 * @Version 1.0   
 * @Author zepei.tao@hirain.com  
 * @Created Dec 14, 2019 3:49:23 PM  
 * @Description
 * <p>
 * @Modification
 * <p>Date         Author      Version     Description  
 * <p>Dec 14, 2019     zepei.tao@hirain.com             1.0        create file 
 */
@Component("PHM_CONTROL" + SidConstant.PHM_VERSION_COMMAND)
public class VersionInquireFactory implements ResponseFactory {

	/** 
	 * @see com.hirain.phm.synapsis.message.ResponseFactory#create(byte[])
	 */
	@Override
	public SynapsisResponse create(byte[] datas) {
		VersionInquireMessage message = new VersionInquireMessage();
		message.parse(datas);
		return message;
	}

}
