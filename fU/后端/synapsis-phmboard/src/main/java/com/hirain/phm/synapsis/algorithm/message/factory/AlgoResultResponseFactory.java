/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.algorithm.message.factory;

import org.springframework.stereotype.Component;

import com.hirain.phm.synapsis.algorithm.message.AlgoResultResponseMessage;
import com.hirain.phm.synapsis.algorithm.util.SidConstant;
import com.hirain.phm.synapsis.message.ResponseFactory;
import com.hirain.phm.synapsis.message.SynapsisResponse;

/**
 * @Version 1.0   
 * @Author zepei.tao@hirain.com  
 * @Created Dec 14, 2019 3:50:49 PM  
 * @Description
 * <p>  解析控制管理程序返回的算法结果确认信息
 * @Modification
 * <p>Date         Author      Version     Description  
 * <p>Dec 14, 2019     zepei.tao@hirain.com             1.0        create file 
 */
@Component("PHM_CONTROL" + SidConstant.PHM_ALGORESULT_COMMAND)
public class AlgoResultResponseFactory implements ResponseFactory {

	/** 
	 * @see com.hirain.phm.synapsis.message.ResponseFactory#create(byte[])
	 */
	@Override
	public SynapsisResponse create(byte[] datas) {
		AlgoResultResponseMessage message = new AlgoResultResponseMessage();
		message.parse(datas);
		return message;
	}

}
