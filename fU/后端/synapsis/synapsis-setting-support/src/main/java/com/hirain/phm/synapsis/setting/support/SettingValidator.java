/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting.support;

import java.util.List;

import com.hirain.phm.synapsis.board.domain.Board;
import com.hirain.phm.synapsis.setting.Setting;
import com.hirain.phm.synapsis.setting.support.domain.ValidateResult;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 10, 2019 2:47:55 PM
 * @Description
 *              <p>
 *              校验配置信息
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 10, 2019 jianwen.xin@hirain.com 1.0 create file
 */
public interface SettingValidator {

	/**
	 * 校验配置和板卡的一致性。
	 * 
	 * @param current
	 * @param boards
	 * @return
	 */
	ValidateResult validateWithBoard(Setting current, List<Board> boards);

	/**
	 * @param setting
	 */
	ValidateResult validate(Setting setting);

}
