/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.algorithm.message;

import java.nio.ByteBuffer;

import com.hirain.phm.synapsis.algorithm.domain.ADRawData;
import com.hirain.phm.synapsis.algorithm.domain.BusRawData;
import com.hirain.phm.synapsis.message.MessageConstant;
import com.hirain.phm.synapsis.message.SynapsisRequest;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec Dec 21, 2019 6:03:38 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 21, 2019 zepei.tao@hirain.com 1.0 create file
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AlgoRawDataMessage extends Case2AlgoMessage implements SynapsisRequest {

	private BusRawData busData;

	private ADRawData adData;

	private byte[] photoData;

	private int BUSDATAHEADLEN = 17;// 1+4+4+4+2+2

	private int ADDATAHEADLEN = 33;// 1+4+4+4+1+1+4+8+4+2

	/**
	 * @see com.hirain.phm.synapsis.message.SynapsisRequest#toBytes()
	 */
	@Override
	public byte[] toBytes() {
		ByteBuffer buffer = null;
		if (busData != null) {
			buffer = ByteBuffer.allocate(BUSDATAHEADLEN + busData.getDataLen()).order(MessageConstant.MESSAGE_ORDER);
			buffer.put(busData.getDataType());
			buffer.putInt(busData.getFrameIndex());
			buffer.put(busData.getUnix_time());
			buffer.put(busData.getUs_time());
			buffer.putShort(busData.getDataCrc());
			buffer.putShort(busData.getDataLen());
			buffer.put(busData.getData());
		} else if (adData != null) {
			buffer = ByteBuffer.allocate(ADDATAHEADLEN + adData.getDataLen()).order(MessageConstant.MESSAGE_ORDER);
			buffer.put(adData.getDataType());
			buffer.putInt(adData.getFrameIndex());
			buffer.put(adData.getUnix_time());
			buffer.put(adData.getUs_time());
			buffer.put(adData.getSlotID());
			buffer.put(adData.getChlID());
			buffer.putInt(adData.getSampleRate());
			buffer.putLong(adData.getStartIndex());
			buffer.putInt(adData.getSampleNum());
			buffer.putShort(adData.getDataLen());
			buffer.put(adData.getData());
		} else if (photoData != null) {
			buffer = ByteBuffer.allocate(photoData.length).order(MessageConstant.MESSAGE_ORDER);
			buffer.put(photoData);
		}
		return buffer.array();
	}

}
