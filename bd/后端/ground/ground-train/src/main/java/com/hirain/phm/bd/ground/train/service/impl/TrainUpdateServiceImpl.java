package com.hirain.phm.bd.ground.train.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirain.phm.bd.ground.train.domain.Train;
import com.hirain.phm.bd.ground.train.service.TrainService;
import com.hirain.phm.bd.ground.train.service.TrainUpdateService;
import com.hirain.phm.bd.message.train.OffsetMessage;
import com.hirain.phm.bd.message.train.TrainConfigMessage;
import com.hirain.phm.bd.message.train.WillMessage;
import com.hirain.phm.bode.core.ICar;
import com.hirain.phm.bode.core.ITrain;
import com.hirain.phm.bode.core.util.SystemInfoUtil;

@Service
public class TrainUpdateServiceImpl implements TrainUpdateService {

	@Autowired
	private TrainService trainService;

	@Override
	public void updateConfig(TrainConfigMessage message) {
		Train train = trainService.select(message.getProject(), message.getTrain());
		train.setConfigInfo(new String(message.getSetting()));
		try {
			ITrain info = SystemInfoUtil.convertXmlBytes2SystemInfo(message.getSetting());
			int count = 0;
			for (ICar car : info.getCars()) {
				count += car.getDoors().size();
			}
			System.err.println(message.getTrain() + ":" + count);
			train.setDoorCount(count);
		} catch (Exception e) {
			e.printStackTrace();
		}
		trainService.updateAll(train);
	}

	@Override
	public void upadateOnline(WillMessage message) {
		Train train = trainService.select(message.getProject(), message.getTrain());
		if (train != null) {
			train.setTrainOnline(message.getOn());
			trainService.updateNotNull(train);
		}
	}

	@Override
	public void updateOffset(OffsetMessage message) {
		Train train = trainService.select(message.getProject(), message.getTrain());
		if (train != null) {
			train.setOffset(message.getOffset());
			trainService.updateNotNull(train);
		}
	}

}
