/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.runtime.message;

import com.hirain.phm.synapsis.message.SynapsisRequest;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 8, 2020 5:37:57 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 8, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public class LaunchProgressRequest implements SynapsisRequest {

	/**
	 * @see com.hirain.phm.synapsis.message.SynapsisRequest#toBytes()
	 */
	@Override
	public byte[] toBytes() {
		return new byte[] {};
	}

}
