package com.hirain.phm.synapsis.runtime.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.hirain.phm.synapsis.board.domain.BoardStartStructure;
import com.hirain.phm.synapsis.constant.RunState;
import com.hirain.phm.synapsis.runtime.param.ActivateResponse;
import com.hirain.phm.synapsis.runtime.param.ControlResponse;
import com.hirain.phm.synapsis.runtime.param.StateResponse;
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
		if (selfcheckResult == null) {
			selfcheckResult = new ActivateResponse();
		}
		selfcheckResult.validateError();
		selfcheckResult.setValidateResult(result);
		errorMessages.addAll(result.getErrors());
	}

	@Override
	public void lauchError(ControlResponse response) {
		if (selfcheckResult == null) {
			selfcheckResult = new ActivateResponse();
		}
		selfcheckResult.lauchFail();
		selfcheckResult.setControlResponse(response);
		List<BoardStartStructure> results = response.getControlResults();
		List<String> errors = results.stream().map(r -> "槽位" + r.getSlotID() + " [" + r.getCardIP() + "]  -" + r.getResult().getMessage())
				.collect(Collectors.toList());
		errors.forEach(System.out::println);
		errorMessages.addAll(errors);
	}

	/**
	 * @see com.hirain.phm.synapsis.runtime.service.StateService#getStates()
	 */
	@Override
	public StateResponse getStates() {
		StateResponse response = new StateResponse();
		response.setState(getCurrentState().name());
		response.setErrors(new ArrayList<>(errorMessages));
		return response;
	}
}
