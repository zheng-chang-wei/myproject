/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting.support.variable;

import com.hirain.phm.synapsis.setting.VariableGroup;
import com.hirain.phm.synapsis.setting.support.param.VariableGroupVO;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 11, 2019 4:01:46 PM
 * @Description
 *              <p>
 *              变量组前后端对象互转
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 11, 2019 jianwen.xin@hirain.com 1.0 create file
 */
public interface ParamVariableConverter {

	VariableGroup parseGroupVO(VariableGroupVO frontVariableGroup);

	VariableGroupVO encodeGroup(VariableGroup group);
}
