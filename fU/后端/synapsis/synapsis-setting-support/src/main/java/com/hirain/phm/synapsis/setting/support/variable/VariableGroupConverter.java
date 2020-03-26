/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting.support.variable;

import com.hirain.phm.synapsis.setting.Variable.VariableType;
import com.hirain.phm.synapsis.setting.VariableGroup;
import com.hirain.phm.synapsis.setting.support.param.VariableGroupVO;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 8, 2020 11:39:44 AM
 * @Description
 *              <p>
 *              前后端变量组对象的转换
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 8, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public interface VariableGroupConverter {

	/**
	 * 前端对象转后端对象
	 * 
	 * @param frontGroup
	 * @param type
	 * @return
	 */
	VariableGroup convertToDomain(VariableGroupVO frontGroup, VariableType type);

	/**
	 * 后端对象转前端对象
	 * 
	 * @param group
	 * @return
	 */
	VariableGroupVO convertToVO(VariableGroup group);

}