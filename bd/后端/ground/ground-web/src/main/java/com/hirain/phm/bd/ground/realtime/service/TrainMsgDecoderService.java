/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.realtime.service;

import java.nio.charset.Charset;

import org.springframework.stereotype.Component;

import com.hirain.phm.bd.common.serialize.JsonUtil;
import com.hirain.phm.bd.ground.util.GroundAccessHelper;
import com.hirain.phm.bd.message.big.DataRecordList;
import com.hirain.phm.bd.message.data.MetaDataEvent;
import com.hirain.phm.bd.message.decode.DecodePacket;
import com.hirain.phm.bd.message.header.MessageHeader;
import com.hirain.phm.bd.message.train.FaultPacket;
import com.hirain.phm.bd.message.train.LifeMessage;
import com.hirain.phm.bd.message.train.OffsetMessage;
import com.hirain.phm.bd.message.train.RegisterMessage;
import com.hirain.phm.bd.message.train.StorageMessage;
import com.hirain.phm.bd.message.train.SubhealthPacket;
import com.hirain.phm.bd.message.train.TrainConfigMessage;
import com.hirain.phm.bd.message.train.WillMessage;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年5月10日 下午5:53:05
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年5月10日 jianwen.xin@hirain.com 1.0 create file
 */
@Component
public class TrainMsgDecoderService implements IDecoderService {

	/**
	 * @see com.hirain.phm.bd.ground.realtime.service.service.IDecoderService#decode(java.lang.String)
	 */
	@Override
	public Object decode(String msg) {
		MessageHeader header = JsonUtil.fromString(msg, MessageHeader.class);
		System.err.println("sid:" + header.getSid());
		switch (header.getSid()) {
		case GroundAccessHelper.TG_REGISTER_SID:
			return JsonUtil.fromString(msg, RegisterMessage.class);
		case GroundAccessHelper.TG_STORAGESTATUS_SID:// 处理车载端存储状态报文
			return JsonUtil.fromString(msg, StorageMessage.class);
		case GroundAccessHelper.TG_FAULTINFO_SID:
			FaultPacket faultPacket = JsonUtil.fromString(msg, FaultPacket.class);
			if (faultPacket.getMessages() == null) {
				return null;
			}
			return faultPacket;
		case GroundAccessHelper.TG_SYSTEMCONFIGINFO_SID:// 处理系统配置信息报文
			return JsonUtil.fromString(msg, TrainConfigMessage.class);
		case GroundAccessHelper.TG_DELETERESULT_SID:// 处理删除是否成功报文
			// TODO
			break;
		case GroundAccessHelper.TG_CONFIGRESULT_SID:// 处理用户配置是否成功报文
			// TODO
			break;
		case GroundAccessHelper.TG_DOORMESSAGE_SID:// 处理车门报文
			break;
		case GroundAccessHelper.TG_ONOFFLINENOTICE_SID:// 处理上线下线是否成功报文
			return JsonUtil.fromString(msg, WillMessage.class);
		case GroundAccessHelper.TG_LIFEINFO_SID:// 处理寿命信息报文
			return JsonUtil.fromString(msg, LifeMessage.class);
		case GroundAccessHelper.TG_TRAINOFFSET_SID:// 车载端历史文件偏移量
			return JsonUtil.fromString(msg, OffsetMessage.class);
		case GroundAccessHelper.GG_DATA_RECORD:
			return JsonUtil.fromString(msg, DataRecordList.class);
		case GroundAccessHelper.GG_METADATA_EVENT:
			return JsonUtil.fromString(msg, MetaDataEvent.class);
		case GroundAccessHelper.TG_SUBHEALTH_SID:
			return JsonUtil.fromString(msg, SubhealthPacket.class, "yyyy-MM-dd HH:mm:ss:SSS");
		default:
			break;
		}
		return null;
	}

	/**
	 * @see com.hirain.phm.bd.ground.realtime.service.service.IDecoderService#decode(byte[])
	 */
	@Override
	public DecodePacket decode(byte[] datas) {
		String str = new String(datas, Charset.forName("utf-8"));
		DecodePacket packet = JsonUtil.fromString(str, DecodePacket.class);
		return packet;
	}

}
