/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.result.ecn.service;

import com.hirain.phm.synapsis.result.ecn.domain.CommonSegmentSetting;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 19, 2020 4:12:16 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 19, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public interface CommonHeaderService {

	/**
	 * @param commonHeader
	 */
	void save(CommonSegmentSetting commonHeader);

	/**
	 * @param settingId
	 */
	CommonSegmentSetting selectBy(Integer settingId);

	/**
	 * @param settingId
	 */
	void delete(int settingId);

}
