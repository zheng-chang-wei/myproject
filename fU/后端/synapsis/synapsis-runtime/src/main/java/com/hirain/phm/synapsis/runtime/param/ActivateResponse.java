/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.runtime.param;

import com.hirain.phm.synapsis.setting.support.domain.ValidateResult;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 10, 2019 10:33:35 AM
 * @Description
 *              <p>
 *              激活配置的响应
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 10, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Data
public class ActivateResponse {

	private static final int SUCCESS = 0;

	private static final int VALIDATE_ERROR = 1;

	private static final int LAUNCH_FAIL = 2;

	private static final int STOP_FAIL = 3;

	/**
	 * 0:成功，1：校验出错，2：启动失败
	 */
	private int result = SUCCESS;

	private ControlResponse controlResponse;

	private ValidateResult validateResult;

	public void lauchFail() {
		result = LAUNCH_FAIL;
	}

	public void stopFail() {
		result = STOP_FAIL;
	}

	public void validateError() {
		result = VALIDATE_ERROR;
	}

	public boolean isSuccess() {
		return result == SUCCESS;
	}
}
