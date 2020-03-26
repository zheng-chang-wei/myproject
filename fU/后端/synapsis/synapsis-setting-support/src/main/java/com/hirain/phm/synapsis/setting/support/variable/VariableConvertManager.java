/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting.support.variable;

import java.util.List;

import com.hirain.phm.synapsis.exception.SynapsisException;
import com.hirain.phm.synapsis.setting.Variable;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 8, 2020 11:41:09 AM
 * @Description
 *              <p>
 *              从数据流文件中提取变量
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 8, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public interface VariableConvertManager {

	List<? extends Variable> convert(String type, Object object) throws SynapsisException;

}