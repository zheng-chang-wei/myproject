/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.store.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.hirain.phm.bd.common.serialize.JsonUtil;
import com.hirain.phm.bd.data.bean.DataRecord;
import com.hirain.phm.bd.message.data.RecordData;
import com.hirain.phm.bd.message.decode.DecodePacket;
import com.hirain.phm.bd.message.decode.RunDataFrame;
import com.hirain.phm.bd.store.service.MessageUnpacker;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Mar 5, 2020 11:24:17 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Mar 5, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Component
@Slf4j
public class MessageUnpackerImpl implements MessageUnpacker {

	private static String CARRIAGE = "车厢号";

	private static String DOOR = "门地址";

	private static String TIMESTAMP = "时间";

	private static String OPENING = "开门过程";

	private static String CLOSING = "关门过程";

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");

	/**
	 * @see com.hirain.phm.bd.store.service.MessageUnpacker#unpack(com.hirain.phm.bd.message.decode.DecodePacket)
	 */
	@Override
	public List<DataRecord> unpack(DecodePacket packet) {
		Map<String, Map<String, DoorPacket>> cache = new HashMap<>();
		List<DataRecord> records = new ArrayList<>();
		List<String> keys = packet.getKeys();
		int cindex = keys.indexOf(CARRIAGE);
		int dindex = keys.indexOf(DOOR);
		int openIndex = keys.indexOf(OPENING);
		int closeIndex = keys.indexOf(CLOSING);
		for (RunDataFrame frame : packet.getFrames()) {
			String carriageId = frame.getValues().get(cindex);
			String doorId = frame.getValues().get(dindex);
			DoorPacket doorPacket = getDoorPacket(cache, carriageId, doorId);
			doorPacket.getFrames().add(frame);
		}
		for (String carriage : cache.keySet()) {
			int carriageId = Integer.parseInt(carriage);
			Map<String, DoorPacket> map = cache.get(carriage);
			for (String door : map.keySet()) {
				try {
					int doorId = Integer.parseInt(door);
					DoorPacket doorPacket = map.get(door);
					DataRecord record = new DataRecord();
					record.setCarriageId(carriageId);
					record.setDoorId(doorId);
					record.setProject(packet.getProject());
					record.setTrain(packet.getTrain());
					record.setTimestamp(getTimestamp(keys, doorPacket.getFrames().get(0)));
					RecordData data = new RecordData();
					data.setKeys(keys);
					data.setFrames(doorPacket.getFrames());
					record.setData(JsonUtil.toString(data));
					record.setState(getState(data.getFrames().get(0), openIndex, closeIndex));
					records.add(record);
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
			}
		}
		Collections.sort(records, (o1, o2) -> o1.getTimestamp().compareTo(o2.getTimestamp()));
		return records;
	}

	private Date getTimestamp(List<String> keys, RunDataFrame frames) throws ParseException {
		int index = keys.indexOf(TIMESTAMP);
		String time = frames.getValues().get(index);
		return sdf.parse(time);
	}

	/**
	 * @param frame
	 * @param openIndex
	 * @param closeIndex
	 * @return
	 */
	private int getState(RunDataFrame frame, int openIndex, int closeIndex) {
		String open = frame.getValues().get(openIndex);
		String close = frame.getValues().get(closeIndex);
		if (open.equals("1")) {
			return DataRecord.OPEN_STATE;
		} else if (close.equals("1")) {
			return DataRecord.CLOSE_STATE;
		}
		return 0;
	}

	/**
	 * @param cache
	 * @param carriageId
	 * @param doorId
	 */
	private DoorPacket getDoorPacket(Map<String, Map<String, DoorPacket>> cache, String carriageId, String doorId) {
		Map<String, DoorPacket> map = cache.get(carriageId);
		if (map == null) {
			map = new HashMap<>();
			cache.put(carriageId, map);
		}
		DoorPacket packet = map.get(doorId);
		if (packet == null) {
			packet = new DoorPacket();
			packet.setDoorId(Integer.parseInt(doorId));
			packet.setFrames(new ArrayList<RunDataFrame>());
			map.put(doorId, packet);
		}
		return packet;
	}

	@Data
	class DoorPacket {

		private int doorId;

		private List<RunDataFrame> frames;
	}

}
