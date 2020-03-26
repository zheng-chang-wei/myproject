/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting.db;

import java.util.List;

import com.hirain.phm.synapsis.setting.Variable;
import com.hirain.phm.synapsis.setting.VariableGroup;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 19, 2020 5:48:04 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 19, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public interface VariableDbService {

	void saveGroups(int boardId, List<VariableGroup> groups);

	void insertVariable(String type, Variable variable);

	/**
	 * @param string
	 * @param variableId
	 */
	default void delete(String string, Long variableId) {

	};
}
