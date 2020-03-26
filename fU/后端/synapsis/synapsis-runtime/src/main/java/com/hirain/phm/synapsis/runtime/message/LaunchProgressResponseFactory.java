/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.runtime.message;

import org.springframework.stereotype.Component;

import com.hirain.phm.synapsis.constant.SidConstant;
import com.hirain.phm.synapsis.message.ResponseFactory;
import com.hirain.phm.synapsis.message.SynapsisResponse;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 8, 2020 5:36:02 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 8, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Component("Response-" + SidConstant.LAUNCH_STEP_COMMAND)
public class LaunchProgressResponseFactory implements ResponseFactory {

	/**
	 * @see com.hirain.phm.synapsis.message.ResponseFactory#create(byte[])
	 */
	@Override
	public SynapsisResponse create(byte[] datas) {
		LaunchProgressResponse response = new LaunchProgressResponse();
		response.parse(datas);
		return response;
	}

}
