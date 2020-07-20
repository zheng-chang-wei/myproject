/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.algorithm.message.factory;

import com.hirain.phm.synapsis.algorithm.message.AlgoCodeResponseMessage;
import com.hirain.phm.synapsis.message.ResponseFactory;
import com.hirain.phm.synapsis.message.SynapsisResponse;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec Dec 23, 2019 11:38:42 AM
 * @Description
 *              <p> 解析算法发送过来的是否收到算法Code信息
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 23, 2019     zepei.tao@hirain.com    1.0   create file   
 */
//@Component("PHM_ALGO" + SidConstant.PHM_ALGOCODE_COMMAND)
public class AlgoCodeFactory implements ResponseFactory {

	/** 
	 * @see com.hirain.phm.synapsis.message.ResponseFactory#create(byte[])
	 */
	@Override
	public SynapsisResponse create(byte[] datas) {
		AlgoCodeResponseMessage message = new AlgoCodeResponseMessage();
		message.parse(datas);
		return message;
	}

}
