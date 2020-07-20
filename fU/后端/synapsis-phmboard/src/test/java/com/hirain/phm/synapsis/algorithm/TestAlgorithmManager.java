/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.algorithm;

import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hirain.phm.synapsis.algorithm.domain.PHMAlgoResult;
import com.hirain.phm.synapsis.algorithm.service.AlgoManagerService;
import com.hirain.phm.synapsis.algorithm.util.ByteUtil;
import com.hirain.phm.synapsis.message.MessageConstant;

import io.netty.util.internal.StringUtil;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec 14, 2019 4:18:29 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 14, 2019 zepei.tao@hirain.com 1.0 create file
 */
public class TestAlgorithmManager extends BaseTest {

	@Autowired
	private AlgoManagerService algorithmService;

	@Test
	public void testAlgoResultSend() throws Exception {
		PHMAlgoResult result = new PHMAlgoResult();
		result.setAlgoId(1);
		long timeStamp = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
		result.setTimestamp(ByteUtil.splitLong(timeStamp));
		result.setDateTime(parseDateTime2String(ByteUtil.splitLong(timeStamp)));
		result.setSaveData(true);
		algorithmService.algoResultSend(result);
	}

	/**
	 * 将8字节的datetime转换成“年月日时分秒毫秒”的格式
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
		return sb.append(year).append(month).append(date).append(hour).append(minute).append(second).append(millisecond).toString();
	}
}
