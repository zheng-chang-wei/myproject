/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.life.service;

import java.util.List;

import com.hirain.phm.bd.ground.common.service.IService;
import com.hirain.phm.bd.ground.life.domain.LifeDoorItem;
import com.hirain.phm.bd.ground.life.param.LifeDoorItemAVG;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created May 24, 2019 10:20:19 AM
 * @Description
 *              <p>
 *              处理t_life_dooritem表的service
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               May 24, 2019 zepei.tao@hirain.com 1.0 create file
 */
public interface LifeDoorItemService extends IService<LifeDoorItem> {

	/**
	 * 查询指定地址车门的寿命信息的集合
	 */
	List<LifeDoorItem> findDoorItemLifeInfos(Integer trainId, Integer carNo, Integer doorAddr);

	/**
	 * 查询指定地址车门的寿命信息
	 */
	LifeDoorItem findDoorItemLifeInfo(Integer trainId, Integer carNo, Integer doorAddr, Integer lifeitemId);

	/**
	 * 更新指定车门、指定寿命项点的相关信息
	 */
	void updateDoorItemLifeInfo(LifeDoorItem doorItemLife);

	/**
	 * 插入一条指定车门、指定寿命项点的数据
	 */
	void insertDoorItemLifeData(LifeDoorItem doorItemLife);

	List<LifeDoorItemAVG> findDoorItemAVGValue(String project, String trainNo);

}
