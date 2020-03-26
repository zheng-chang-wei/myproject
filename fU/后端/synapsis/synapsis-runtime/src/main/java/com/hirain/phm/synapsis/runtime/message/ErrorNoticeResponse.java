/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.runtime.message;

import com.hirain.phm.synapsis.message.SynapsisResponse;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 10, 2019 4:51:03 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 10, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Data
public class ErrorNoticeResponse implements SynapsisResponse {

	private byte result;

	/**
	 * @see com.hirain.phm.synapsis.message.SynapsisResponse#parse(byte[])
	 */
	@Override
	public void parse(byte[] datas) {
		result = datas[0];
	}

}
