/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.dictionary.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirain.phm.bd.ground.common.service.BaseService;
import com.hirain.phm.bd.ground.dictionary.dao.FaultRepairMapper;
import com.hirain.phm.bd.ground.dictionary.domain.FaultRepair;
import com.hirain.phm.bd.ground.dictionary.domain.Repair;
import com.hirain.phm.bd.ground.dictionary.service.FaultRepairService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Apr 8, 2020 6:50:42 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Apr 8, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Service
public class FaultRepairServiceImpl extends BaseService<FaultRepair> implements FaultRepairService {

	@Autowired
	private FaultRepairMapper mapper;

	/**
	 * @see com.hirain.phm.bd.ground.dictionary.service.FaultRepairService#update(com.hirain.phm.bd.ground.fault.domain.FaultInfo)
	 */
	@Override
	public void update(Integer infoId, List<Repair> repairList) {
		deleteByInfoId(infoId);
		if (repairList != null && !repairList.isEmpty()) {
			repairList.forEach(t -> {
				FaultRepair repair = new FaultRepair();
				repair.setFaultInfoId(infoId);
				repair.setRepairId(t.getId());
				mapper.insert(repair);
			});
		}

	}

	@Override
	public void deleteByInfoId(Integer infoId) {
		Example example = new Example(FaultRepair.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("faultInfoId", infoId);
		mapper.deleteByExample(example);
	}

	/**
	 * @see com.hirain.phm.bd.ground.dictionary.service.FaultRepairService#deleteByRepairId(java.lang.Integer)
	 */
	@Override
	public void deleteByRepairId(Integer repairId) {
		Example example = new Example(FaultRepair.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("repairId", repairId);
		mapper.deleteByExample(example);
	}

}
