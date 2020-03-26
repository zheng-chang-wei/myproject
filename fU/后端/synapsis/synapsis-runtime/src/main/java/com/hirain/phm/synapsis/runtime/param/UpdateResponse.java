/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.runtime.param;

import com.hirain.phm.synapsis.setting.support.domain.ValidateResult;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 10, 2019 10:31:27 AM
 * @Description
 *              <p>
 *              保存配置的响应
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 10, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Data
public class UpdateResponse {

	private static final int SUCCESS = 0;

	private static final int VALIDATE_ERROR = 1;

	private int settingId;

	private int result = SUCCESS;

	private ValidateResult validateResult;

	public void validateError() {
		result = VALIDATE_ERROR;
	}
}
