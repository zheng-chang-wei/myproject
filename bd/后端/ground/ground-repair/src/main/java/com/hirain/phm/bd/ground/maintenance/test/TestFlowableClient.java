/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.maintenance.test;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.hirain.phm.bd.ground.maintenance.flowable.FlowableClient;
import com.hirain.phm.bd.ground.maintenance.param.FlowResult;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Nov 12, 2019 1:57:43 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Nov 12, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Service
@Profile("test")
public class TestFlowableClient implements FlowableClient {

	private Map<String, String> processMap = new HashMap<>();

	private int processId = 1;

	/**
	 * @see com.hirain.phm.bd.ground.maintenance.flowable.FlowableClient#start()
	 */
	@Override
	public FlowResult start() {
		FlowResult result = new FlowResult();
		result.setProcessId(String.valueOf(processId++));
		processMap.put(result.getProcessId(), "创建工单");
		return result;
	}

	/**
	 * @see com.hirain.phm.bd.ground.maintenance.flowable.FlowableClient#process(java.lang.String, boolean)
	 */
	@Override
	public FlowResult process(String processId, boolean result) {
		String last = processMap.get(processId);
		FlowResult fresult = new FlowResult();
		if (last.equals("创建工单")) {
			fresult.setNext("售后审核");
		}
		fresult.setProcessId(processId);
		return fresult;
	}

	/**
	 * @see com.hirain.phm.bd.ground.maintenance.flowable.FlowableClient#terminate(java.lang.String)
	 */
	@Override
	public FlowResult terminate(String processId) {
		processMap.remove(processId);
		return new FlowResult();
	}

	/**
	 * @see com.hirain.phm.bd.ground.maintenance.flowable.FlowableClient#current(java.lang.String)
	 */
	@Override
	public FlowResult current(String processId) {
		return null;
	}

}
