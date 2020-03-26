/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.comm;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.hirain.phm.synapsis.connection.Connection;
import com.hirain.phm.synapsis.connection.TargetConnSelector;
import com.hirain.phm.synapsis.constant.Program;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 2, 2019 5:36:42 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 2, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Component
public class TargetConnSelectorImpl implements TargetConnSelector {

	/**
	 * @see com.hirain.phm.synapsis.connection.TargetConnSelector#select(int, java.util.Map)
	 */
	@Override
	public Connection select(int target, Map<String, Connection> connectionMap) {
		String programId = Program.valueOf(target);
		return connectionMap.get(programId);
	}

}
