/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hirain.phm.synapsis.setting.AlgorithmSetting;
import com.hirain.phm.synapsis.setting.Setting;
import com.hirain.phm.synapsis.setting.VariableGroup;
import com.hirain.phm.synapsis.setting.dao.AlgorithmSettingMapper;
import com.hirain.phm.synapsis.setting.dao.SettingMapper;
import com.hirain.phm.synapsis.setting.db.AlgorithmSettingQuery;
import com.hirain.phm.synapsis.setting.service.AlgorithmSettingService;
import com.hirain.phm.synapsis.setting.service.VariableGroupService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 3, 2019 2:34:59 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 3, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Service
public class AlgorithmSettingServiceImpl implements AlgorithmSettingService, AlgorithmSettingQuery {

	@Autowired
	private AlgorithmSettingMapper mapper;

	@Autowired
	private VariableGroupService groupService;

	@Autowired
	private SettingMapper settingMapper;

	@Override
	public List<AlgorithmSetting> selectBySettingId(Integer settingId) {
		Example example = new Example(AlgorithmSetting.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("settingId", settingId);
		return mapper.selectByExample(example);
	}

	/**
	 * @see com.hirain.phm.synapsis.setting.db.AlgorithmSettingQuery#getAlgorithmSetting(int)
	 */
	@Override
	public AlgorithmSetting getAlgorithmSetting(int code) {
		Setting current = settingMapper.selectCurrent();
		if (current != null) {
			return getAlgorithmSetting(current.getId(), code);
		}
		return null;
	}

	/**
	 * @see com.hirain.phm.synapsis.setting.db.AlgorithmSettingQuery#getAlgorithmSetting(int, int)
	 */
	@Override
	public AlgorithmSetting getAlgorithmSetting(int settingId, int code) {
		AlgorithmSetting setting = mapper.selectByCode(settingId, code);
		return setting;
	}

	/**
	 * @param id
	 * @return
	 * @deprecated 没有被调用，可考虑移除
	 */
	@Deprecated
	public List<VariableGroup> getVariables(int id) {
		List<VariableGroup> variables = groupService.selectByAlgorithm(id);
		return variables;
	}

	/**
	 * @see com.hirain.phm.synapsis.setting.service.AlgorithmSettingService#saveList(java.lang.Integer, java.util.List)
	 */
	@Override
	@Transactional
	public void saveList(Integer settingId, List<AlgorithmSetting> algorithmSettings) {
		for (AlgorithmSetting algorithmSetting : algorithmSettings) {
			algorithmSetting.setSettingId(settingId);
			mapper.insertGenerateKey(algorithmSetting);
		}
		for (AlgorithmSetting algorithmSetting : algorithmSettings) {
			groupService.saveAlgorithmVariables(algorithmSetting.getId(), algorithmSetting.getVariableGroups());
		}
	}

	/**
	 * @see com.hirain.phm.synapsis.setting.service.AlgorithmSettingService#deleteBySettingId(int)
	 */
	@Override
	public void deleteBySettingId(int settingId) {
		List<AlgorithmSetting> settings = mapper.selectSetting(settingId);
		for (AlgorithmSetting setting : settings) {
			groupService.deleteAlgorithmVariables(setting.getId(), setting.getVariableGroups());
		}
		mapper.deleteBySettingId(settingId);
	}

	@Override
	public List<AlgorithmSetting> selectBySubsystemId(Integer subsystemId) {
		Example example = new Example(AlgorithmSetting.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("subsystemId", subsystemId);
		List<AlgorithmSetting> list = mapper.selectByExample(example);
		return list;
	}

}
