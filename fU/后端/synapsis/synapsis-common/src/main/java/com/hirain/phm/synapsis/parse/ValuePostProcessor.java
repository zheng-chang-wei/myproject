/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.parse;

import com.hirain.phm.synapsis.setting.Variable;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 15, 2020 2:59:41 PM
 * @Description
 *              <p>
 *              数据后处理，主要针对AD采集的数据
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 15, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public interface ValuePostProcessor {

	double process(Variable variable, double value);
}
