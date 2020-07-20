/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.algorithm.message.factory;

import org.springframework.stereotype.Component;

import com.hirain.phm.synapsis.algorithm.message.AlgoResultMessage;
import com.hirain.phm.synapsis.algorithm.util.SidConstant;
import com.hirain.phm.synapsis.message.ResponseFactory;
import com.hirain.phm.synapsis.message.SynapsisResponse;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec Dec 23, 2019 11:40:06 AM
 * @Description
 *              <p>
 *              解析算法发送过来的算法结果信息
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 23, 2019 zepei.tao@hirain.com 1.0 create file
 */
@Component("PHM_ALGO" + SidConstant.PHM_ALGORESULT_COMMAND)
public class AlgoResultFactory implements ResponseFactory {

	/**
	 * @see com.hirain.phm.synapsis.message.ResponseFactory#create(byte[])
	 */
	@Override
	public SynapsisResponse create(byte[] datas) {
		AlgoResultMessage message = new AlgoResultMessage();
		message.parse(datas);
		return message;
	}

}
