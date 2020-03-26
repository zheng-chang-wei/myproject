/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.runtime.message;

import com.hirain.phm.synapsis.constant.ErrorCode;
import com.hirain.phm.synapsis.message.SynapsisRequest;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 10, 2019 4:37:08 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 10, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Data
public class ErrorNoticeRequest implements SynapsisRequest {

	private ErrorCode status;

	/**
	 * @see com.hirain.phm.synapsis.message.SynapsisRequest#toBytes()
	 */
	@Override
	public byte[] toBytes() {
		return new byte[] { (byte) status.getCode() };
	}

}
