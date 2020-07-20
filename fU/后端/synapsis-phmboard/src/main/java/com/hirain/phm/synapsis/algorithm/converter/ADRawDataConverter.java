/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.algorithm.converter;

import java.nio.ByteBuffer;

import org.springframework.stereotype.Component;

import com.hirain.phm.synapsis.algorithm.domain.ADRawData;
import com.hirain.phm.synapsis.algorithm.domain.AlgoRawData;
import com.hirain.phm.synapsis.message.MessageConstant;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec Dec 24, 2019 11:21:41 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 24, 2019     zepei.tao@hirain.com    1.0   create file   
 */
@Component("AD")
public class ADRawDataConverter implements RawDataConverter {

	/** 
	 * @see com.hirain.phm.synapsis.algorithm.converter.RawDataConverter#convert(byte[])
	 */
	@Override
	public AlgoRawData convert(byte[] datas) {
		final ByteBuffer buffer = ByteBuffer.wrap(datas).order(MessageConstant.MESSAGE_ORDER);
		ADRawData adRawData = new ADRawData();
		buffer.getInt();//consumptionID
		byte[] multicastIp = new byte[4];
		buffer.get(multicastIp);
		byte[] multicastPort = new byte[2];
		buffer.get(multicastPort);
		adRawData.setFrameIndex(buffer.getInt());
		byte[] unix_time = new byte[4];
		buffer.get(unix_time);
		adRawData.setUnix_time(unix_time);

		byte[] us_time = new byte[4];
		buffer.get(us_time);
		adRawData.setUs_time(us_time);

		adRawData.setSlotID(buffer.get());
		byte channelID = buffer.get();//通道号
		adRawData.setChlID(channelID);
		adRawData.setDataType(channelID);
		adRawData.setSampleRate(buffer.getInt());
		adRawData.setStartIndex(buffer.getLong());
		adRawData.setSampleNum(buffer.getInt());

		short dataLen = buffer.getShort();
		adRawData.setDataLen(dataLen);

		byte[] data = new byte[dataLen];
		buffer.get(data);
		adRawData.setData(data);

		return adRawData;
	}

}
