/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.result.ecn.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hirain.phm.synapsis.result.ecn.dao.AlgorithmIndexMapper;
import com.hirain.phm.synapsis.result.ecn.dao.AlgorithmIndexSettingMapper;
import com.hirain.phm.synapsis.result.ecn.domain.AlgorithmIndex;
import com.hirain.phm.synapsis.result.ecn.domain.AlgorithmIndexSetting;
import com.hirain.phm.synapsis.result.ecn.service.AlgorithmIndexSettingService;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 21, 2020 4:06:20 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 21, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Service
public class IndexSettingServiceImpl implements AlgorithmIndexSettingService {

	@Autowired
	private AlgorithmIndexSettingMapper settingMapper;

	@Autowired
	private AlgorithmIndexMapper mapper;

	/**
	 * @see com.hirain.phm.synapsis.result.ecn.service.AlgorithmIndexSettingService#save(java.util.List)
	 */
	@Override
	@Transactional
	public void save(List<AlgorithmIndexSetting> algorithmIndexSetting) {
		for (AlgorithmIndexSetting setting : algorithmIndexSetting) {
			save(setting);
		}
	}

	/**
	 * @param setting
	 */
	private void save(AlgorithmIndexSetting setting) {
		settingMapper.insertGenerateKey(setting);
		setting.getAlgorithms().forEach(a -> {
			a.setIndexSettingId(setting.getId());
			mapper.insertGenerateKey(a);
		});
	}

	/**
	 * @see com.hirain.phm.synapsis.result.ecn.service.AlgorithmIndexSettingService#selectBy(java.lang.Integer)
	 */
	@Override
	public List<AlgorithmIndexSetting> selectBy(Integer settingId) {
		return settingMapper.selectBySettingId(settingId);
	}

	/**
	 * @see com.hirain.phm.synapsis.result.ecn.service.AlgorithmIndexSettingService#delete(int)
	 */
	@Override
	public void delete(int settingId) {
		List<AlgorithmIndexSetting> settings = selectBy(settingId);
		for (AlgorithmIndexSetting setting : settings) {
			settingMapper.deleteByPrimaryKey(setting.getId());
			for (AlgorithmIndex index : setting.getAlgorithms()) {
				mapper.deleteByPrimaryKey(index.getId());
			}
		}
	}

}
