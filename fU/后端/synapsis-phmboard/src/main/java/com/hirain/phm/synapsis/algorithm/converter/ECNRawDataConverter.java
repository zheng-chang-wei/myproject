/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.algorithm.converter;

import java.nio.ByteBuffer;

import org.springframework.stereotype.Component;

import com.hirain.phm.synapsis.algorithm.domain.AlgoRawData;
import com.hirain.phm.synapsis.algorithm.domain.BusRawData;
import com.hirain.phm.synapsis.message.MessageConstant;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec Dec 24, 2019 11:20:23 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 24, 2019 zepei.tao@hirain.com 1.0 create file
 */
@Component("ECN")
public class ECNRawDataConverter implements RawDataConverter {

	/**
	 * @see com.hirain.phm.synapsis.algorithm.converter.RawDataConverter#convert(byte[])
	 */
	@Override
	public AlgoRawData convert(byte[] datas) {
		final ByteBuffer buffer = ByteBuffer.wrap(datas).order(MessageConstant.MESSAGE_ORDER);
		BusRawData ecnRawData = new BusRawData();
		ecnRawData.setDataType((byte) 0x99);
		buffer.getInt();// consumptionID
		byte[] multicastIp = new byte[4];
		buffer.get(multicastIp);
		byte[] multicastPort = new byte[2];
		buffer.get(multicastPort);
		ecnRawData.setFrameIndex(buffer.getInt());
		byte[] unix_time = new byte[4];
		buffer.get(unix_time);
		ecnRawData.setUnix_time(unix_time);

		byte[] us_time = new byte[4];
		buffer.get(us_time);
		ecnRawData.setUs_time(us_time);

		ecnRawData.setDataCrc(buffer.getShort());
		short dataLen = buffer.getShort();
		ecnRawData.setDataLen(dataLen);

		byte[] data = new byte[dataLen];
		buffer.get(data);
		ecnRawData.setData(data);

		return ecnRawData;
	}

}
