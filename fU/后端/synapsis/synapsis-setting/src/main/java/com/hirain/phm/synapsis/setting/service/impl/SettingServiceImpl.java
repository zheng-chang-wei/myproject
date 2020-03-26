/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hirain.phm.synapsis.setting.AlgorithmSetting;
import com.hirain.phm.synapsis.setting.BoardSetting;
import com.hirain.phm.synapsis.setting.Setting;
import com.hirain.phm.synapsis.setting.VariableGroup;
import com.hirain.phm.synapsis.setting.dao.SettingMapper;
import com.hirain.phm.synapsis.setting.service.AlgorithmSettingService;
import com.hirain.phm.synapsis.setting.service.BoardSettingService;
import com.hirain.phm.synapsis.setting.service.SettingService;
import com.hirain.phm.synapsis.setting.service.VariableGroupService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 3, 2019 6:21:44 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 3, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Service
public class SettingServiceImpl implements SettingService {

	@Autowired
	private SettingMapper settingMapper;

	@Autowired
	private VariableGroupService groupService;

	@Autowired
	private BoardSettingService boardService;

	@Autowired
	private AlgorithmSettingService algorithmService;

	@Override
	public void saveOrUpdate(Setting setting) {
		Setting oldSetting = selectByName(setting.getName());
		setting.setLastModify(new Date());
		if (oldSetting != null) {
			setting.setId(oldSetting.getId());
			setting.setSelected(oldSetting.getSelected());
			update(setting);
		} else {
			List<Setting> list = selectAll();
			if (list != null && list.size() >= 10) {
				deleteOldest();
			}
			setting.setSelected(false);
			save(setting);
		}
	}

	@Override
	@Transactional
	public Setting save(Setting setting) {
		settingMapper.insertGenerateKey(setting);
		saveDetail(setting);
		return setting;
	}

	/**
	 * @param setting
	 */
	private void saveDetail(Setting setting) {
		boardService.saveList(setting.getId(), setting.getBoardSettings());
		algorithmService.saveList(setting.getId(), setting.getAlgorithmSettings());

		saveTimeVariables(setting.getId(), setting.getTimeVariables());
		saveStoreVariables(setting.getId(), setting.getStoreVariables());
	}

	/**
	 * @param id
	 * @param timeVariables
	 */
	private void saveTimeVariables(Integer settingId, VariableGroup timeVariables) {
		if (timeVariables != null) {
			groupService.insertTimeGroup(settingId, timeVariables);
		}
	}

	/**
	 * @param settingId
	 * @param storeVariables
	 */
	private void saveStoreVariables(Integer settingId, List<VariableGroup> storeVariables) {
		if (storeVariables != null) {
			groupService.insertStoreGroups(settingId, storeVariables);
		}
	}

	/**
	 * @see com.hirain.phm.synapsis.setting.service.SettingService#update(com.hirain.phm.synapsis.setting.Setting)
	 */
	@Override
	@Transactional
	public void update(Setting setting) {
		deleteDetail(setting.getId());
		settingMapper.updateByKey(setting);
		saveDetail(setting);
	}

	/**
	 * @see com.hirain.phm.synapsis.setting.service.SettingService#changeCurrent(java.lang.Integer)
	 */
	@Override
	@Transactional
	public void changeCurrent(Integer id) {
		Setting oldCurrent = selectCurrent();
		if (oldCurrent.getId().equals(id)) {
			return;
		}
		oldCurrent.setSelected(false);
		Setting newCurrent = selectById(id);
		newCurrent.setSelected(true);
		settingMapper.updateByPrimaryKeySelective(oldCurrent);
		settingMapper.updateByPrimaryKeySelective(newCurrent);
	}

	@Override
	@Transactional
	public void deleteOldest() {
		int settingId = settingMapper.selectOldest();
		delete(settingId);
	}

	/**
	 */
	@Override
	@Transactional
	public void delete(int settingId) {
		settingMapper.deleteByPrimaryKey(settingId);
		deleteDetail(settingId);
	}

	/**
	 * @param settingId
	 */
	private void deleteDetail(int settingId) {
		groupService.deleteTimeVariables(settingId);
		groupService.deleteStoreVariables(settingId);
		boardService.delete(settingId);
		algorithmService.delete(settingId);
	}

	@Override
	public List<Setting> selectAll() {
		return settingMapper.selectAll();
	}

	/**
	 * @param i
	 */
	@Override
	public Setting selectById(int id) {
		return settingMapper.selectByPrimaryKey(id);
	}

	/**
	 * @see com.hirain.phm.synapsis.setting.service.SettingService#selectByName(java.lang.String)
	 */
	@Override
	public Setting selectByName(String name) {
		Example example = new Example(Setting.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("name", name);
		List<Setting> results = settingMapper.selectByExample(example);
		return results == null || results.isEmpty() ? null : results.get(0);
	}

	/**
	 * 
	 */
	@Override
	public Setting selectCurrent() {
		Setting current = settingMapper.selectCurrent();
		Setting setting = selectWithDetail(current.getId());
		return setting;
	}

	/**
	 * @return
	 * @see com.hirain.phm.synapsis.setting.service.SettingService#selectWithDetail(int)
	 */
	@Override
	public Setting selectWithDetail(int id) {
		Setting setting = settingMapper.selectDetails(id);
		if (setting.getTimeVariables() != null) {
			groupService.fillVariableGroup(setting.getTimeVariables());
		}
		groupService.fillVariableGroups(setting.getStoreVariables());
		postAlgorithm(setting.getAlgorithmSettings());
		postBoard(setting.getBoardSettings());
		return setting;
	}

	/**
	 * 板卡配置后处理
	 * 
	 * @param boardSettings
	 */
	private void postBoard(List<BoardSetting> boardSettings) {
		if (boardSettings != null) {
			for (BoardSetting boardSetting : boardSettings) {
				groupService.fillVariableGroups(boardSetting.getVariableGroups());
			}
		}
	}

	/**
	 * 算法配置后处理
	 * 
	 * @param algorithmSettings
	 */
	private void postAlgorithm(List<AlgorithmSetting> algorithmSettings) {
		if (algorithmSettings != null) {
			for (AlgorithmSetting algorithmSetting : algorithmSettings) {
				groupService.fillVariableGroups(algorithmSetting.getVariableGroups());
			}
		}
	}

}
