/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.store.original.record;

import java.io.File;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;

import com.hirain.phm.bd.common.serialize.JsonUtil;
import com.hirain.phm.bd.message.big.DataRecord;
import com.hirain.phm.bd.message.big.DataRecordList;
import com.hirain.phm.bd.store.hdfs.UploadRecord;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年6月5日 下午5:07:50
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年6月5日 jianwen.xin@hirain.com 1.0 create file
 */
@Configuration
public class RecordSender {

	static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;

	@Value("${data.record.topic}")
	private String topic;

	@EventListener
	@Async
	public void send(UploadRecord record) {
		System.out.println(record);
		sendDataRecord(record.getPathList());
	}

	/**
	 * @param filepaths
	 */
	private void sendDataRecord(List<String> filepaths) {
		DataRecordList list = new DataRecordList();
		Map<String, DataRecord> cache = new HashMap<>();
		for (String filepath : filepaths) {
			DataRecord record = of(filepath);
			String flag = record.getCity() + "-" + record.getLine() + "-" + record.getLine();
			DataRecord dataRecord = cache.get(flag);
			if (dataRecord == null) {
				cache.put(flag, record);
			} else {
				try {
					Date d1 = sdf.parse(record.getDate());
					Date d2 = sdf.parse(dataRecord.getDate());
					if (d2.compareTo(d1) > 0) {
						cache.put(flag, dataRecord);
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		list.getRecords().addAll(cache.values());
		String string = JsonUtil.toString(list);
		System.out.println(string);
		kafkaTemplate.send(topic, string.getBytes());
	}

	/**
	 * @param filepath
	 *            xxx/project/train/date/time.xx
	 * @return
	 */
	public static DataRecord of(String filepath) {
		File file = new File(filepath);
		Path path = file.toPath();
		int len = path.getNameCount();
		DataRecord record = new DataRecord();
		record.setProject(path.getName(len - 4).toString());
		record.setTrain(path.getName(len - 3).toString());
		record.setDate(path.getName(len - 2).toString());
		return record;
	}
}
