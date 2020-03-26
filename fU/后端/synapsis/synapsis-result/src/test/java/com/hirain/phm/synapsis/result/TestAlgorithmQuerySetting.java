/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.result;

import org.springframework.stereotype.Service;

import com.hirain.phm.synapsis.setting.AlgorithmSetting;
import com.hirain.phm.synapsis.setting.db.AlgorithmSettingQuery;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Feb 10, 2020 3:39:28 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Feb 10, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Service
public class TestAlgorithmQuerySetting implements AlgorithmSettingQuery {

	private AlgorithmSetting setting;

	@Override
	public AlgorithmSetting getAlgorithmSetting(int settingId, int code) {
		AlgorithmSetting algorithmSetting = getSetting();
		return algorithmSetting;
	}

	/**
	 * @return
	 */
	public AlgorithmSetting getSetting() {
		if (setting == null) {
			AlgorithmSetting algorithmSetting = new AlgorithmSetting();
			algorithmSetting.setId(1);
			algorithmSetting.setName("算法1");
			algorithmSetting.setCode(1);
			algorithmSetting.setSlotId(1);
			algorithmSetting.setType(1);
			algorithmSetting.setFilename("xxx.py");
			algorithmSetting.setEnable(true);
			algorithmSetting.setSubsystemId(1);
			setting = algorithmSetting;
		}
		return setting;
	}

	@Override
	public AlgorithmSetting getAlgorithmSetting(int code) {
		return getSetting();
	}
}
