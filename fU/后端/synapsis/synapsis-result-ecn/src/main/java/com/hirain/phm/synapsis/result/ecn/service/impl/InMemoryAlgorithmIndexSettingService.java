/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.result.ecn.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.hirain.phm.synapsis.result.ecn.domain.AlgorithmIndexSetting;
import com.hirain.phm.synapsis.result.ecn.service.AlgorithmIndexSettingService;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 19, 2020 4:49:19 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 19, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public class InMemoryAlgorithmIndexSettingService implements AlgorithmIndexSettingService {

	private List<AlgorithmIndexSetting> list = new ArrayList<>();

	private static long count = 1;

	/**
	 * @see com.hirain.phm.synapsis.result.ecn.service.AlgorithmIndexSettingService#save(java.util.List)
	 */
	@Override
	public void save(List<AlgorithmIndexSetting> algorithmIndexSettings) {
		for (AlgorithmIndexSetting setting : algorithmIndexSettings) {
			setting.setId(count++);
			list.add(setting);
		}
	}

	/**
	 * @see com.hirain.phm.synapsis.result.ecn.service.AlgorithmIndexSettingService#selectBy(java.lang.Integer)
	 */
	@Override
	public List<AlgorithmIndexSetting> selectBy(Integer settingId) {
		List<AlgorithmIndexSetting> indexList = new ArrayList<>();
		for (AlgorithmIndexSetting setting : list) {
			if (setting.getSettingId() == settingId) {
				indexList.add(setting);
			}
		}
		return indexList;
	}

	/**
	 * @see com.hirain.phm.synapsis.result.ecn.service.AlgorithmIndexSettingService#delete(int)
	 */
	@Override
	public void delete(int settingId) {
		AlgorithmIndexSetting remove = null;
		for (AlgorithmIndexSetting setting : list) {
			if (setting.getSettingId() == settingId) {
				remove = setting;
				break;
			}
		}
		if (remove != null) {
			list.remove(remove);
		}
	}

}
