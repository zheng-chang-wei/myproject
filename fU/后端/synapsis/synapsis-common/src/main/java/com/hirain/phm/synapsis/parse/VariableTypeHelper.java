/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.parse;

import com.hirain.phm.synapsis.parse.domain.HeaderVariableGroup;
import com.hirain.phm.synapsis.setting.VariableGroup;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 16, 2020 6:31:19 PM
 * @Description
 *              <p>
 *              针对不同的采集源分别处理
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 16, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public interface VariableTypeHelper {

	/**
	 * 获取原始数据文件名前缀
	 * 
	 * @param group
	 * @return
	 */
	String getDataFilePrefix(VariableGroup group);

	/**
	 * @param headerGroup
	 * @param variableGroup
	 * @return
	 */
	boolean isSameGroup(HeaderVariableGroup headerGroup, VariableGroup variableGroup);

	/**
	 * 获取变量值后处理模块
	 * 
	 * @param group
	 * @return
	 */
	ValuePostProcessor getValuePostProcessor(HeaderVariableGroup group);
}
