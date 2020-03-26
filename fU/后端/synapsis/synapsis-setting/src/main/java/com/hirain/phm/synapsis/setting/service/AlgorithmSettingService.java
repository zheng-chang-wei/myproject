/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting.service;

import java.util.List;

import com.hirain.phm.synapsis.setting.AlgorithmSetting;
import com.hirain.phm.synapsis.setting.VariableGroup;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 5, 2019 11:24:26 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 5, 2019 jianwen.xin@hirain.com 1.0 create file
 */
public interface AlgorithmSettingService {

	/**
	 * @param id
	 * @param algorithmSettings
	 */
	void saveList(Integer settingId, List<AlgorithmSetting> algorithmSettings);

	/**
	 * @param settingId
	 */
	void delete(int settingId);

	List<VariableGroup> getVariables(int id);

	List<AlgorithmSetting> selectBySubsystemId(Integer subsystemId);
}
