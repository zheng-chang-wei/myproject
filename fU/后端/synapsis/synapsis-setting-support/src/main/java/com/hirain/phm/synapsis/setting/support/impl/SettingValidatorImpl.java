/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting.support.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hirain.phm.synapsis.board.domain.Board;
import com.hirain.phm.synapsis.setting.Setting;
import com.hirain.phm.synapsis.setting.support.SettingValidator;
import com.hirain.phm.synapsis.setting.support.domain.ValidateResult;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 10, 2019 4:16:25 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 10, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Service
public class SettingValidatorImpl implements SettingValidator {

	/**
	 * @see com.hirain.phm.synapsis.setting.support.SettingValidator#validateWithBoard(com.hirain.phm.synapsis.setting.Setting, java.util.List)
	 */
	@Override
	public ValidateResult validateWithBoard(Setting current, List<Board> boards) {
		// TODO 校验板卡和配置的一致性
		ValidateResult result = new ValidateResult();
		if (current == null) {
			result.setOutline("没有配置");
			result.setErrors(Arrays.asList("没有当前配置"));
			result.setError(true);
		}
		return result;
	}

	/**
	 * @see com.hirain.phm.synapsis.setting.support.SettingValidator#validate(com.hirain.phm.synapsis.setting.Setting)
	 */
	@Override
	public ValidateResult validate(Setting setting) {
		// TODO 校验配置的正确性
		return new ValidateResult();
	}

}
