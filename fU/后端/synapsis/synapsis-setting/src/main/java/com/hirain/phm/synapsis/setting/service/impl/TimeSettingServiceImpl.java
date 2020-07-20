/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting.service.impl;

import org.springframework.stereotype.Service;

import com.hirain.phm.synapsis.setting.TimeSetting;
import com.hirain.phm.synapsis.setting.common.BaseService;
import com.hirain.phm.synapsis.setting.service.TimeSettingService;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Apr 21, 2020 5:40:27 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Apr 21, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Service
public class TimeSettingServiceImpl extends BaseService<TimeSetting> implements TimeSettingService {

	/**
	 * @see com.hirain.phm.synapsis.setting.service.TimeSettingService#deleteBySettingId(java.lang.Integer)
	 */
	@Override
	public void deleteBySettingId(Integer settingId) {
		delete(settingId);
	}

}
