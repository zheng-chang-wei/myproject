/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirain.phm.synapsis.setting.MVBVariable;
import com.hirain.phm.synapsis.setting.Variable;
import com.hirain.phm.synapsis.setting.dao.MVBVariableMapper;
import com.hirain.phm.synapsis.setting.service.VariableService;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 22, 2020 2:01:58 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 22, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Service("MVB_variable")
public class MVBVariableService implements VariableService {

	@Autowired
	private MVBVariableMapper mapper;

	/**
	 * @see com.hirain.phm.synapsis.setting.service.VariableService#selectVariables(int)
	 */
	@Override
	public List<? extends Variable> selectVariables(int groupId) {
		return mapper.selectVariables(groupId);
	}

	/**
	 * @see com.hirain.phm.synapsis.setting.service.VariableService#insertVariables(int, java.util.List)
	 */
	@Override
	public int insertVariables(int groupId, List<? extends Variable> variables) {
		return mapper.insertVariables(groupId, variables);
	}

	/**
	 * @see com.hirain.phm.synapsis.setting.service.VariableService#deleteVariables(int)
	 */
	@Override
	public void deleteVariables(int groupId) {
		mapper.deleteVariables(groupId);
	}

	@Override
	public void insert(Variable variable) {
		mapper.insertGenerateKey((MVBVariable) variable);
	}

	/**
	 * @see com.hirain.phm.synapsis.setting.service.VariableService#delete(java.lang.Long)
	 */
	@Override
	public void delete(Long variableId) {
		mapper.deleteByPrimaryKey(variableId);
	}
}
