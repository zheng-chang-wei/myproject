/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.update.message;

import com.hirain.phm.synapsis.message.SynapsisRequest;

import lombok.Data;
import lombok.ToString;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec Jul 10, 2020 11:02:56 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jul 10, 2020 zepei.tao@hirain.com 1.0 create file
 */
@Data
@ToString
public class VersionUpdateMessage implements SynapsisRequest {

	/**
	 * @see com.hirain.phm.synapsis.message.SynapsisRequest#toBytes()
	 */
	@Override
	public byte[] toBytes() {
		return new byte[] { 0x00 };
	}

}
