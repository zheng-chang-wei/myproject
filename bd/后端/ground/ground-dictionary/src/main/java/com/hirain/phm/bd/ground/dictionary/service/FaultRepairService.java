/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.dictionary.service;

import java.util.List;

import com.hirain.phm.bd.ground.common.service.IService;
import com.hirain.phm.bd.ground.dictionary.domain.FaultRepair;
import com.hirain.phm.bd.ground.dictionary.domain.Repair;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Apr 8, 2020 6:50:12 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Apr 8, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public interface FaultRepairService extends IService<FaultRepair> {

	/**
	 * @param info
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
