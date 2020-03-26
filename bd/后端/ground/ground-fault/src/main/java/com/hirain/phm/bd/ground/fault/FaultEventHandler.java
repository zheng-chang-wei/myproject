/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.fault;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

import com.hirain.phm.bd.ground.common.event.StatisticsEvent;
import com.hirain.phm.bd.ground.fault.domain.FaultDetail;
import com.hirain.phm.bd.ground.fault.service.FaultDetailService;
import com.hirain.phm.bd.message.train.FaultMessage;
import com.hirain.phm.bd.message.train.FaultPacket;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年5月10日 下午6:28:54
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年5月10日 jianwen.xin@hirain.com 1.0 create file
 */
@Configuration
public class FaultEventHandler {

	@Autowired
	private FaultDetailService service;

	@EventListener
	@Async
	public void handleFault(FaultPacket message) {
		List<FaultMessage> messages = message.getMessages();
		for (FaultMessage fault : messages) {
			service.handleFaultMessage(message, fault);
		}
	}

	@EventListener
	@Async
	public void handleStatisticsEvent(StatisticsEvent event) {
		if (event.getTopCode() == 0) {
			FaultDetail detail = new FaultDetail();
			detail.setId(event.getFaultId());
			detail.setStatistics(event.isStatistics());
			service.updateNotNull(detail);
		}
	}
}
