/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting.support.variable.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.hirain.phm.synapsis.setting.MVBVariable;
import com.hirain.phm.synapsis.setting.Variable;
import com.hirain.phm.synapsis.setting.Variable.VariableType;
import com.hirain.phm.synapsis.setting.support.param.MVBGroupVO;
import com.hirain.phm.synapsis.setting.support.param.MVBVariableVO;
import com.hirain.phm.synapsis.setting.support.param.VariableGroupVO;
import com.hirain.phm.synapsis.setting.support.variable.ParamVariableConverter;
import com.hirain.phm.synapsis.setting.VariableGroup;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 11, 2019 4:11:47 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 11, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Component("MVB-front")
public class MVBGroupConverter implements ParamVariableConverter {

	/**
	 * @see com.hirain.phm.synapsis.setting.support.variable.ParamVariableConverter#parseGroupVO(com.hirain.phm.synapsis.setting.support.param.VariableGroupVO)
	 */
	@Override
	public VariableGroup parseGroupVO(VariableGroupVO frontVariableGroup) {
		MVBGroupVO mvbGroup = (MVBGroupVO) frontVariableGroup;
		VariableGroup group = new VariableGroup();
		group.setSlotId(mvbGroup.getSlotId());
		group.setType(VariableType.MVB.name());
		List<MVBVariable> variables = new ArrayList<>();
		for (MVBVariableVO frontVariable : mvbGroup.getVariables()) {
			MVBVariable variable = convertFrom(frontVariable);
			variables.add(variable);
		}
		group.setVariables(variables);
		return group;
	}

	private MVBVariable convertFrom(MVBVariableVO frontMVBVariable) {
		MVBVariable variable = new MVBVariable();
		variable.setSignalName(frontMVBVariable.getSignalName());
		variable.setByteOffset(frontMVBVariable.getByteOffset());
		variable.setBitOffset(frontMVBVariable.getBitOffset());
		variable.setBitLen(frontMVBVariable.getBitLen());
		variable.setCarriage(frontMVBVariable.getCarriage());
		variable.setDataType(frontMVBVariable.getDataType());
		variable.setName(frontMVBVariable.getName());
		variable.setUnit(frontMVBVariable.getUnit());
		variable.setSystem(frontMVBVariable.getSystem());
		variable.setPort(frontMVBVariable.getPort());
		variable.setDevice(frontMVBVariable.getDevice());
		variable.setDeviceName(frontMVBVariable.getDeviceName());
		variable.setFcode(frontMVBVariable.getFcode());
		return variable;
	}

	/**
	 * @param group
	 * @return
	 */
	@Override
	public MVBGroupVO encodeGroup(VariableGroup group) {
		MVBGroupVO mvbGroup = new MVBGroupVO();
		mvbGroup.setSlotId(group.getSlotId());
		List<MVBVariableVO> variables = new ArrayList<>();
		for (Variable variable : group.getVariables()) {
			MVBVariableVO mvbVariable = parseFrom((MVBVariable) variable);
			variables.add(mvbVariable);
		}
		mvbGroup.setVariables(variables);
		return mvbGroup;
	}

	/**
	 * @param variable
	 * @return
	 */
	private MVBVariableVO parseFrom(MVBVariable variable) {
		MVBVariableVO mvbVariable = new MVBVariableVO();
		mvbVariable.setSignalName(variable.getSignalName());
		mvbVariable.setByteOffset(variable.getByteOffset());
		mvbVariable.setBitOffset(variable.getBitOffset());
		mvbVariable.setBitLen(variable.getBitLen());
		mvbVariable.setCarriage(variable.getCarriage());
		mvbVariable.setDataType(variable.getDataType());
		mvbVariable.setName(variable.getName());
		mvbVariable.setUnit(variable.getUnit());
		mvbVariable.setSystem(variable.getSystem());
		mvbVariable.setPort(variable.getPort());
		mvbVariable.setDevice(variable.getDevice());
		mvbVariable.setDeviceName(variable.getDeviceName());
		mvbVariable.setFcode(variable.getFcode());
		return mvbVariable;
	}

}
