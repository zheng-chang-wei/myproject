/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.algorithm.message;

import org.springframework.stereotype.Component;

import com.hirain.phm.synapsis.constant.SidConstant;
import com.hirain.phm.synapsis.message.MessageConstant;
import com.hirain.phm.synapsis.message.ResponseFactory;
import com.hirain.phm.synapsis.message.SynapsisResponse;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 17, 2019 2:09:08 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 17, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Component(MessageConstant.Factory_PREFIX + SidConstant.ALGORITHM_STATUS_COMMAND)
public class AlgorithmStatusResponseFactory implements ResponseFactory {

	/**
	 * @see com.hirain.phm.synapsis.message.ResponseFactory#create(byte[])
	 */
	@Override
	public SynapsisResponse create(byte[] datas) {
		AlgorithmStatusResponse response = new AlgorithmStatusResponse();
		response.parse(datas);
		return response;
	}

}
