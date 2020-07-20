package com.hirain.phm.bd.ground.life.service.impl;

import static com.hirain.phm.bd.ground.life.domain.LifeDoorItemConstants.BUZZER;
import static com.hirain.phm.bd.ground.life.domain.LifeDoorItemConstants.CONTROLLER_CPU;
import static com.hirain.phm.bd.ground.life.domain.LifeDoorItemConstants.DRAWBAR;
import static com.hirain.phm.bd.ground.life.domain.LifeDoorItemConstants.ELECTRIC_MACHINERY;
import static com.hirain.phm.bd.ground.life.domain.LifeDoorItemConstants.ELECTROMAGNET;
import static com.hirain.phm.bd.ground.life.domain.LifeDoorItemConstants.EMERGENCY_UNLOCK_SWITCH;
import static com.hirain.phm.bd.ground.life.domain.LifeDoorItemConstants.IN_PLACE_SWITCH;
import static com.hirain.phm.bd.ground.life.domain.LifeDoorItemConstants.ISOLATION_SWITCH;
import static com.hirain.phm.bd.ground.life.domain.LifeDoorItemConstants.LOCKED_ROTOR;
import static com.hirain.phm.bd.ground.life.domain.LifeDoorItemConstants.LOCKING_BENT_LINK;
import static com.hirain.phm.bd.ground.life.domain.LifeDoorItemConstants.LOCK_SWITCH;
import static com.hirain.phm.bd.ground.life.domain.LifeDoorItemConstants.MOTOR_SWING_WHEEL;
import static com.hirain.phm.bd.ground.life.domain.LifeDoorItemConstants.ROLLER_BLOCK_COMPONENT;
import static com.hirain.phm.bd.ground.life.domain.LifeDoorItemConstants.ROTATING_COLUMN_ROLLER;
import static com.hirain.phm.bd.ground.life.domain.LifeDoorItemConstants.TIMING_PULLEY;
import static com.hirain.phm.bd.ground.life.domain.LifeDoorItemConstants.TOOTHED_BELT;
import static com.hirain.phm.bd.ground.life.domain.LifeDoorItemConstants.UNLOCK_DRAWBAR;
import static com.hirain.phm.bd.ground.life.domain.LifeDoorItemConstants.UNLOCK_ROTATING_FRAME;
import static com.hirain.phm.bd.ground.life.domain.LifeDoorItemConstants.UPPER_TRACK;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirain.phm.bd.ground.common.event.TrainInitEvent;
import com.hirain.phm.bd.ground.life.domain.LifeDoorItem;
import com.hirain.phm.bd.ground.life.domain.LifeItem;
import com.hirain.phm.bd.ground.life.domain.LifeProjectInfo;
import com.hirain.phm.bd.ground.life.domain.LifeTrainInfo;
import com.hirain.phm.bd.ground.life.domain.LifeWarning;
import com.hirain.phm.bd.ground.life.param.DoorItemLifeResult;
import com.hirain.phm.bd.ground.life.param.LifeMonitorRequest;
import com.hirain.phm.bd.ground.life.service.LifeDoorItemService;
import com.hirain.phm.bd.ground.life.service.LifeItemService;
import com.hirain.phm.bd.ground.life.service.LifeProjectInfoService;
import com.hirain.phm.bd.ground.life.service.LifeService;
import com.hirain.phm.bd.ground.life.service.LifeTrainInfoService;
import com.hirain.phm.bd.ground.life.service.LifeWarningService;
import com.hirain.phm.bd.ground.train.controller.TrainGateWay;
import com.hirain.phm.bd.ground.train.domain.Project;
import com.hirain.phm.bd.ground.train.domain.Train;
import com.hirain.phm.bd.message.train.LifeMessage;

@Service
public class LifeServiceImpl implements LifeService {

	@Autowired
	private LifeItemService lifeItemService;

	@Autowired
	private LifeTrainInfoService trainLifeService;

