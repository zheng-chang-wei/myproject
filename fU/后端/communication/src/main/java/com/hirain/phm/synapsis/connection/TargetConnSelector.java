/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.connection;

import java.util.Map;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 2, 2019 5:24:31 PM
 * @Description
 *              <p>
 *              连接实例挑选规则
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 2, 2019 jianwen.xin@hirain.com 1.0 create file
 */
public interface TargetConnSelector {

	/**
	 * 根据target返回发送的连接实例
	 * 
	 * @param target
	 * @param connectionMap
	 * @return
	 */
	Connection select(int target, Map<String, Connection> connectionMap);
}
