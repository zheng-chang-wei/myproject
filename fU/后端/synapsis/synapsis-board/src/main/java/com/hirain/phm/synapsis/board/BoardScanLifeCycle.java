/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.SmartLifecycle;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created May 20, 2020 6:19:28 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               May 20, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Component
@Configuration
public class BoardScanLifeCycle implements SmartLifecycle {

	@Autowired
	private BoardStatusTimer timer;

	/**
	 * @see org.springframework.context.Lifecycle#start()
	 */
	@Override
	public void start() {
		timer.start();
	}

	/**
	 * @see org.springframework.context.Lifecycle#stop()
	 */
	@Override
	public void stop() {

	}

	/**
	 * @see org.springframework.context.Lifecycle#isRunning()
	 */
	@Override
	public boolean isRunning() {
		return false;
	}

}
