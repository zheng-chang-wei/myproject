/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.store.original.message;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hirain.phm.bd.common.ZipUtil;
import com.hirain.phm.bd.common.serialize.JsonUtil;
import com.hirain.phm.bd.message.CarriagePacket;
import com.hirain.phm.bd.message.CommonMessage;
import com.hirain.phm.bd.message.DoorPacket;
import com.hirain.phm.bd.message.TrainPacket;
import com.hirain.phm.bd.store.domain.StoreEntry;
import com.hirain.phm.bd.store.service.IMessageEncoder;

import lombok.extern.slf4j.Slf4j;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年5月20日 下午6:11:46
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年5月20日 jianwen.xin@hirain.com 1.0 create file
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Slf4j
public class OriginalMessageEncoder implements IMessageEncoder {

	static String SEPARATOR = ",";

	static String LINE_SEPARATOR = System.getProperty("line.separator");

	static String HEADER_LINE = "时戳" + SEPARATOR + "车厢号" + SEPARATOR + "门地址" + SEPARATOR + "数据" + SEPARATOR + "debug" + LINE_SEPARATOR;

	/**
	 * @see com.hirain.phm.bd.store.service.IMessageEncoder#encode(java.lang.String)
	 */
	@Override
	public List<StoreEntry> encode(byte[] payload) {
		byte[] uncompress = null;
		try {
			uncompress = ZipUtil.uncompress(payload);
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
		}
		if (uncompress == null) {
			log.info("uncompress==null");
			return new ArrayList<>();
		}
		List<StoreEntry> entries = new ArrayList<>();
		try {
			String message = new String(uncompress, Charset.forName("utf-8"));
			TrainPacket packet = JsonUtil.fromString(message, TrainPacket.class);
			if (packet == null || packet.getPackets() == null) {
				log.info("packet == null || packet.getPackets() == null");
				return entries;
			}
			for (CarriagePacket carriagePacket : packet.getPackets()) {
				int carriageId = carriagePacket.getCarriageId();
				for (DoorPacket doorPacket : carriagePacket.getPackets()) {
					int doorId = doorPacket.getDoorId();
					for (CommonMessage msg : doorPacket.getMessages()) {
						String dateTime = getDateTime(msg);
						String string = joint(dateTime, String.valueOf(carriageId), String.valueOf(doorId), byte2Hex(msg.getDatas()),
								String.valueOf(msg.isDebug()));
						byte[] bytes = string.getBytes(Charset.forName("utf-8"));
						StoreEntry entry = new StoreEntry();
						entry.setDatas(bytes);
						entry.setTime(getLocalDateTime(msg));
						entries.add(entry);
					}
				}
			}
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
		}
		return entries;
	}

	public String byte2Hex(byte[] data) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < data.length; i++) {
			int value = Byte.toUnsignedInt(data[i]);
			String hex = Integer.toHexString(value);
			for (int j = 0; j < 2 - hex.length(); j++) {
				sb.append("0");
			}
			sb.append(hex);
		}
		return sb.toString();
	}

	private String joint(String... strings) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < strings.length; i++) {
			sb.append(strings[i]);
			if (i < strings.length - 1) {
				sb.append(SEPARATOR);
			}
		}
		sb.append(LINE_SEPARATOR);
		return sb.toString();
	}

	private String getDateTime(CommonMessage message) {
		ByteBuffer buffer = ByteBuffer.wrap(message.getDatas());
		int day = buffer.get(21);
		int hour = buffer.get(22);
		int minute = buffer.get(23);
		int second = buffer.get(25);
		int milli = message.getMilli();
		if (milli >= 1000) {
			milli = 999;
		}
		LocalDate date = LocalDate.of(message.getYear(), message.getMonth(), day);
		LocalTime time = LocalTime.of(hour, minute, second, milli * 100000);
		return date.format(DateTimeFormatter.ofPattern("yyyyMMdd")) + time.format(DateTimeFormatter.ofPattern("HHmmssSSS"));
	}

	private LocalDateTime getLocalDateTime(CommonMessage message) {
		ByteBuffer buffer = ByteBuffer.wrap(message.getDatas());
		int day = buffer.get(21);
		int hour = buffer.get(22);
		int minute = buffer.get(23);
		int second = buffer.get(25);
		LocalDate date = LocalDate.of(message.getYear(), message.getMonth(), day);
		LocalTime time = LocalTime.of(hour, minute, second);
		return LocalDateTime.of(date, time);
	}

	public static void main(String[] args) {
		byte[] data = new byte[] { 12, 22, 96, 0, 0, 21, 4, 0, 12, 4, 33, 4, 33, 1, 59, 1, 12, (byte) 250, 0, 9, 2, 1, 0, 0, 12, 0, 0, 0, 0, 0, 36,
				60 };
		OriginalMessageEncoder encoder = new OriginalMessageEncoder();
		String string = encoder.byte2Hex(data);
		System.out.println(string.toUpperCase());
	}

	@Override
	public String getHeader() {
		return HEADER_LINE;
	}
}
