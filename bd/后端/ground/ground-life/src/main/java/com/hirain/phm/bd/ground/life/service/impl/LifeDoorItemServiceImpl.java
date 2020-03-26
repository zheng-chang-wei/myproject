/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.life.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirain.phm.bd.ground.common.service.BaseService;
import com.hirain.phm.bd.ground.life.dao.LifeDoorItemMapper;
import com.hirain.phm.bd.ground.life.domain.LifeDoorItem;
import com.hirain.phm.bd.ground.life.param.LifeDoorItemAVG;
import com.hirain.phm.bd.ground.life.service.LifeDoorItemService;
import com.hirain.phm.bd.ground.train.controller.TrainGateWay;
import com.hirain.phm.bd.ground.train.domain.Train;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created May 24, 2019 10:50:40 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               May 24, 2019 zepei.tao@hirain.com 1.0 create file
 */
@Service
public class LifeDoorItemServiceImpl extends BaseService<LifeDoorItem> implements LifeDoorItemService {

	@Autowired
	private LifeDoorItemMapper mapper;

	@Autowired
	private TrainGateWay trainGW;

	/**
	 * @see com.hirain.phm.bd.ground.life.service.LifeDoorItemService#findDoorItemLifeInfos(java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<LifeDoorItem> findDoorItemLifeInfos(Integer trainId, Integer carNo, Integer doorAddr) {
		List<LifeDoorItem> doorItemLifes = mapper.findDoorItemLifes(trainId, carNo, doorAddr);
		return doorItemLifes;
	}

	/**
	 * @see com.hirain.phm.bd.ground.life.service.LifeDoorItemService#findDoorItemLifeInfo(java.lang.Integer, java.lang.Integer, java.lang.Integer,
	 *      java.lang.Integer)
	 */
	@Override
	public LifeDoorItem findDoorItemLifeInfo(Integer trainId, Integer carNo, Integer doorAddr, Integer lifeitemId) {
		LifeDoorItem doorItemLife = mapper.findDoorItemLife(trainId, carNo, doorAddr, lifeitemId);
		return doorItemLife;
	}

	/**
	 * @see com.hirain.phm.bd.ground.life.service.LifeDoorItemService#insertDoorItemLifeData()
	 */
	@Override
	public void insertDoorItemLifeData(LifeDoorItem doorItemLife) {
		mapper.insert(doorItemLife);
	}

	/**
	 * @see com.hirain.phm.bd.ground.life.service.LifeDoorItemService#updateDoorItemLifeInfo(com.hirain.phm.bd.ground.life.domain.LifeDoorItem)
	 */
	@Override
	public void updateDoorItemLifeInfo(LifeDoorItem doorItemLife) {
		mapper.updateDoorItemLifeData(doorItemLife);
	}

	@Override
	public List<LifeDoorItemAVG> findDoorItemAVGValue(String project, String trainNo) {
		Train train = trainGW.selectTrain(project, trainNo);
		List<LifeDoorItemAVG> lifeDoorItemAVGs = mapper.findDoorItemAVGValue(train.getId());
		for (LifeDoorItemAVG lifeDoorItemAVG : lifeDoorItemAVGs) {
			lifeDoorItemAVG.setTime(new Date());
		}
		return lifeDoorItemAVGs;
	}

}
