/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.runtime.service;

import com.hirain.phm.synapsis.runtime.message.LaunchProgressResponse;
import com.hirain.phm.synapsis.runtime.param.LaunchStepResponse;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 8, 2020 5:17:44 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 8, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public interface LaunchProgressManager {

	/**
	 * 初始化启动进度查询
	 */
	void start();

	void addStep(LaunchProgressResponse response);

	/**
	 * @return
	 */
	LaunchStepResponse getSteps();
}