	@Autowired
	private LifeProjectInfoService lineProjectInfoService;

	@Autowired
	private LifeDoorItemService doorItemService;

	@Autowired
	private LifeWarningService lifeWarningService;

	@Autowired
	private TrainGateWay trainGW;

	@Override
	public List<DoorItemLifeResult> findItemLifeByDoor(LifeMonitorRequest request) {
		List<DoorItemLifeResult> results = new ArrayList<>();
		Train train = trainGW.selectTrain(request.getProject(), request.getTrainNo());

		List<LifeDoorItem> doorItemLifes = doorItemService.findDoorItemLifeInfos(train.getId(), request.getCarNo(), request.getDoorAddr());
		for (LifeDoorItem doorItemLife : doorItemLifes) {
			DoorItemLifeResult result = new DoorItemLifeResult();

			Integer lifeItemId = doorItemLife.getLifeitemId();
			LifeItem lifeItem = lifeItemService.findLifeItemById(lifeItemId);
			result.setItemName(lifeItem.getItemName());

			LifeTrainInfo trainLifeInfo = trainLifeService.findTrainLifeInfoByTrainIDItemId(train.getId(), lifeItemId);
			Integer referenceValue = trainLifeInfo.getReferenceValue();
			double lifePercentage = 0.0;
			if (referenceValue != 0) {
				lifePercentage = doorItemLife.getValue() * 1.0 / referenceValue;
				result.setReferenceValue(referenceValue);
			}
			result.setLifeValue(doorItemLife.getValue());
			result.setLifePercentage((int) lifePercentage * 100);
			results.add(result);
		}
		return results;
	}

	/**
	 * 向t_life_traininfo数据表中插入新注册列车对应的寿命项点数据
	 */
	@Override
	public void insertLifeTrainInfo(TrainInitEvent message) {
		// 根据lineID从t_linelife_info表中找到对应的数据集合
		Project project = trainGW.selectProjectByName(message.getProject());
		List<LifeProjectInfo> lineLifeInfos = lineProjectInfoService.findLifeInfos(project.getId());
		for (LifeProjectInfo lineLifeInfo : lineLifeInfos) {
			Integer lifeitemId = lineLifeInfo.getLifeitemId();
			LifeTrainInfo trainLifeInfo = new LifeTrainInfo();
			Train train = trainGW.selectTrain(message.getProject(), message.getTrain());
			trainLifeInfo.setTrainId(train.getId());
			trainLifeInfo.setLifeitemId(lifeitemId);
			trainLifeService.insertTrainLifeInfo(trainLifeInfo);
		}
	}

	/**
	 * @see com.hirain.phm.bd.ground.train.ILifeHandler#handle(com.hirain.phm.bd.message.train.LifeMessage)
	 */
	@Override
	public void handle(LifeMessage message) {
		Train train = trainGW.selectTrain(message.getProject(), message.getTrain());
		Project project = trainGW.selectProjectByName(message.getProject());

		List<LifeDoorItem> doorItemLifes = doorItemService.findDoorItemLifeInfos(train.getId(), message.getCarNo(), message.getDoorAddr());
		if (doorItemLifes == null || doorItemLifes.size() == 0) {// 数据表中暂没有该车门的寿命数据
			List<LifeProjectInfo> lineLifeInfos = lineProjectInfoService.findLifeInfos(project.getId());
			for (LifeProjectInfo lineLifeInfo : lineLifeInfos) {
				LifeDoorItem doorItemLife = new LifeDoorItem();
				doorItemLife.setTrainId(train.getId());
				doorItemLife.setCarNo(message.getCarNo());
				doorItemLife.setDoorAddr(message.getDoorAddr());
				Integer lifeitemId = lineLifeInfo.getLifeitemId();
				doorItemLife.setLifeitemId(lifeitemId);
				doorItemLife.setValue(increDoorItemValue(doorItemLife.getLifeitemId(), message, doorItemLife));

				doorItemService.insertDoorItemLifeData(doorItemLife);
			}
		} else {// 已存在该车门寿命数据
			for (LifeDoorItem doorItemLife : doorItemLifes) {
				Integer doorItemValue = increDoorItemValue(doorItemLife.getLifeitemId(), message, doorItemLife);
				doorItemLife.setValue(doorItemValue);
				doorItemService.updateDoorItemLifeInfo(doorItemLife);
				saveOrUpdateLifeWearning(doorItemLife, doorItemValue);
			}
		}
	}

