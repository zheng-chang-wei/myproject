/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.algorithm.message;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import com.hirain.phm.synapsis.algorithm.domain.RunStatus;
import com.hirain.phm.synapsis.message.MessageConstant;
import com.hirain.phm.synapsis.message.SynapsisResponse;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 17, 2019 2:04:04 PM
 * @Description
 *              <p>
 *              算法状态反馈报文
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 17, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Data
public class AlgorithmStatusResponse implements SynapsisResponse {

	private Map<Integer, RunStatus> statusMap = new HashMap<>();

	/**
	 * @see com.hirain.phm.synapsis.message.SynapsisResponse#parse(byte[])
	 */
	@Override
	public void parse(byte[] datas) {
		ByteBuffer buffer = ByteBuffer.wrap(datas).order(MessageConstant.MESSAGE_ORDER);
		@SuppressWarnings("unused")
		byte slotId = buffer.get();
		byte length = buffer.get();
		RunStatus[] values = RunStatus.values();
		for (int i = 0; i < length; i++) {
			int id = buffer.getInt();
			byte status = buffer.get();
			statusMap.put(id, values[status]);
		}
	}

}
