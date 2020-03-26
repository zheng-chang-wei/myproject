/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.parse.impl;

import com.hirain.phm.synapsis.parse.ValuePostProcessor;
import com.hirain.phm.synapsis.setting.Variable;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 15, 2020 3:08:37 PM
 * @Description
 *              <p>
 *              默认后处理模块，直接返回原值
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 15, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public class DefaultValuePostProcessor implements ValuePostProcessor {

	/**
	 * @see com.hirain.phm.synapsis.parse.ValuePostProcessor#process(com.hirain.phm.synapsis.setting.Variable, double)
	 */
	@Override
	public double process(Variable variable, double value) {
		return value;
	}

}
