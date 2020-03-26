/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.ecn.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.hirain.phm.synapsis.setting.ECNVariable;
import com.hirain.phm.synapsis.setting.Variable;
import com.hirain.phm.synapsis.setting.Variable.VariableType;
import com.hirain.phm.synapsis.setting.VariableGroup;
import com.hirain.phm.synapsis.setting.support.param.ECNGroupVO;
import com.hirain.phm.synapsis.setting.support.param.ECNVariableVO;
import com.hirain.phm.synapsis.setting.support.param.VariableGroupVO;
import com.hirain.phm.synapsis.setting.support.variable.ParamVariableConverter;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 11, 2019 4:02:55 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 11, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Component("ECN-front")
public class ECNGroupConverter implements ParamVariableConverter {

	/**
	 * @see com.hirain.phm.synapsis.setting.support.variable.ParamVariableConverter#parseGroupVO(com.hirain.phm.synapsis.setting.support.param.VariableGroupVO)
	 */
	@Override
	public VariableGroup parseGroupVO(VariableGroupVO frontVariableGroup) {
		ECNGroupVO ecnGroup = (ECNGroupVO) frontVariableGroup;
		VariableGroup variableGroup = new VariableGroup();
		variableGroup.setSlotId(ecnGroup.getSlotId());
		variableGroup.setType(VariableType.ECN.name());
		List<ECNVariable> variables = new ArrayList<>();
		for (ECNVariableVO ecnVariable : ecnGroup.getVariables()) {
			variables.add(converFrom(ecnVariable));
		}
		variableGroup.setVariables(variables);
		return variableGroup;
	}

	public ECNVariable converFrom(ECNVariableVO frontECNVariable) {
		ECNVariable variable = new ECNVariable();
		variable.setSignalName(frontECNVariable.getSignalName());
		variable.setByteOffset(frontECNVariable.getByteOffset());
		variable.setBitOffset(frontECNVariable.getBitOffset());
		variable.setBitLen(frontECNVariable.getBitLen());
		variable.setComId(frontECNVariable.getComId());
		variable.setDataType(frontECNVariable.getDataType());
		variable.setName(frontECNVariable.getName());
		variable.setUnit(frontECNVariable.getUnit());
		variable.setSourceIp(frontECNVariable.getSourceIp());
		return variable;
	}

	@Override
	public ECNGroupVO encodeGroup(VariableGroup group) {
		ECNGroupVO ecnGroup = new ECNGroupVO();
		ecnGroup.setSlotId(group.getSlotId());
		List<ECNVariableVO> variables = new ArrayList<>();
		for (Variable variable : group.getVariables()) {
			ECNVariableVO mvbVariable = parseFrom((ECNVariable) variable);
			variables.add(mvbVariable);
		}
		ecnGroup.setVariables(variables);
		return ecnGroup;
	}

	/**
	 * @param variable
	 * @return
	 */
	public ECNVariableVO parseFrom(ECNVariable variable) {
		ECNVariableVO ecnVariable = new ECNVariableVO();
		ecnVariable.setSignalName(variable.getSignalName());
		ecnVariable.setByteOffset(variable.getByteOffset());
		ecnVariable.setBitOffset(variable.getBitOffset());
		ecnVariable.setBitLen(variable.getBitLen());
		ecnVariable.setComId(variable.getComId());
		ecnVariable.setDataType(variable.getDataType());
		ecnVariable.setName(variable.getName());
		ecnVariable.setUnit(variable.getUnit());
		ecnVariable.setSourceIp(variable.getSourceIp());
		return ecnVariable;
	}
}
