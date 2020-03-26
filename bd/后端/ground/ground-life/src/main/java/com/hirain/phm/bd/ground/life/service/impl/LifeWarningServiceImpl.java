/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.life.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirain.phm.bd.ground.common.service.BaseService;
import com.hirain.phm.bd.ground.life.dao.LifeWarningMapper;
import com.hirain.phm.bd.ground.life.domain.LifeItem;
import com.hirain.phm.bd.ground.life.domain.LifeWarning;
import com.hirain.phm.bd.ground.life.param.LifeWarningParam;
import com.hirain.phm.bd.ground.life.param.TodayLifeWarning;
import com.hirain.phm.bd.ground.life.service.LifeWarningService;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created May 29, 2019 5:17:40 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               May 29, 2019 zepei.tao@hirain.com 1.0 create file
 */
@Service
public class LifeWarningServiceImpl extends BaseService<LifeWarning> implements LifeWarningService {

	@Autowired
	private LifeWarningMapper mapper;

	/**
	 * @see com.hirain.phm.bd.ground.life.service.LifeWarningService#findLifeWarningsByParam(com.hirain.phm.bd.ground.life.param.LifeWarningParam)
	 */
	@Override
	public List<LifeWarningParam> findLifeWarningsByParam(LifeWarningParam lifeWarningParam) {
		return mapper.findLifeWarningsByParams(lifeWarningParam);
	}

	/**
	 * @see com.hirain.phm.bd.ground.life.service.LifeWarningService#downloadWarningData()
	 */
	@Override
	public void downloadWarningData() {
		// TODO Auto-generated method stub

	}

	@Override
	public List<LifeItem> selectAllGroupByLifeItemName() {
		return mapper.selectAllGroupByParam();
	}

	/**
	 * @see com.hirain.phm.bd.ground.life.service.LifeWarningService#findLifeItems(java.lang.String)
	 */
	@Override
	public List<LifeItem> findLifeItems(String project) {
		return mapper.findLifeItemsByProject(project);
	}

	@Override
	public LifeWarning findLifeWarningByTrainIDItemId(Integer trainID, Integer itemID) {
		return mapper.findLifeWarningByTrainIDItemId(trainID, itemID);
	}

	@Override
	public List<TodayLifeWarning> listLifeWarningToday(String project, String trainNo) {
		return mapper.listLifeWarningToday(project, trainNo);
	}

	/** 
	 * @see com.hirain.phm.bd.ground.life.service.LifeWarningService#listLifeWarningToday()
	 */
	@Override
	public List<TodayLifeWarning> listLifeWarningToday() {
		String todayDate = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now());
		String startTime = todayDate + " 00:00:00";
		String endTime = todayDate + " 23:59:59";
		return mapper.listLifeWarningToday4Bode(startTime, endTime);
	}

}
