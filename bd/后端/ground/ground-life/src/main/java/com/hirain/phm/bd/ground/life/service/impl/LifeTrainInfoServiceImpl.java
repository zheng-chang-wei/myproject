/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.life.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirain.phm.bd.ground.common.service.BaseService;
import com.hirain.phm.bd.ground.life.dao.LifeTrainInfoMapper;
import com.hirain.phm.bd.ground.life.domain.LifeTrainInfo;
import com.hirain.phm.bd.ground.life.service.LifeTrainInfoService;

/**
 * @Version 1.0   
 * @Author zepei.tao@hirain.com  
 * @Created May 24, 2019 10:53:34 AM  
 * @Description
 * <p>
 * @Modification
 * <p>Date         Author      Version     Description  
 * <p>May 24, 2019     zepei.tao@hirain.com             1.0        create file 
 */
@Service
public class LifeTrainInfoServiceImpl extends BaseService<LifeTrainInfo> implements LifeTrainInfoService {

	@Autowired
	private LifeTrainInfoMapper mapper;

	/** 
	 * @see com.hirain.phm.bd.ground.life.service.LifeTrainInfoService#findTrainLifeInfoByTrainID(java.lang.Integer)
	 */
	@Override
	public List<LifeTrainInfo> findTrainLifeInfoByTrainID(Integer trainID) {
		List<LifeTrainInfo> trainLifeInfos = mapper.findTrainLifeInfosByTrainID(trainID);
		return trainLifeInfos;

	}

	/** 
	 * @see com.hirain.phm.bd.ground.life.service.LifeTrainInfoService#findTrainLifeInfoByTrainIDItemId(java.lang.Integer)
	 */
	@Override
	public LifeTrainInfo findTrainLifeInfoByTrainIDItemId(Integer trainID, Integer itemID) {
		return mapper.findTrainLifeInfoByTrainIDItemID(trainID, itemID);
	}

	/** 
	 * @see com.hirain.phm.bd.ground.life.service.LifeTrainInfoService#insertTrainLifeInfo(com.hirain.phm.bd.ground.life.domain.LifeTrainInfo)
	 */
	@Override
	public void insertTrainLifeInfo(LifeTrainInfo trainLifeInfo) {
		mapper.insert(trainLifeInfo);
	}

	/** 
	 * @see com.hirain.phm.bd.ground.life.service.LifeTrainInfoService#updateTrainLifeInfo(com.hirain.phm.bd.ground.life.domain.LifeTrainInfo)
	 */
	@Override
	public void updateTrainLifeInfo(LifeTrainInfo trainLifeInfo) {
		mapper.updateTrainLifeInfo(trainLifeInfo);
	}

}
