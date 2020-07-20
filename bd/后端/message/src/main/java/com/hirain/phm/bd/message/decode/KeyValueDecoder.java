/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.message.decode;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.hirain.phm.bd.message.CommonMessage;

import lombok.extern.slf4j.Slf4j;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年5月17日 上午9:27:22
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年5月17日 jianwen.xin@hirain.com 1.0 create file
 */
@Slf4j
public class KeyValueDecoder implements IDoorMessageDecoder {

	public static List<String> keys;

	public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS");

	static {
		ClassLoader loader = KeyValueDecoder.class.getClassLoader();
		InputStream stream = loader.getResourceAsStream("key.txt");
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream, Charset.forName("utf-8")));
		keys = new ArrayList<>();
		String line;
		try {
			while ((line = reader.readLine()) != null) {
				keys.add(line);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			try {
				reader.close();
				stream.close();
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
	}

	@Override
	public RunDataFrame decode(CommonMessage message) {
		byte[] datas = message.getDatas();
		if (datas.length != 32) {
			log.info("door message length error");
			return null;
		}
		RunDataFrame frame = new RunDataFrame();
		ByteBuffer buffer = ByteBuffer.wrap(datas).order(ByteOrder.LITTLE_ENDIAN);
		frame.setId(buffer.get());// b0
		frame.setType(buffer.get());// b1:软件版本号

		{
			byte b2 = buffer.get();// b2
			for (int i = 0; i < 8; i++) {
				frame.getValues().add(str(int2Binary(b2 >> i)));
			} // 8
		}
		{
			byte b3 = buffer.get();// b3
			for (int i = 2; i < 8; i++) {
				frame.getValues().add(str(int2Binary(b3 >> i)));
			} // 14
		}
		{
			byte b4 = buffer.get();// b4
			for (int i = 0; i < 8; i++) {
				frame.getValues().add(str(int2Binary(b4 >> i)));
			} // 22
		}
		{
			byte b5 = buffer.get();// b5
			for (int i = 0; i < 8; i++) {
				frame.getValues().add(str(int2Binary(b5 >> i)));
			} // 30
		}
		{
			byte b6 = buffer.get();// b6
			for (int i = 0; i < 8; i++) {
				frame.getValues().add(str(int2Binary(b6 >> i)));
			} // 38
		}
		{
			byte b7 = buffer.get();// b7
			for (int i = 0; i < 3; i++) {
				frame.getValues().add(str(int2Binary(b7 >> i)));
			} // 41
		}
		buffer.get();// b8
		{
			byte b9 = buffer.get();// b9
			for (int i = 0; i < 6; i++) {
				frame.getValues().add(str(int2Binary(b9 >> i)));
			} // 47
		}
		frame.getValues().add(str(buffer.get()));// b10,48
		byte b11 = buffer.get();
		int high = b11 >> 4 & 0x0F;
		int low = b11 & 0x0F;
		frame.getValues().add(high + "." + low);// b11,49
		{
			byte b12 = buffer.get();// b12
			frame.getValues().add(str(b12 >> 4 & 0x0F));// 50
			frame.getValues().add(str(b12 & 0x0F));// 51
		}
		frame.getValues().add(str(Byte.toUnsignedInt(buffer.get())));// b13,52
		int openTime = buffer.order(ByteOrder.LITTLE_ENDIAN).getShort() * 10;
		frame.getValues().add(str(openTime));// b14,15,53
		buffer.get();// b16
		int closeTime = buffer.order(ByteOrder.LITTLE_ENDIAN).getShort() * 10;
		frame.getValues().add(str(closeTime));// b17,18,54
		frame.getValues().add(str(buffer.order(ByteOrder.LITTLE_ENDIAN).getShort()));// b19,b20,55
		int day = Byte.toUnsignedInt(buffer.get());// b21
		int hour = Byte.toUnsignedInt(buffer.get());// b22
		int minute = Byte.toUnsignedInt(buffer.get());// b23
		buffer.get();// b24
		int second = Byte.toUnsignedInt(buffer.get());// b25
		short value = buffer.order(ByteOrder.LITTLE_ENDIAN).getShort();
		int milli = Short.toUnsignedInt(value);// b26,27
		frame.getValues().add(getDate(day, hour, minute, second, milli, message));// 56 报文时间
		frame.getValues().add(str(Byte.toUnsignedInt(buffer.get())));// b28,57
		buffer.get();// b29
		frame.getValues().add(str(buffer.getShort()));// b30,31,58

		String date = getDate(day, hour, minute, second, message.getMilli(), message);
		frame.getValues().add(date);// 59 时间
		return frame;

	}

	private String str(Object object) {
		return String.valueOf(object);
	}

	private int int2Binary(int i) {
		return i & 0x01;
	}

	private String getDate(int day, int hour, int minute, int second, int milli, CommonMessage message) {
		if (milli >= 1000) {
			milli = 999;
		}
		LocalDateTime dateTime = LocalDateTime.of(message.getYear(), message.getMonth(), day, hour, minute, second, milli * 1000000);
		return dateTime.format(formatter);
	}

}
