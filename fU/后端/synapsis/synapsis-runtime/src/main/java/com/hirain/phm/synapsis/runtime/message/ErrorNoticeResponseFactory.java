/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.runtime.message;

import org.springframework.stereotype.Component;

import com.hirain.phm.synapsis.constant.SidConstant;
import com.hirain.phm.synapsis.message.ResponseFactory;
import com.hirain.phm.synapsis.message.SynapsisResponse;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 10, 2019 4:52:29 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 10, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Component("Response-" + SidConstant.ERROR_COMMAND)
public class ErrorNoticeResponseFactory implements ResponseFactory {

	/**
	 * @see com.hirain.phm.synapsis.message.ResponseFactory#create(byte[])
	 */
	@Override
	public SynapsisResponse create(byte[] datas) {
		ErrorNoticeResponse response = new ErrorNoticeResponse();
		response.parse(datas);
		return response;
	}

}
