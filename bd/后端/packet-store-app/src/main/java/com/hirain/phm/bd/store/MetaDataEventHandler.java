/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;

import com.hirain.phm.bd.common.serialize.JsonUtil;
import com.hirain.phm.bd.data.hive.event.RefreshMetadataEvent;
import com.hirain.phm.bd.message.data.MetaDataEvent;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Mar 12, 2020 10:28:58 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Mar 12, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Configuration
public class MetaDataEventHandler {

	@Value("${data.record.topic:big-data-record}")
	private String topic;

	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;

	@EventListener
	@Async
	public void listen(RefreshMetadataEvent event) {
		MetaDataEvent metaDataEvent = new MetaDataEvent(event.getTableName(), event.getPartition());
		String json = JsonUtil.toString(metaDataEvent);
		kafkaTemplate.send(topic, json.toString().getBytes());
	}
}
