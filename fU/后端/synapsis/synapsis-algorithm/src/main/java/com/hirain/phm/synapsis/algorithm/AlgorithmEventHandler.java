/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.algorithm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

import com.hirain.phm.synapsis.algorithm.service.AlgorithmService;
import com.hirain.phm.synapsis.algorithm.service.RecordMapper;
import com.hirain.phm.synapsis.constant.RunState;
import com.hirain.phm.synapsis.setting.Setting;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 17, 2019 9:42:37 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 17, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Configuration
public class AlgorithmEventHandler {

	@Autowired
	private AlgorithmService service;

	@Autowired
	private AlgorithmStatusTimer timer;

	@Autowired
	private RecordMapper recordMapper;

	/**
	 * 初始化算法对象组
	 * 
	 * @param setting
	 */
	@EventListener
	@Async
	public void listen(Setting setting) {
		System.out.println("algorithm init");
		service.init(setting.getAlgorithmSettings());
	}

	/**
	 * 根据系统状态，启动或停止状态查询
	 * 
	 * @param state
	 */
	@EventListener
	public void listen(RunState state) {
		if (state.equals(RunState.RUNNING)) {
			timer.start();
			recordMapper.addRecord("系统启动");
		} else if (state.equals(RunState.IDLE)) {
			timer.stop();
			service.updateStatus(null);
			recordMapper.addRecord("系统停止");
		}
	}
}
