/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.parse.impl;

import org.springframework.stereotype.Component;

import com.hirain.phm.synapsis.parse.ValuePostProcessor;
import com.hirain.phm.synapsis.parse.VariableTypeHelper;
import com.hirain.phm.synapsis.parse.domain.HeaderVariableGroup;
import com.hirain.phm.synapsis.setting.Variable;
import com.hirain.phm.synapsis.setting.VariableGroup;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 19, 2020 9:23:43 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 19, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Component
public class DefaultVariableTypeHelper implements VariableTypeHelper {

	private ValuePostProcessor postProcessor = new DefaultValuePostProcessor();

	/**
	 * @see com.hirain.phm.synapsis.parse.VariableTypeHelper#getDataFilePrefix(com.hirain.phm.synapsis.setting.VariableGroup)
	 */
	@Override
	public String getDataFilePrefix(VariableGroup group) {
		// TODO AD数据文件前缀有通道号
		StringBuilder sb = new StringBuilder();
		sb.append(group.getType()).append("_");
		Integer slotId = group.getSlotId();
		if (slotId < 10) {
			sb.append("0");
		}
		sb.append(slotId).append("_");
		return sb.toString();
	}

	/**
	 * @see com.hirain.phm.synapsis.parse.VariableTypeHelper#isSameGroup(com.hirain.phm.synapsis.parse.domain.HeaderVariableGroup,
	 *      com.hirain.phm.synapsis.setting.VariableGroup)
	 */
	@Override
	public boolean isSameGroup(HeaderVariableGroup headerGroup, VariableGroup variableGroup) {
		boolean sameType = headerGroup.getType().equals(variableGroup.getType());
		boolean sameSlot = headerGroup.getSlotId() == variableGroup.getSlotId();
		Variable selected = variableGroup.getVariables().get(0);
		boolean hasVariable = false;
		for (Variable variable : headerGroup.getVariables()) {
			if (variable.getSignalName().equals(selected.getSignalName())) {
				hasVariable = true;
				break;
			}
		}
		return sameSlot && sameType && hasVariable;
	}

	/**
	 * @see com.hirain.phm.synapsis.parse.VariableTypeHelper#getValuePostProcessor(com.hirain.phm.synapsis.parse.domain.HeaderVariableGroup)
	 */
	@Override
	public ValuePostProcessor getValuePostProcessor(HeaderVariableGroup group) {
		// TODO AD需单独处理
		return postProcessor;
	}

}
