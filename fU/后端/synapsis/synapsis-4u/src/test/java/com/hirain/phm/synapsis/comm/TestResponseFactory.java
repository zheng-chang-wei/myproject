/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.comm;

import org.springframework.stereotype.Component;

import com.hirain.phm.synapsis.message.ResponseFactory;
import com.hirain.phm.synapsis.message.SynapsisResponse;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 4, 2019 3:48:12 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 4, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Component("Response-1")
public class TestResponseFactory implements ResponseFactory {

	/**
	 * @see com.hirain.phm.synapsis.message.ResponseFactory#create(byte[])
	 */
	@Override
	public SynapsisResponse create(byte[] datas) {
		TestSynapsisMessage message = new TestSynapsisMessage();
		message.parse(datas);
		return message;
	}

}
