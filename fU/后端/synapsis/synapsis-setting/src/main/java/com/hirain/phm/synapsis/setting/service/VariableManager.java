/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting.service;

import java.util.List;

import com.hirain.phm.synapsis.setting.Variable;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 22, 2020 10:11:03 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 22, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public interface VariableManager {

	List<? extends Variable> selectVariables(String type, int groupId);

	int insertVariables(String type, int groupId, List<? extends Variable> variables);

	/**
	 * @param groupId
	 */
	void deleteVariables(String type, int groupId);

	void insertVariable(String type, Variable variable);

	/**
	 * @param type
	 * @param variableId
	 */
	void delete(String type, Long variableId);
}
