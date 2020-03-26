/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.runtime.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

import com.hirain.phm.synapsis.runtime.message.LaunchProgressResponse;
import com.hirain.phm.synapsis.runtime.service.LaunchProgressManager;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Mar 16, 2020 3:33:34 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Mar 16, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Configuration
public class LaunchProgressEventHandler {

	@Autowired
	private LaunchProgressManager launchManager;

	@EventListener
	@Async
	public void on(LaunchProgressResponse response) {
		launchManager.addStep(response);
	}
}
