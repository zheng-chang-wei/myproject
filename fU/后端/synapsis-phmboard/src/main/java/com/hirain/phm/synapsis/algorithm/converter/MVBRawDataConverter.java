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
 * @Created Dec Dec 24, 2019 9:58:52 AM
 * @Description
 *              <p>  将组播监听到的MVB数据，转化成发送给算法的算法原始数据
 * @Modification 
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 24, 2019     zepei.tao@hirain.com    1.0   create file   
 */
@Component("MVB")
public class MVBRawDataConverter implements RawDataConverter {

	/** 
	 * @see com.hirain.phm.synapsis.algorithm.converter.RawDataConverter#convert(byte[])
	 */
	@Override
	public AlgoRawData convert(byte[] datas) {
		final ByteBuffer buffer = ByteBuffer.wrap(datas).order(MessageConstant.MESSAGE_ORDER);
		BusRawData mvbRawData = new BusRawData();
		mvbRawData.setDataType((byte) 0x00);
		buffer.getInt();//consumptionID
		byte[] multicastIp = new byte[4];
		buffer.get(multicastIp);
		byte[] multicastPort = new byte[2];
		buffer.get(multicastPort);
		mvbRawData.setFrameIndex(buffer.getInt());
		byte[] unix_time = new byte[4];
		buffer.get(unix_time);
		mvbRawData.setUnix_time(unix_time);

		byte[] us_time = new byte[4];
		buffer.get(us_time);
		mvbRawData.setUs_time(us_time);

		mvbRawData.setDataCrc(buffer.getShort());
		short dataLen = buffer.getShort();
		mvbRawData.setDataLen(dataLen);

		byte[] data = new byte[dataLen];
		buffer.get(data);
		mvbRawData.setData(data);

		return mvbRawData;
	}

}
