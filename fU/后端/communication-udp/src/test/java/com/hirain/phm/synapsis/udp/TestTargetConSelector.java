/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.udp;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.hirain.phm.synapsis.connection.Connection;
import com.hirain.phm.synapsis.connection.TargetConnSelector;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 4, 2019 11:32:22 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 4, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Component
public class TestTargetConSelector implements TargetConnSelector {

	/**
	 * @see com.hirain.phm.synapsis.connection.TargetConnSelector#select(int, java.util.Map)
	 */
	@Override
	public Connection select(int target, Map<String, Connection> connectionMap) {
		return connectionMap.get("Service");
	}

}
