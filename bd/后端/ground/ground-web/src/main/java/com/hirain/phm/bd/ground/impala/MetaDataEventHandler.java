/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.impala;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

import com.hirain.phm.bd.data.impala.dao.DataMapper;
import com.hirain.phm.bd.message.data.MetaDataEvent;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Mar 12, 2020 10:53:09 AM
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

	@Autowired
	private DataMapper mapper;

	@EventListener
	@Async
	public void on(MetaDataEvent event) {
		mapper.refresh(event.getTableName(), event.getPartition());
	}
}
