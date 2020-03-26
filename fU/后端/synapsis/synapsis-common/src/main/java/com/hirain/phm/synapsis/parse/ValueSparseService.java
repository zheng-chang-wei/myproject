/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.parse;

import java.util.List;

import com.hirain.phm.synapsis.parse.domain.VariableValue;
import com.hirain.phm.synapsis.setting.Variable;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 17, 2020 1:31:20 PM
 * @Description
 *              <p>
 *              抽稀
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 17, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public interface ValueSparseService {

	List<VariableValue> sparse(Variable variable, List<VariableValue> values);
}
