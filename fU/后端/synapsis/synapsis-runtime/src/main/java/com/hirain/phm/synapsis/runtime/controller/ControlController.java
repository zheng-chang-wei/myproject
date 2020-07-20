/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.runtime.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirain.phm.synapsis.response.ResultBean;
import com.hirain.phm.synapsis.runtime.param.ActivateResponse;
import com.hirain.phm.synapsis.runtime.param.ControlResponse;
import com.hirain.phm.synapsis.runtime.param.LaunchStepResponse;
import com.hirain.phm.synapsis.runtime.param.StateResponse;
import com.hirain.phm.synapsis.runtime.service.LaunchProgressManager;
import com.hirain.phm.synapsis.runtime.service.RuntimeService;
import com.hirain.phm.synapsis.runtime.service.StateService;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 10, 2019 10:50:34 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 10, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@RestController
public class ControlController {

	@Autowired
	private LaunchProgressManager manager;

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private StateService stateService;

	@PostMapping("/launch")
	public ResultBean<ActivateResponse> launch() throws Exception {
		return new ResultBean<>(runtimeService.launchCurrent());
	}

	@PostMapping("/terminate")
	public ResultBean<ControlResponse> terminate() throws Exception {
		return new ResultBean<>(runtimeService.terminate().getControlResponse());
	}

	@GetMapping("/launch/steps")
	public ResultBean<LaunchStepResponse> getLaunchSteps() {
		return new ResultBean<>(manager.getSteps());
	}

	@GetMapping("/state")
	public ResultBean<String> getCurrentState() {
		return new ResultBean<>(stateService.getCurrentState().name());
	}

	@GetMapping("/states")
	public ResultBean<StateResponse> getStates() {
		return new ResultBean<>(stateService.getStates());
	}
}
