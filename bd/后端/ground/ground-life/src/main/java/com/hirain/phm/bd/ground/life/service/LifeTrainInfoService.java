/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.life.service;

import java.util.List;

import com.hirain.phm.bd.ground.common.service.IService;
import com.hirain.phm.bd.ground.life.domain.LifeTrainInfo;

/**
 * @Version 1.0   
 * @Author zepei.tao@hirain.com  
 * @Created May 24, 2019 10:28:01 AM  
 * @Description
 * <p>处理t_life_traininfo表的service
 * @Modification
 * <p>Date         Author      Version     Description  
 * <p>May 24, 2019     zepei.tao@hirain.com             1.0        create file 
 */
public interface LifeTrainInfoService extends IService<LifeTrainInfo> {

	/**
	 * 根据trainID 从t_life_traininfo表中找到相应的数据集合
	 */
	List<LifeTrainInfo> findTrainLifeInfoByTrainID(Integer trainID);

	/**
	 * 根据trainID 和 lifeitemID从t_life_traininfo表中找到相应的数据
	 */
	LifeTrainInfo findTrainLifeInfoByTrainIDItemId(Integer trainID, Integer itemID);

	/**
	 * 插入一条新数据
	 */
	void insertTrainLifeInfo(LifeTrainInfo trainLifeInfo);

	/**
	 * 更新指定数据
	 */
	void updateTrainLifeInfo(LifeTrainInfo trainLifeInfo);
}
