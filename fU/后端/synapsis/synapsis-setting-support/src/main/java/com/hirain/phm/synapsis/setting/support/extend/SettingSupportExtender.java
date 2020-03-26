/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting.support.extend;

import com.hirain.phm.synapsis.setting.Setting;
import com.hirain.phm.synapsis.setting.support.domain.ValidateResult;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 22, 2020 9:24:40 AM
 * @Description
 *              <p>
 *              配置操作扩展
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 22, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public interface SettingSupportExtender {

	void activate(int settingId) throws Exception;

	ValidateResult validate(int settingId, Setting setting);

	/**
	 * @param settingId
	 */
	void delete(int settingId) throws Exception;
}
