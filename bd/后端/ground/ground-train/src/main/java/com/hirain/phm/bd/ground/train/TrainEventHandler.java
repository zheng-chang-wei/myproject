package com.hirain.phm.bd.ground.train;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

import com.hirain.phm.bd.ground.common.event.TrainOnlineEvent;
import com.hirain.phm.bd.ground.train.service.TrainStorageInfoService;
import com.hirain.phm.bd.ground.train.service.TrainUpdateService;
import com.hirain.phm.bd.message.train.OffsetMessage;
import com.hirain.phm.bd.message.train.RegisterMessage;
import com.hirain.phm.bd.message.train.StorageMessage;
import com.hirain.phm.bd.message.train.TrainConfigMessage;
import com.hirain.phm.bd.message.train.WillMessage;

/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年5月10日 下午6:20:18
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年5月10日 jianwen.xin@hirain.com 1.0 create file
 */
@Configuration
public class TrainEventHandler {

	@Autowired
	private IRegisterHandler registerHandler;

	@Autowired
	private TrainUpdateService updateService;

	@Autowired
	private TrainStorageInfoService storeInfoService;

	@EventListener
	@Async
	public void handleRegister(RegisterMessage message) {
		registerHandler.handle(message);
	}

	@EventListener
	@Async
	public void handleConfigMessage(TrainConfigMessage message) {
		updateService.updateConfig(message);
	}

	// @EventListener
	// @Async
	public void handleOnline(WillMessage message) {
		System.out.println(message);
		updateService.upadateOnline(message);
	}

	@EventListener
	@Async
	public void handleOffset(OffsetMessage message) {
		updateService.updateOffset(message);
	}

	@EventListener
	@Async
	public void handleStorage(StorageMessage message) {
		storeInfoService.handleStorageMessage(message);
	}

	@EventListener
	@Async
	public void handleTrainOnline(TrainOnlineEvent event) {
		storeInfoService.updateTrainInfo(event);
	}

}
