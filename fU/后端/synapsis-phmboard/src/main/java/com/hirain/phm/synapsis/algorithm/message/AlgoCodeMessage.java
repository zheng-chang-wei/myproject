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
 * @Created Dec Dec 21, 2019 11:46:14 AM
 * @Description
 *              <p>   壳子-->算法   算法Code信息
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 21, 2019     zepei.tao@hirain.com    1.0   create file   
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AlgoCodeMessage extends Case2AlgoMessage implements SynapsisRequest {

	private int algoCode;

	/** 
	 * @see com.hirain.phm.synapsis.message.SynapsisRequest#toBytes()
	 */
	@Override
	public byte[] toBytes() {
		return new byte[] { (byte) algoCode };
	}

}
