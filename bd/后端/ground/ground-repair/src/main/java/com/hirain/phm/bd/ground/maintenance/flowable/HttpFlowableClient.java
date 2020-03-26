/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.maintenance.flowable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.hirain.phm.bd.ground.maintenance.param.FlowResult;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Nov 12, 2019 1:54:54 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Nov 12, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Service
@Profile({ "local", "dev" })
public class HttpFlowableClient implements FlowableClient {

	@Autowired
	private HttpClient http;

	/**
	 * @see com.hirain.phm.bd.ground.maintenance.flowable.FlowableClient#start()
	 */
	@Override
	public FlowResult start() {
		return http.start();
	}

	/**
	 * @see com.hirain.phm.bd.ground.maintenance.flowable.FlowableClient#process(java.lang.String, boolean)
	 */
	@Override
	public FlowResult process(String processId, boolean result) {
		return http.process(processId, result);
	}

	/**
	 * @see com.hirain.phm.bd.ground.maintenance.flowable.FlowableClient#terminate(java.lang.String)
	 */
	@Override
	public FlowResult terminate(String processId) {
		return http.terminate(processId);
	}

	/**
	 * @see com.hirain.phm.bd.ground.maintenance.flowable.FlowableClient#current(java.lang.String)
	 */
	@Override
	public FlowResult current(String processId) {
		return http.current(processId);
	}

}
