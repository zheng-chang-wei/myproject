package com.hirain.phm.bd.ground.life.service;

import java.util.List;

import com.hirain.phm.bd.ground.common.event.TrainInitEvent;
import com.hirain.phm.bd.ground.life.param.DoorItemLifeResult;
import com.hirain.phm.bd.ground.life.param.LifeMonitorRequest;
import com.hirain.phm.bd.message.train.LifeMessage;

public interface LifeService {

	List<DoorItemLifeResult> findItemLifeByDoor(LifeMonitorRequest request);

	/**
	 * @param message
	 */
	void handle(LifeMessage message);

	/**
	 * @param message
	 */
	void insertLifeTrainInfo(TrainInitEvent event);
}
