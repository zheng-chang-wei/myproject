/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.runtime.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

import com.hirain.phm.synapsis.runtime.message.LaunchProgressResponse;
import com.hirain.phm.synapsis.runtime.param.LaunchStep;
import com.hirain.phm.synapsis.runtime.param.LaunchStepResponse;
import com.hirain.phm.synapsis.runtime.service.LaunchProgressManager;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 8, 2020 5:20:35 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 8, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Service
public class LaunchProgressManagerImpl implements LaunchProgressManager {

	private List<LaunchStep> steps = new CopyOnWriteArrayList<>();

	private AtomicInteger stepNum = new AtomicInteger();

	@Override
	public void start() {
		steps.clear();
		stepNum.set(0);
	}

	/**
	 * @see com.hirain.phm.synapsis.runtime.service.LaunchProgressManager#addStep()
	 */
	@Override
	public void addStep(LaunchProgressResponse response) {
		stepNum.set(response.getStepNum());
		LaunchStep step = new LaunchStep();
		step.setStep(response.getStepIndex());
		step.setMessage(response.getMessage());
		steps.add(step);
	}

	/**
	 * @see com.hirain.phm.synapsis.runtime.service.LaunchProgressManager#getSteps()
	 */
	@Override
	public LaunchStepResponse getSteps() {
		LaunchStepResponse response = new LaunchStepResponse();
		response.setStepNum(stepNum.get());
		List<LaunchStep> fsteps = new ArrayList<>(steps);
		response.setSteps(fsteps);
		if (fsteps.isEmpty()) {
			LaunchStep step = new LaunchStep();
			step.setStep(-1);
			step.setMessage("请稍等……");
			fsteps.add(step);
		}
		return response;
	}

}
