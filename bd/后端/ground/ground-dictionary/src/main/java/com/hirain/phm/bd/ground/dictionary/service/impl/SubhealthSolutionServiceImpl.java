/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.dictionary.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirain.phm.bd.ground.common.service.BaseService;
import com.hirain.phm.bd.ground.dictionary.dao.SubhealthSolutionMapper;
import com.hirain.phm.bd.ground.dictionary.domain.Solution;
import com.hirain.phm.bd.ground.dictionary.domain.SubhealthSolution;
import com.hirain.phm.bd.ground.dictionary.service.SubhealthSolutionService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Apr 9, 2020 2:23:16 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Apr 9, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Service
public class SubhealthSolutionServiceImpl extends BaseService<SubhealthSolution> implements SubhealthSolutionService {

	@Autowired
	private SubhealthSolutionMapper mapper;

	/**
	 * @see com.hirain.phm.bd.ground.dictionary.service.FaultRepairService#update(com.hirain.phm.bd.ground.fault.domain.FaultInfo)
	 */
	@Override
	public void update(Integer infoId, List<Solution> solutionList) {
		deleteByInfoId(infoId);
		if (solutionList != null && !solutionList.isEmpty()) {
			solutionList.forEach(t -> {
				SubhealthSolution solution = new SubhealthSolution();
				solution.setSubhealthInfoId(infoId);
				solution.setSolutionId(t.getId());
				mapper.insert(solution);
			});
		}

	}

	@Override
	public void deleteByInfoId(Integer infoId) {
		Example example = new Example(SubhealthSolution.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("subhealthInfoId", infoId);
		mapper.deleteByExample(example);
	}

	/**
	 * @see com.hirain.phm.bd.ground.dictionary.service.FaultSolutionService#deleteBySolutionId(java.lang.Integer)
	 */
	@Override
	public void deleteBySolutionId(Integer id) {
		Example example = new Example(SubhealthSolution.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("solutionId", id);
		mapper.deleteByExample(example);

	}
}
