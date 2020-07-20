/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.board;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hirain.phm.synapsis.board.service.BoardService;
import com.hirain.phm.synapsis.board.service.impl.BoardConfig;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 18, 2019 2:25:58 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 18, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Component
public class BoardStatusTimer {

	@Autowired
	private BoardService service;

	private AtomicBoolean stop;

	private ExecutorService executor = Executors.newSingleThreadExecutor(r -> new Thread(r, getClass().getName()));

	@Autowired
	private BoardConfig config;

	public void start() {
		stop = new AtomicBoolean(false);
		executor.submit(() -> {
			while (!stop.get()) {
				try {
					TimeUnit.MILLISECONDS.sleep(config.getStatusPeriod());
				} catch (InterruptedException e) {
				}
				checkStatus();
			}
		});
	}

	public void stop() {
		if (stop != null) {
			stop.set(true);
		}
	}

	/**
	 * 
	 */
	private void checkStatus() {
		service.boardStatusInquire();
	}
}
