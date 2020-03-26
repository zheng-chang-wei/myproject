/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.store.decode.record;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;

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
// @Configuration
public class RecordSender {

	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;

	@Value("${data.record.topic}")
	private String topic;

	// @EventListener
	// @Async
	public void send(UploadRecord record) {
		System.out.println(record);
		sendDataRecord(record.getPathList());
	}

	/**
	 * @param filepaths
	 */
	private void sendDataRecord(List<String> filepaths) {
		DataRecordList list = new DataRecordList();
		for (String filepath : filepaths) {
			String[] split = filepath.split("\\\\");
			int len = split.length;
			DataRecord record = new DataRecord();
			record.setCity(split[len - 5]);
			record.setLine(split[len - 4]);
			record.setTrain(split[len - 3]);
			record.setDate(split[len - 2]);
			String time = split[len - 1];
			int index = time.indexOf(".");
			record.setTime(time.substring(0, index));
			list.getRecords().add(record);
		}
		String string = JsonUtil.toString(list);
		System.out.println(string);
		kafkaTemplate.send(topic, string.getBytes());
	}

}
