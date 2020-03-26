/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.parse.domain;

import java.util.List;

import com.hirain.phm.synapsis.setting.Variable;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 15, 2020 1:28:40 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 15, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Data
public class VariableData {

	private Variable variable;

	private List<VariableValue> values;

}
