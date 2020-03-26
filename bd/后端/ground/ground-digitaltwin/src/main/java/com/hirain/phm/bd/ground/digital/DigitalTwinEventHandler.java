/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.digital;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

import com.hirain.phm.bd.common.serialize.JsonUtil;
import com.hirain.phm.bd.ground.common.event.NoSidEvent;
import com.hirain.phm.bd.ground.digital.domain.DigitalTwinPacket;
import com.hirain.phm.bd.ground.digital.service.DigitalTwinService;
import com.hirain.phm.bd.ground.util.GroundAccessHelper;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Sep 25, 2019 10:25:53 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Sep 25, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Configuration
public class DigitalTwinEventHandler {

	@Autowired
	private DigitalTwinService service;

	@EventListener
	@Async
	public void onEvent(NoSidEvent event) {
		if (event.getTopic().equals(GroundAccessHelper.DIGITAL_TWIN_KAFKA_TOPIC)) {
			DigitalTwinPacket packet = JsonUtil.fromString(event.getValue(), DigitalTwinPacket.class);
			service.update(packet);
		}
	}

}
