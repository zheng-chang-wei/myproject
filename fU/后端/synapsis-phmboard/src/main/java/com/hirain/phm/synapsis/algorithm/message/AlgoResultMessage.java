/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.algorithm.message;

import java.nio.ByteBuffer;

import com.hirain.phm.synapsis.algorithm.domain.PHMAlgoResult;
import com.hirain.phm.synapsis.message.MessageConstant;
import com.hirain.phm.synapsis.message.SynapsisRequest;
import com.hirain.phm.synapsis.message.SynapsisResponse;

import io.netty.util.internal.StringUtil;
import lombok.Data;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec 14, 2019 3:15:47 PM
 * @Description
 *              <p>
 *              壳子-->控制管理 算法结果返回
 *              算法-->壳子 算法结果
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 14, 2019 zepei.tao@hirain.com 1.0 create file
 */
@Data
public class AlgoResultMessage implements SynapsisRequest, SynapsisResponse {

	private PHMAlgoResult phmAlgoResult;

	private int algoCode;

	private byte fileType;// 0：总线算法结果 ； 1：视频算法结果

	private int ALGOCODE_LEN = 4;

	private int FILETYPE_LEN = 1;

	private int TIMESTAMP_LEN = 8;

	private int ALGORESULT_LEN = 41;

	/**
	 * @see com.hirain.phm.synapsis.message.SynapsisRequest#toBytes()
	 */
	@Override
	public byte[] toBytes() {
		ByteBuffer buffer = ByteBuffer.allocate(ALGOCODE_LEN + FILETYPE_LEN + TIMESTAMP_LEN + ALGORESULT_LEN).order(MessageConstant.MESSAGE_ORDER);
		buffer.putInt(algoCode);
		buffer.put(fileType);
		buffer.put(phmAlgoResult.getTimestamp());
		buffer.put(phmAlgoResult.getData());
		return buffer.array();
	}

	/**
	 * @see com.hirain.phm.synapsis.message.SynapsisResponse#parse(byte[])
	 */
	@Override
	public void parse(byte[] datas) {
		final ByteBuffer buffer = ByteBuffer.wrap(datas).order(MessageConstant.MESSAGE_ORDER);
		phmAlgoResult = new PHMAlgoResult();
		phmAlgoResult.setData(datas);
		buffer.getShort();// 包头类型
		phmAlgoResult.setAlgoId(Short.toUnsignedInt(buffer.getShort()));// 算法ID
		buffer.get();// 算法包头长度
		byte[] timestamp = new byte[8];
		buffer.get(timestamp);
		phmAlgoResult.setTimestamp(timestamp);
		phmAlgoResult.setDateTime(parseDateTime2String(timestamp));

		buffer.get();// 算法供应商代码
		buffer.getShort();// 算法版本信息
		buffer.getShort();// 生命信号
		buffer.get();// 车、端
		buffer.get();// 设备顺序编号
		buffer.get();// 模型子系统类型
		buffer.getShort();// CRC校验
		buffer.get();// 算法自定义数据段长度
		byte[] itemName = new byte[16];// 自定义数据内容
		buffer.get(itemName);
		phmAlgoResult.setItemName(new String(itemName));
		phmAlgoResult.setSaveData(buffer.get() == 1 ? true : false);
	}

	/**
	 * 将8字节的datetime转换成“年月日_时分秒”的格式
	 */
	private String parseDateTime2String(byte[] data) {
		StringBuilder sb = new StringBuilder(StringUtil.EMPTY_STRING);
		final ByteBuffer buffer = ByteBuffer.wrap(data).order(MessageConstant.MESSAGE_ORDER);
		String year = String.valueOf(Byte.toUnsignedInt(buffer.get())).trim();
		if (year.length() == 1) {
			year = "200" + year;
		} else {
			year = "20" + year;
		}
		String month = String.valueOf(Byte.toUnsignedInt(buffer.get())).trim();
		if (month.length() == 1) {
			month = "0" + month;
		}
		String date = String.valueOf(Byte.toUnsignedInt(buffer.get())).trim();
		if (date.length() == 1) {
			date = "0" + date;
		}
		String hour = String.valueOf(Byte.toUnsignedInt(buffer.get())).trim();
		if (hour.length() == 1) {
			hour = "0" + hour;
		}
		String minute = String.valueOf(Byte.toUnsignedInt(buffer.get())).trim();
		if (minute.length() == 1) {
			minute = "0" + minute;
		}
		String second = String.valueOf(Byte.toUnsignedInt(buffer.get())).trim();
		if (second.length() == 1) {
			second = "0" + second;
		}
		String millisecond = String.valueOf(buffer.getShort()).trim();
		return sb.append(year).append(month).append(date).append("_").append(hour).append(minute).append(second).toString();
	}

}
