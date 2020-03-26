/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.store.decode.message;

import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hirain.phm.bd.common.serialize.JsonUtil;
import com.hirain.phm.bd.message.decode.DecodePacket;
import com.hirain.phm.bd.message.decode.RunDataFrame;
import com.hirain.phm.bd.store.domain.StoreEntry;
import com.hirain.phm.bd.store.service.IMessageEncoder;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年5月21日 上午11:14:17
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年5月21日 jianwen.xin@hirain.com 1.0 create file
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Slf4j
public class DecodeMessageEncoder implements IMessageEncoder {

	static String SEPARATOR = ",";

	static String LINE_SEPARATOR = System.getProperty("line.separator");

	static String CARRAIGE_KEY = "车厢号";

	static String DOOR_KEY = "门地址";

	static String DATE_KEY = "时间";

	static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS");

	@Getter
	private String header;

	/**
	 * @see com.hirain.phm.bd.store.service.IMessageEncoder#encode(java.lang.String)
	 */
	@Override
	public List<StoreEntry> encode(byte[] payload) {
		String message = new String(payload, Charset.forName("utf-8"));
		List<StoreEntry> entries = new ArrayList<>();
		try {
			DecodePacket packet = JsonUtil.fromString(message, DecodePacket.class);
			List<String> keys = packet.getKeys();
			if (header == null) {
				generateHeader(keys);
			}
			for (RunDataFrame frame : packet.getFrames()) {
				StringBuilder sb = new StringBuilder();
				List<String> values = frame.getValues();
				String timestamp = getTimestamp(values, keys.indexOf(DATE_KEY));
				String carriageId = getCarriageId(values, keys.indexOf(CARRAIGE_KEY));
				String doorAddr = getDoorAddr(values, keys.indexOf(DOOR_KEY));
				sb.append(timestamp).append(SEPARATOR);
				sb.append(carriageId).append(SEPARATOR);
				sb.append(doorAddr).append(SEPARATOR);
				for (int i = 0; i < values.size(); i++) {
					sb.append(values.get(i));
					if (i < values.size() - 1) {
						sb.append(SEPARATOR);
					} else {
						sb.append(LINE_SEPARATOR);
					}
				}
				byte[] bytes = sb.toString().getBytes(Charset.forName("utf-8"));
				StoreEntry entry = new StoreEntry();
				entry.setDatas(bytes);
				entry.setTime(LocalDateTime.parse(timestamp, formatter));
				entries.add(entry);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return entries;
	}

	private void generateHeader(List<String> keys) {
		StringBuilder sb = new StringBuilder();
		sb.append("时间").append(SEPARATOR);
		sb.append("车厢号").append(SEPARATOR);
		sb.append("门地址").append(SEPARATOR);
		for (int i = 0; i < keys.size(); i++) {
			sb.append(keys.get(i));
			if (i < keys.size() - 1) {
				sb.append(SEPARATOR);
			} else {
				sb.append(LINE_SEPARATOR);
			}
		}
		header = sb.toString();
	}

	private String getTimestamp(List<String> values, int index) {
		return values.get(index);
	}

	private String getCarriageId(List<String> values, int index) {
		return values.get(index);
	}

	/**
	 * @param values
	 * @param index
	 * @return
	 */
	private String getDoorAddr(List<String> values, int index) {
		return values.get(index);
	}
}
