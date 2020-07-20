/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/

package com.hirain.phm.bd.ground.subhealth.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.hirain.phm.bd.ground.common.event.FaultTopType;
import com.hirain.phm.bd.ground.common.event.UnifiedFaultRecord;
import com.hirain.phm.bd.ground.subhealth.domain.SubhealthDetail;
import com.hirain.phm.bd.ground.subhealth.domain.SubhealthInfo;
import com.hirain.phm.bd.ground.subhealth.service.SubhealthAnalyseService;
import com.hirain.phm.bd.ground.subhealth.service.SubhealthDataService;
import com.hirain.phm.bd.ground.subhealth.service.SubhealthDetailService;
import com.hirain.phm.bd.ground.subhealth.service.SubhealthInfoService;
import com.hirain.phm.bd.ground.train.controller.TrainGateWay;
import com.hirain.phm.bd.ground.train.domain.Train;
import com.hirain.phm.bd.message.train.SubhealthPacket;

/**
 * @Version 1.0
 * @Author weijia.kong@hirain.com
 * @Created May 30, 2019 4:07:30 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               May 30, 2019 weijia.kong@hirain.com 1.0 create file
 */
@Service
public class SubhealthAnalyseServiceImpl implements SubhealthAnalyseService {

	@Autowired
	private TrainGateWay trainGW;

	@Autowired
	private SubhealthDetailService detailService;

	@Autowired
	private SubhealthInfoService subhealthInfoService;

	@Autowired
	private SubhealthDataService dataService;

	/*
	 * (non-Javadoc)
	 * @see com.hirain.phm.bd.ground.subhealth.service.SubhealthAnalyseService#analyse(com.hirain.phm.bd.message.algorithm.SubhealthPacket)
	 */
	@Override
	public List<SubhealthDetail> analyse(SubhealthPacket packet) {
		Train train = trainGW.selectTrain(packet.getProject(), packet.getTrain());
		List<SubhealthDetail> result = new ArrayList<>();
		for (Integer item : packet.getSubhealthItems()) {
			if (item == 0) {
				continue;
			}
			SubhealthDetail detail = new SubhealthDetail();
			detail.setTrainId(train.getId());
			detail.setCarNo(packet.getCarID());
			detail.setDoorAddr(packet.getDoorID());
			detail.setSubhealthInfoId(item);
			detail.setStartTime(packet.getStartTime());
			detail.setEndTime(packet.getEndTime());
			detail.setDebugMode(packet.isDebug());
			result.add(detail);
		}
		if (!result.isEmpty()) {
			detailService.insertList(result);
		}
		saveSubhealthData(result, packet);
		return result;
	}

	/**
	 * @param result
	 * @param packet
	 */
	private void saveSubhealthData(List<SubhealthDetail> details, SubhealthPacket packet) {
		String keys = JSONObject.toJSONString(packet.getKeys());
		String datas = JSONObject.toJSONString(packet.getDatas());
		for (SubhealthDetail detail : details) {
			dataService.saveData(detail.getId(), packet.getState(), keys, datas);
		}
	}

	@Override
	public UnifiedFaultRecord getUnifiedFaultRecord(SubhealthDetail subhealthDetail) {
		UnifiedFaultRecord record = new UnifiedFaultRecord();
		record.setType(FaultTopType.SubHealth);
		record.setId(Long.valueOf(subhealthDetail.getId()));
		record.setTime(subhealthDetail.getStartTime());
		SubhealthInfo info = subhealthInfoService.selectByKey(subhealthDetail.getSubhealthInfoId());
		record.setFaultName(info.getSubhealthName());
		record.setCode(info.getSubhealthCode());
		Train train = trainGW.findTrainById(subhealthDetail.getTrainId());
		record.setTrainId(train.getTrainNo());
		record.setProjectId(train.getProjectId());

		record.setCarriageId(subhealthDetail.getCarNo());
		record.setDoorId(subhealthDetail.getDoorAddr());
		return record;
	}

}
