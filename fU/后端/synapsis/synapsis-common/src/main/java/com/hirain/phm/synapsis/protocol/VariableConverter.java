/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.protocol;

import java.util.List;

import com.hirain.phm.synapsis.exception.SynapsisException;
import com.hirain.phm.synapsis.setting.Variable;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 10, 2019 3:56:06 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 10, 2019 jianwen.xin@hirain.com 1.0 create file
 */
public interface VariableConverter {

	/**
	 * 从数据流文件中获取变量
	 * 
	 * @param object
	 * @return
	 * @throws SynapsisException
	 */
	List<? extends Variable> convert(Object object) throws SynapsisException;
}
