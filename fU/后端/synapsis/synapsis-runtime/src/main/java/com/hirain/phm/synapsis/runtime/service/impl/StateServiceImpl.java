package com.hirain.phm.synapsis.runtime.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.hirain.phm.synapsis.constant.RunState;
import com.hirain.phm.synapsis.runtime.param.ActivateResponse;
import com.hirain.phm.synapsis.runtime.param.ControlResponse;
import com.hirain.phm.synapsis.runtime.service.StateService;
import com.hirain.phm.synapsis.setting.support.domain.ValidateResult;

@Service
public class StateServiceImpl implements StateService {

	@Autowired
	private ApplicationEventPublisher publisher;

	private RunState state = RunState.SELFCHECK;

	private List<String> errorMessages = new ArrayList<>();

	private ActivateResponse selfcheckResult;

	@Override
	public RunState getCurrentState() {
		return state;
	}

	public void setState(RunState state) {
		this.state = state;
		publisher.publishEvent(state);
	}

	public void setState(RunState state, List<String> errorMessages) {
		setState(state);
		this.errorMessages.clear();
		this.errorMessages.addAll(errorMessages);
	}

	@Override
	public void init() {
		setState(RunState.INIT);
		selfcheckResult = new ActivateResponse();
	}

	@Override
	public void loading() {
		setState(RunState.LOADING);
	}

	@Override
	public void waiting(String errorMessage) {
		setState(RunState.WAITING, Arrays.asList(errorMessage));
	}

	@Override
	public void running() {
		setState(RunState.RUNNING);
	}

	@Override
	public void idle() {
		setState(RunState.IDLE);
	}

	@Override
	public void validateError(ValidateResult result) {
		selfcheckResult.validateError();
		selfcheckResult.setValidateResult(result);
	}

	@Override
	public void lauchError(ControlResponse reponse) {
		selfcheckResult.lauchFail();
		selfcheckResult.setControlResponse(reponse);
	}
}
