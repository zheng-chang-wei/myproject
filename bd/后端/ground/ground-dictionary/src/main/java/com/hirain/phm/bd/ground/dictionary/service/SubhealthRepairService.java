/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.dictionary.service;

import java.util.List;

import com.hirain.phm.bd.ground.common.service.IService;
import com.hirain.phm.bd.ground.dictionary.domain.Repair;
import com.hirain.phm.bd.ground.dictionary.domain.SubhealthRepair;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Apr 9, 2020 2:20:42 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Apr 9, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public interface SubhealthRepairService extends IService<SubhealthRepair> {

	/**
	 * @param infoId
	 * @param repairList
	 */
	void update(Integer infoId, List<Repair> repairList);

	/**
	 * @param infoId
	 */
	void deleteByInfoId(Integer infoId);

	/**
	 * @param repairId
	 */
	void deleteByRepairId(Integer repairId);

}
