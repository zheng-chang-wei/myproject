/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.update.message.factory;

import org.springframework.stereotype.Component;

import com.hirain.phm.synapsis.constant.SidConstant;
import com.hirain.phm.synapsis.message.MessageConstant;
import com.hirain.phm.synapsis.message.ResponseFactory;
import com.hirain.phm.synapsis.message.SynapsisResponse;
import com.hirain.phm.synapsis.update.message.VersionUpdateResponseMessage;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec Jul 8, 2020 10:20:46 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jul 8, 2020 zepei.tao@hirain.com 1.0 create file
 */
@Component(MessageConstant.Factory_PREFIX + SidConstant.VERSION_UPDATE_COMMAND)
public class VersionUpdateResponseFactory implements ResponseFactory {

	/**
	 * @see com.hirain.phm.synapsis.message.ResponseFactory#create(byte[])
	 */
	@Override
	public SynapsisResponse create(byte[] datas) {
		VersionUpdateResponseMessage response = new VersionUpdateResponseMessage();
		response.parse(datas);
		return response;
	}

}
