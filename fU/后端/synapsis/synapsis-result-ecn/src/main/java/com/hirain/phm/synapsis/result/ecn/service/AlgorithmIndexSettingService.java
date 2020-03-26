/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.result.ecn.service;

import java.util.List;

import com.hirain.phm.synapsis.result.ecn.domain.AlgorithmIndexSetting;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 19, 2020 4:45:52 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 19, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public interface AlgorithmIndexSettingService {

	/**
	 * @param algorithmIndexSetting
	 */
	void save(List<AlgorithmIndexSetting> algorithmIndexSetting);

	/**
	 * @param settingId
	 */
	List<AlgorithmIndexSetting> selectBy(Integer settingId);

	/**
	 * @param settingId
	 */
	void delete(int settingId);

}
