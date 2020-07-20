package com.hirain.phm.synapsis.runtime.service;

import com.hirain.phm.synapsis.constant.RunState;
import com.hirain.phm.synapsis.runtime.param.ControlResponse;
import com.hirain.phm.synapsis.runtime.param.StateResponse;
import com.hirain.phm.synapsis.setting.support.domain.ValidateResult;

public interface StateService {

	/**
	 * 获取当前状态
	 * 
	 * @return
	 */
	RunState getCurrentState();

	/**
	 * 状态变更为初始化
	 */
	void init();

	/**
	 * 状态变更为加载状态
	 */
	void loading();

	/**
	 * 状态变更为等待状态
	 */
	void waiting(String errorMessage);

	/**
	 * 状态变更为运行状态
	 */
	void running();

	/**
	 * 状态变更为错误状态，设置错误信息
	 * 
	 * @param result
	 */
	void validateError(ValidateResult result);

	/**
	 * 状态变更为错误状态，设置错误信息
	 * 
	 * @param reponse
	 */
	void lauchError(ControlResponse reponse);

	/**
	 * 
	 */
	void idle();

	/**
	 * @return
	 */
	StateResponse getStates();
}
