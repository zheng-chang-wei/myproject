/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.life;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

import com.hirain.phm.bd.ground.common.event.StatisticsEvent;
import com.hirain.phm.bd.ground.common.event.TrainInitEvent;
import com.hirain.phm.bd.ground.life.domain.LifeWarning;
import com.hirain.phm.bd.ground.life.service.LifeService;
import com.hirain.phm.bd.ground.life.service.LifeWarningService;
import com.hirain.phm.bd.message.train.LifeMessage;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created May 28, 2019 9:57:22 AM
 * @Description
 *              <p>
 *              处理寿命信息报文
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               May 28, 2019 zepei.tao@hirain.com 1.0 create file
 */
@Configuration
public class LifeEventHandler {

	@Autowired
	private LifeWarningService lifeWarningService;

	@Autowired
	private LifeService lifeService;

	// @EventListener
	@Async
	public void handleTrainInit(TrainInitEvent event) {
		lifeService.insertLifeTrainInfo(event);
	}

	@EventListener
	@Async
	public void handleLifeMessage(LifeMessage message) {
		lifeService.handle(message);
	}

	@EventListener
	@Async
	public void onStatisticsEvent(StatisticsEvent event) {
		if (event.getTopCode() == 2) {
			LifeWarning warning = new LifeWarning();
			warning.setId(Integer.valueOf((int) event.getFaultId()));
			warning.setStatistics(event.isStatistics());
			lifeWarningService.updateNotNull(warning);
		}
	}

}
