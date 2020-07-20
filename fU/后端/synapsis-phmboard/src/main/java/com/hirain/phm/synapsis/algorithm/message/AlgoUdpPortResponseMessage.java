/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.algorithm.message;

import com.hirain.phm.synapsis.message.SynapsisRequest;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec Dec 21, 2019 11:38:59 AM
 * @Description
 *              <p>  壳子-->算法    是否收到该算法对应端口的  确认信息
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 21, 2019     zepei.tao@hirain.com    1.0   create file   
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AlgoUdpPortResponseMessage extends Case2AlgoMessage implements SynapsisRequest {

	private int result;//0:收到   1：未收到

	/** 
	 * @see com.hirain.phm.synapsis.message.SynapsisRequest#toBytes()
	 */
	@Override
	public byte[] toBytes() {
		return new byte[] { (byte) result };
	}

}
