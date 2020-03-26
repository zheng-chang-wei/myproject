/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting.support.variable.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hirain.phm.synapsis.setting.Variable.VariableType;
import com.hirain.phm.synapsis.setting.VariableGroup;
import com.hirain.phm.synapsis.setting.support.param.VariableGroupVO;
import com.hirain.phm.synapsis.setting.support.variable.ParamVariableConverter;
import com.hirain.phm.synapsis.setting.support.variable.VariableGroupConverter;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 11, 2019 3:43:04 PM
 * @Description
 *              <p>
 *              前后端变量组对象转换
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 11, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Component
public class VariableGroupConverterImpl implements VariableGroupConverter {

	@Autowired
	private Map<String, ParamVariableConverter> converters;

	/** 
	 * @see com.hirain.phm.synapsis.setting.support.variable.VariableGroupConverter#convertToDomain(com.hirain.phm.synapsis.setting.support.param.VariableGroupVO, com.hirain.phm.synapsis.setting.Variable.VariableType)
	 */
	@Override
	public VariableGroup convertToDomain(VariableGroupVO frontGroup, VariableType type) {
		ParamVariableConverter converter = converters.get(type.name() + "-front");
		if (converter != null) {
			return converter.parseGroupVO(frontGroup);
		}
		return null;
	}

	/** 
	 * @see com.hirain.phm.synapsis.setting.support.variable.VariableGroupConverter#convertToVO(com.hirain.phm.synapsis.setting.VariableGroup)
	 */
	@Override
	public VariableGroupVO convertToVO(VariableGroup group) {
		ParamVariableConverter converter = converters.get(group.getType() + "-front");
		if (converter != null) {
			return converter.encodeGroup(group);
		}
		return null;
	}

}
