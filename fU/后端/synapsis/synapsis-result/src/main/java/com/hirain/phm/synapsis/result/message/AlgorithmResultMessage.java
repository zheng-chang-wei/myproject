/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.result.message;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

import com.hirain.phm.synapsis.message.SynapsisResponse;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 19, 2020 6:28:40 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 19, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Data
public class AlgorithmResultMessage implements SynapsisResponse {

	private int code;

	private int fileType;

	private Date systemTime;

	private int longiDegree;

	private double longiMinute;

	private char longiDirection;

	private int latiDegree;

	private double latiMinute;

	private char latiDirection;

	private String protocolVersion;

	private int crc;

	private short headerType;

	private int id;

	private Date timestamp;

	private int supplier;

	private String algorithmVersion;

	private int lifeSignal;

	private int carriage;

	private int end;

	private int deviceSeq;

	private int subsystem;

	private int dataCrc;

	private String itemName;

	private int value;

	private static final ByteOrder BUFFER_ENDIAN = ByteOrder.BIG_ENDIAN;

	/**
	 * @see com.hirain.phm.synapsis.message.SynapsisResponse#parse(byte[])
	 */
	@Override
	public void parse(byte[] bs) {
		ByteBuffer buffer = ByteBuffer.wrap(bs).order(BUFFER_ENDIAN);
		setCode(buffer.getInt());
		// setFileType(buffer.get());
		buffer.get(new byte[8]);
		int commonLength = Byte.toUnsignedInt(buffer.get());
		byte[] headBs = new byte[commonLength - 3];
		buffer.get(headBs);
		parseHead(headBs);
		setCrc(Short.toUnsignedInt(buffer.getShort()));
		parseAlgorithm(buffer);
		parseValue(buffer);
	}

	/**
	 * @param buffer
	 */
	private void parseValue(ByteBuffer buffer) {
		buffer.get();
		byte[] nameBs = new byte[16];
		buffer.get(nameBs);
		String name = new String(nameBs, Charset.forName("utf-8"));
		setItemName(name.trim());
		setValue(Byte.toUnsignedInt(buffer.get()));
	}

	/**
	 * @param buffer
	 */
	private void parseAlgorithm(ByteBuffer buffer) {
		setHeaderType(buffer.getShort());
		setId(Short.toUnsignedInt(buffer.getShort()));
		buffer.get();
		setTimestamp(parseTimestamp(buffer));
		setSupplier(Byte.toUnsignedInt(buffer.get()));
		int first = Byte.toUnsignedInt(buffer.get());
		int second = Byte.toUnsignedInt(buffer.get());
		String version = Integer.toHexString(first) + "." + Integer.toHexString(second);
		setAlgorithmVersion(version);
		setLifeSignal(Short.toUnsignedInt(buffer.getShort()));
		byte b = buffer.get();
		int high = b >> 4 & 0x0f;
		int low = b & 0x0f;
		setCarriage(high);
		setEnd(low);
		setDeviceSeq(Byte.toUnsignedInt(buffer.get()));
		setSubsystem(Byte.toUnsignedInt(buffer.get()));
		setDataCrc(Short.toUnsignedInt(buffer.getShort()));
	}

	/**
	 * @param headBs
	 */
	private void parseHead(byte[] headBs) {
		ByteBuffer buffer = ByteBuffer.wrap(headBs).order(BUFFER_ENDIAN);
		setSystemTime(parseTimestamp(buffer));
		parseLongitude(buffer);
		parseLatitude(buffer);
		int first = Byte.toUnsignedInt(buffer.get());
		int second = Byte.toUnsignedInt(buffer.get());
		String version = Integer.toHexString(first) + "." + Integer.toHexString(second);
		setProtocolVersion("V" + version);
	}

	/**
	 * @param buffer
	 */
	private void parseLatitude(ByteBuffer buffer) {
		byte[] bs = new byte[4];
		buffer.get(bs);
		String string = bs2str(bs);
		setLatiDegree(getDegreePart(string));
		setLatiMinute(getMinutePart(string));
		setLatiDirection((char) buffer.get());
	}

	/**
	 * @param buffer
	 */
	private void parseLongitude(ByteBuffer buffer) {
		byte[] bs = new byte[4];
		buffer.get(bs);
		String string = bs2str(bs);
		setLongiDegree(getDegreePart(string));
		setLongiMinute(getMinutePart(string));
		setLongiDirection((char) buffer.get());
	}

	/**
	 * @param string
	 * @return
	 */
	private int getDegreePart(String string) {
		return Integer.parseInt(string.substring(0, string.length() - 5));
	}

	/**
	 * @param string
	 * @return
	 */
	private double getMinutePart(String string) {
		double value = Double.parseDouble(string.substring(string.length() - 5));
		return value / 1000;
	}

	/**
	 * @param bs
	 */
	private String bs2str(byte[] bs) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < bs.length; i++) {
			int value = Byte.toUnsignedInt(bs[i]);
			if (value < 10) {
				sb.append("0");
			}
			sb.append(value);
		}
		return sb.toString();
	}

	/**
	 * @param buffer
	 * @return
	 */
	private Date parseTimestamp(ByteBuffer buffer) {
		int year = buffer.get() + 2000;
		int month = buffer.get();
		int day = buffer.get();
		int hour = buffer.get();
		int minute = buffer.get();
		int second = buffer.get();
		int milli = Short.toUnsignedInt(buffer.getShort());
		LocalDateTime dateTime = LocalDateTime.of(year, month, day, hour, minute, second, milli * 1000000);
		ZoneId zoneId = ZoneId.systemDefault();
		ZonedDateTime zdt = dateTime.atZone(zoneId);
		Date date = Date.from(zdt.toInstant());
		return date;
	}

}
