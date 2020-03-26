/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirain.phm.synapsis.setting.Variable;
import com.hirain.phm.synapsis.setting.service.VariableManager;
import com.hirain.phm.synapsis.setting.service.VariableService;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 22, 2020 10:12:15 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 22, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Service
public class VariableManagerImpl implements VariableManager {

	@Autowired
	private Map<String, VariableService> serviceMap;

	/**
	 * @see com.hirain.phm.synapsis.setting.service.VariableManager#selectVariables(java.lang.String, int)
	 */
	@Override
	public List<? extends Variable> selectVariables(String type, int groupId) {
		VariableService service = getService(type);
		return service.selectVariables(groupId);
	}

	/**
	 * @see com.hirain.phm.synapsis.setting.service.VariableManager#insertVariables(java.lang.String, int, java.util.List)
	 */
	@Override
	public int insertVariables(String type, int groupId, List<? extends Variable> variables) {
		VariableService service = getService(type);
		return service.insertVariables(groupId, variables);
	}

	/**
	 * @see com.hirain.phm.synapsis.setting.service.VariableManager#deleteVariables(java.lang.String, int)
	 */
	@Override
	public void deleteVariables(String type, int groupId) {
		VariableService service = getService(type);
		service.deleteVariables(groupId);
	}

	/**
	 * @see com.hirain.phm.synapsis.setting.service.VariableManager#insertVariable(java.lang.String, com.hirain.phm.synapsis.setting.Variable)
	 */
	@Override
	public void insertVariable(String type, Variable variable) {
		VariableService service = getService(type);
		service.insert(variable);
	}

	/**
	 * @see com.hirain.phm.synapsis.setting.service.VariableManager#delete(java.lang.String, java.lang.Long)
	 */
	@Override
	public void delete(String type, Long variableId) {
		VariableService service = getService(type);
		service.delete(variableId);
	}

	/**
	 * @param type
	 * @return
	 */
	private VariableService getService(String type) {
		VariableService service = serviceMap.get(getKey(type));
		if (service == null) {
			throw new RuntimeException("no type service");
		}
		return service;
	}

	/**
	 * @param type
	 * @return
	 */
	private String getKey(String type) {
		return type.toUpperCase() + "_variable";
	}
}
