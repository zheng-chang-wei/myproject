/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.result.ecn.service.impl;

import java.util.HashMap;
import java.util.Map;

import com.hirain.phm.synapsis.result.ecn.domain.CommonSegmentSetting;
import com.hirain.phm.synapsis.result.ecn.service.CommonHeaderService;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 19, 2020 4:48:02 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 19, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public class InMemoryCommonHeaderService implements CommonHeaderService {

	private Map<Integer, CommonSegmentSetting> map = new HashMap<>();

	private static long count = 1;

	/**
	 * @see com.hirain.phm.synapsis.result.ecn.service.CommonHeaderService#save(com.hirain.phm.synapsis.result.ecn.domain.CommonSegmentSetting)
	 */
	@Override
	public void save(CommonSegmentSetting commonHeader) {
		commonHeader.setId(count++);
		map.put(commonHeader.getSettingId(), commonHeader);
	}

	/**
	 * @see com.hirain.phm.synapsis.result.ecn.service.CommonHeaderService#selectBy(java.lang.Integer)
	 */
	@Override
	public CommonSegmentSetting selectBy(Integer settingId) {
		return map.get(settingId);
	}

	/**
	 * @see com.hirain.phm.synapsis.result.ecn.service.CommonHeaderService#delete(int)
	 */
	@Override
	public void delete(int settingId) {
		map.remove(settingId);
	}

}
