/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting.ad;

import java.util.List;

import org.springframework.stereotype.Component;

import com.hirain.phm.synapsis.exception.SynapsisException;
import com.hirain.phm.synapsis.protocol.VariableConverter;
import com.hirain.phm.synapsis.setting.Variable;
import com.hirain.phm.synapsis.setting.VariableGroup;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 10, 2020 4:14:03 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 10, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Component("AD-converter")
public class ADVariableConverter implements VariableConverter {

	/**
	 * @see com.hirain.phm.synapsis.protocol.VariableConverter#convert(java.lang.Object)
	 */
	@Override
	public List<? extends Variable> convert(Object object) throws SynapsisException {
		if (object instanceof VariableGroup) {
			return ((VariableGroup) object).getVariables();
		}
		return null;
	}

}
