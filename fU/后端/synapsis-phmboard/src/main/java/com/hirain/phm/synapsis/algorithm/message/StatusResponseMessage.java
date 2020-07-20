/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.algorithm.message;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.hirain.phm.synapsis.algorithm.domain.AlgorithmStatus;
import com.hirain.phm.synapsis.message.MessageConstant;
import com.hirain.phm.synapsis.message.SynapsisRequest;

import lombok.Data;

/**
 * @Version 1.0   
 * @Author zepei.tao@hirain.com  
 * @Created Dec 14, 2019 2:22:59 PM  
 * @Description
 * <p>  壳子-->控制管理程序   算法状态回复
 * @Modification
 * <p>Date         Author      Version     Description  
 * <p>Dec 14, 2019     zepei.tao@hirain.com             1.0        create file 
 */
@Data
public class StatusResponseMessage implements SynapsisRequest {

	/**
	 * 算法数量
	 */
	private int algoNum;

	private Map<Integer, AlgorithmStatus> map = new HashMap<>();

	/** 
	 * @see com.hirain.phm.synapsis.message.SynapsisRequest#toBytes()
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public byte[] toBytes() {
		ByteBuffer buffer = ByteBuffer.allocate(1 + algoNum * 5).order(MessageConstant.MESSAGE_ORDER);
		buffer.put((byte) algoNum);

		Iterator entries = map.entrySet().iterator();
		while (entries.hasNext()) {
			Map.Entry entry = (Map.Entry) entries.next();
			int algoCode = (int) entry.getKey();
			buffer.putInt(algoCode);
			buffer.put((byte) ((AlgorithmStatus) entry.getValue()).getCode());
		}
		return buffer.array();
	}

}