	private Integer increDoorItemValue(Integer lifeItemID, LifeMessage message, LifeDoorItem doorItem) {
		Integer increment = getDoorItemIncrement(lifeItemID, message);
		if (doorItem != null && doorItem.getValue() != null) {
			return doorItem.getValue() + increment;
		} else {
			return increment;
		}
	}

	private void saveOrUpdateLifeWearning(LifeDoorItem doorItemLife, Integer doorItemValue) {
		Integer trainId = doorItemLife.getTrainId();
		Integer lifeitemId = doorItemLife.getLifeitemId();
		LifeTrainInfo info = trainLifeService.findTrainLifeInfoByTrainIDItemId(trainId, lifeitemId);
		Integer referenceValue = info.getReferenceValue();
		double remainderLife = 1 - (double) doorItemValue / referenceValue;
		// 如果剩余寿命小于20%,开始预警
		if (remainderLife <= 0.2) {
			// 如果剩余寿命小于0，将剩余寿命等于0
			if (remainderLife < 0) {
				remainderLife = 0;
			}
			LifeWarning lifeWarning = lifeWarningService.findLifeWarningByTrainIDItemId(trainId, lifeitemId);
			if (lifeWarning == null) {
				lifeWarning = new LifeWarning();
				lifeWarning.setCarNo(doorItemLife.getCarNo());
				lifeWarning.setDoorAddr(doorItemLife.getDoorAddr());
				lifeWarning.setLifeitemId(lifeitemId);
				lifeWarning.setRemainderLife(remainderLife);
				lifeWarning.setTrainId(trainId);
				lifeWarning.setWarningTime(new Date());
				// lifeWarning.setStatistics(statistics);
				// lifeWarning.setDebugMode(debugMode);
				lifeWarningService.save(lifeWarning);
			} else {
				lifeWarning.setRemainderLife(remainderLife);
				lifeWarningService.updateNotNull(lifeWarning);
			}
		}
	}

	/**
	 * @param lifeItemID
	 * @param message
	 * @return
	 */
	private Integer getDoorItemIncrement(Integer lifeItemID, LifeMessage message) {
		switch (lifeItemID) {
		case IN_PLACE_SWITCH:
		case UPPER_TRACK:
		case ROLLER_BLOCK_COMPONENT:
		case UNLOCK_ROTATING_FRAME:
		case TOOTHED_BELT:
		case ROTATING_COLUMN_ROLLER:
		case LOCKED_ROTOR:
		case DRAWBAR:
		case UNLOCK_DRAWBAR:
		case LOCKING_BENT_LINK:
		case MOTOR_SWING_WHEEL:
		case TIMING_PULLEY:
			return message.getDoorOpenFrequency() + message.getDoorCloseFrequency();
		case EMERGENCY_UNLOCK_SWITCH:
		case ELECTROMAGNET:
			return message.getEmergencyUnlockFrequency();
		case ISOLATION_SWITCH:
			return message.getDoorIsolationFrequency();
		case LOCK_SWITCH:
			return message.getLockSwitchFrenquency();
		case CONTROLLER_CPU:
			return message.getCommunicationTime();
		case ELECTRIC_MACHINERY:
			return message.getDoorOpenTime() + message.getDoorCloseTime();
		case BUZZER:
			return message.getDoorOpenFrequency() * 3 + message.getDoorCloseFrequency() * 2;
		default:
			return 0;
		}
	}

}
