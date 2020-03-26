/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.result.message;

import org.springframework.stereotype.Component;

import com.hirain.phm.synapsis.constant.SidConstant;
import com.hirain.phm.synapsis.message.MessageConstant;
import com.hirain.phm.synapsis.message.ResponseFactory;
import com.hirain.phm.synapsis.message.SynapsisResponse;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Mar 17, 2020 4:30:57 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Mar 17, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Component(MessageConstant.Factory_PREFIX + SidConstant.ALGORITHM_RESULT_RESPONSE)
public class AlgorithmResultMessageFactory implements ResponseFactory {

	/**
	 * @see com.hirain.phm.synapsis.message.ResponseFactory#create(byte[])
	 */
	@Override
	public SynapsisResponse create(byte[] datas) {
		AlgorithmResultMessage message = new AlgorithmResultMessage();
		message.parse(datas);
		return message;
	}

}
