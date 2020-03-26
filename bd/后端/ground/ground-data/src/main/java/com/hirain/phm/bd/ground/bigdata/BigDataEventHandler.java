/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.bigdata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.hirain.phm.bd.ground.bigdata.service.BigDataService;
import com.hirain.phm.bd.message.big.DataRecordList;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年6月5日 下午5:22:28
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年6月5日 jianwen.xin@hirain.com 1.0 create file
 */
@Configuration
@Component
public class BigDataEventHandler {

	@Autowired
	private BigDataService service;

	@EventListener
	@Async
	public void onRecord(DataRecordList recordList) {
		System.out.println(recordList);
		service.insertDataRecord(recordList.getRecords());
		service.updateStorageInfo();
	}
}
