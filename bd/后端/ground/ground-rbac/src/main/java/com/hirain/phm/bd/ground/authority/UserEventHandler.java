package com.hirain.phm.bd.ground.authority;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.hirain.phm.bd.ground.authority.service.TrainUserService;
import com.hirain.phm.bd.ground.common.event.TrainOnlineEvent;

@Configuration
public class UserEventHandler {

	@Autowired
	private TrainUserService service;

	// @EventListener
	public void onOnline(TrainOnlineEvent event) {
		service.updateTrainUser(event);
	}
}
