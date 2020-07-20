/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.algorithm.domain;

import java.nio.ByteBuffer;

import com.hirain.phm.synapsis.message.MessageConstant;
import com.hirain.phm.synapsis.setting.Variable.VariableType;

import lombok.Data;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec Feb 19, 2020 1:44:15 PM
 * @Description
 *              <p>
 *              存储的算法原始数据对象
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Feb 19, 2020 zepei.tao@hirain.com 1.0 create file
 */
@Data
public class StorageData {

	private VariableType type;

	/**
	 * 帧计数
	 */
	private int frameIndex;

	/**
	 * 秒
	 */
	private byte[] unix_time;// 4字节

	/**
	 * 毫秒
	 */
	private byte[] us_time;// 4字节

	private short dataLen;

	private byte[] data;

	public byte[] toBytes() {
		ByteBuffer buffer = ByteBuffer.allocate(14 + dataLen).order(MessageConstant.MESSAGE_ORDER);
		buffer.putInt(frameIndex);
		buffer.put(unix_time);
		buffer.put(us_time);
		buffer.putShort(dataLen);
		buffer.put(data);
		return buffer.array();
	}
}
