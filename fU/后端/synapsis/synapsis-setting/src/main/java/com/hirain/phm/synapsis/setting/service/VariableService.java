/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting.service;

import java.util.List;

import com.hirain.phm.synapsis.setting.Variable;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 22, 2020 2:01:38 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 22, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public interface VariableService {

	List<? extends Variable> selectVariables(int groupId);

	int insertVariables(int groupId, List<? extends Variable> variables);

	/**
	 * @param groupId
	 */
	void deleteVariables(int groupId);

	/**
	 * @param variable
	 */
	void insert(Variable variable);

	/**
	 * @param variableId
	 */
	void delete(Long variableId);

}
