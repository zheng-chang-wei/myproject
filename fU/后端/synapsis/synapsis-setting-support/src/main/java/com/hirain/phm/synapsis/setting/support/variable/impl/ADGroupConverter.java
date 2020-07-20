/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting.support.variable.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.hirain.phm.synapsis.setting.ADVariable;
import com.hirain.phm.synapsis.setting.Variable;
import com.hirain.phm.synapsis.setting.Variable.VariableType;
import com.hirain.phm.synapsis.setting.VariableGroup;
import com.hirain.phm.synapsis.setting.support.param.ADGroupVO;
import com.hirain.phm.synapsis.setting.support.param.ADVariableVO;
import com.hirain.phm.synapsis.setting.support.param.VariableGroupVO;
import com.hirain.phm.synapsis.setting.support.variable.ParamVariableConverter;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 11, 2019 4:16:36 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 11, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Component("AD-front")
public class ADGroupConverter implements ParamVariableConverter {

	/**
	 * @see com.hirain.phm.synapsis.setting.support.variable.ParamVariableConverter#parseGroupVO(com.hirain.phm.synapsis.setting.support.param.VariableGroupVO)
	 */
	@Override
	public VariableGroup parseGroupVO(VariableGroupVO frontVariableGroup) {
		ADGroupVO adGroup = (ADGroupVO) frontVariableGroup;
		VariableGroup group = new VariableGroup();
		group.setSlotId(adGroup.getSlotId());
		group.setType(VariableType.AD.name());
		List<ADVariable> variables = new ArrayList<>();
		for (ADVariableVO frontVariable : adGroup.getVariables()) {
			ADVariable adVariable = convertFrom(frontVariable);
			variables.add(adVariable);
		}
		group.setVariables(variables);
		return group;
	}

	private ADVariable convertFrom(ADVariableVO frontADVariable) {
		ADVariable variable = new ADVariable();
		variable.setChnId(frontADVariable.getChnId());
		variable.setName(frontADVariable.getName());
		variable.setSampleRate(frontADVariable.getSampleRate());
		return variable;
	}

	@Override
	public ADGroupVO encodeGroup(VariableGroup group) {
		ADGroupVO adGroup = new ADGroupVO();
		List<ADVariableVO> variables = new ArrayList<>();
		for (Variable variable : group.getVariables()) {
			variables.add(parseFrom((ADVariable) variable));
		}
		adGroup.setVariables(variables);
		adGroup.setSlotId(group.getSlotId());
		return adGroup;
	}

	/**
	 * @param variable
	 * @return
	 */
	private ADVariableVO parseFrom(ADVariable variable) {
		ADVariableVO adVariable = new ADVariableVO();
		adVariable.setChnId(variable.getChnId());
		adVariable.setName(variable.getName());
		adVariable.setSampleRate(variable.getSampleRate());
		return adVariable;
	}

}
