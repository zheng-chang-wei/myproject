/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.algorithm.converter;

import java.nio.ByteBuffer;

import org.springframework.stereotype.Component;

import com.hirain.phm.synapsis.algorithm.domain.ADStorageData;
import com.hirain.phm.synapsis.algorithm.domain.StorageData;
import com.hirain.phm.synapsis.message.MessageConstant;
import com.hirain.phm.synapsis.setting.Variable.VariableType;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec Feb 19, 2020 1:47:43 PM
 * @Description
 *              <p>
 *              将组播监听到的数据，转化成存储文件格式的数据
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Feb 19, 2020 zepei.tao@hirain.com 1.0 create file
 */
@Component
public class StorageDataConverter {

	public StorageData converter(byte[] datas, VariableType type) {
		final ByteBuffer buffer = ByteBuffer.wrap(datas).order(MessageConstant.MESSAGE_ORDER);
		StorageData storageData = type == VariableType.AD ? new ADStorageData() : new StorageData();
		storageData.setType(type);
		buffer.getInt();// consumptionID
		byte[] multicastIp = new byte[4];
		buffer.get(multicastIp);
		byte[] multicastPort = new byte[2];
		buffer.get(multicastPort);
		storageData.setFrameIndex(buffer.getInt());
		byte[] unix_time = new byte[4];
		buffer.get(unix_time);
		storageData.setUnix_time(unix_time);
		byte[] us_time = new byte[4];
		buffer.get(us_time);
		storageData.setUs_time(us_time);
		switch (type) {
		case MVB:
		case ECN:
			buffer.getShort();// dataCRC
			break;
		case AD:
			buffer.get();// slotID
			int chnID = Byte.toUnsignedInt(buffer.get());// channelID
			((ADStorageData) storageData).setChnID(chnID);
			buffer.getInt();// sampleRate;
			buffer.getLong();// startIndex;
			buffer.getInt();// sampleNum;
			break;
		}
		short dataLen = buffer.getShort();
		storageData.setDataLen(dataLen);
		byte[] data = new byte[dataLen];
		buffer.get(data);
		storageData.setData(data);
		return storageData;
	}
}
