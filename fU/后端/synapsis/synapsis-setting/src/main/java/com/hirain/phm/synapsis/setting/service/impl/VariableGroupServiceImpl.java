/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirain.phm.synapsis.setting.Variable;
import com.hirain.phm.synapsis.setting.VariableGroup;
import com.hirain.phm.synapsis.setting.dao.VariableGroupMapper;
import com.hirain.phm.synapsis.setting.service.VariableGroupService;
import com.hirain.phm.synapsis.setting.service.VariableManager;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 3, 2019 4:41:23 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 3, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Service
public class VariableGroupServiceImpl implements VariableGroupService {

	@Autowired
	private VariableGroupMapper mapper;

	@Autowired
	private VariableManager manager;

	/**
	 * @see com.hirain.phm.synapsis.setting.service.VariableGroupService#selectByAlgorithm(int)
	 */
	@Override
	public List<VariableGroup> selectByAlgorithm(int id) {
		List<VariableGroup> variableGroups = mapper.selectByAlgorithm(id);
		fillVariableGroups(variableGroups);
		return variableGroups;
	}

	/**
	 * @see com.hirain.phm.synapsis.setting.service.VariableGroupService#selectByBoard(int)
	 */
	@Override
	public List<VariableGroup> selectByBoard(int boardId) {
		List<VariableGroup> groups = mapper.selectByBoard(boardId);
		fillVariableGroups(groups);
		return groups;
	}

	@Override
	public VariableGroup select(int groupId) {
		VariableGroup group = mapper.selectByPrimaryKey(groupId);
		fillVariableGroup(group);
		return group;
	}

	/**
	 * @param variableGroups
	 */
	@Override
	public void fillVariableGroups(List<VariableGroup> variableGroups) {
		if (variableGroups != null) {
			for (VariableGroup group : variableGroups) {
				fillVariableGroup(group);
			}
		}
	}

	/**
	 * @param group
	 */
	@Override
	public void fillVariableGroup(VariableGroup group) {
		List<? extends Variable> variables = getVariables(group.getId(), group.getType());
		group.setVariables(variables);
	}

	/**
	 * 查询变量组的变量
	 * 
	 * @param groupId
	 * @param type
	 * @return
	 */
	private List<? extends Variable> getVariables(int groupId, String type) {
		return manager.selectVariables(type, groupId);
	}

	/**
	 * @see com.hirain.phm.synapsis.setting.service.VariableGroupService#selectStoreVariables(int)
	 */
	@Override
	public List<VariableGroup> selectStoreVariables(int settingId) {
		return mapper.selectStoreVariables(settingId);
	}

	/**
	 * @see com.hirain.phm.synapsis.setting.service.VariableGroupService#saveBoardVariables(java.lang.Integer, java.util.List)
	 */
	@Override
	public void saveBoardVariables(Integer boardId, List<VariableGroup> variableGroups) {
		if (variableGroups != null && !variableGroups.isEmpty()) {
			insertVariables(variableGroups);
			mapper.insertBoardGroups(boardId, variableGroups);
		}
	}

	/**
	 * @see com.hirain.phm.synapsis.setting.service.VariableGroupService#saveAlgorithmVariables(java.lang.Integer, java.util.List)
	 */
	@Override
	public void saveAlgorithmVariables(Integer algorithmId, List<VariableGroup> variableGroups) {
		if (variableGroups != null && !variableGroups.isEmpty()) {
			insertVariables(variableGroups);
			mapper.insertAlgorithmGroups(algorithmId, variableGroups);
		}
	}

	/**
	 * @param variableGroups
	 */
	@Override
	public void insertVariables(List<VariableGroup> variableGroups) {
		if (!variableGroups.isEmpty()) {
			for (VariableGroup group : variableGroups) {
				mapper.insertGenerateKey(group);
				manager.insertVariables(group.getType(), group.getId(), group.getVariables());
			}
		}
	}

	/**
	 * @see com.hirain.phm.synapsis.setting.service.VariableGroupService#insertTimeGroup(java.lang.Integer, VariableGroup)
	 */
	@Override
	public void insertTimeGroup(Integer settingId, VariableGroup group) {
		insertVariables(Arrays.asList(group));
	}

	/**
	 * @see com.hirain.phm.synapsis.setting.service.VariableGroupService#insertStoreGroups(java.lang.Integer, java.util.List)
	 */
	@Override
	public void insertStoreGroups(Integer settingId, List<VariableGroup> groups) {
		insertVariables(groups);
		mapper.insertStoreGroups(settingId, groups);
	}

	/**
	 * @see com.hirain.phm.synapsis.setting.service.VariableGroupService#deleteTimeVariables(java.lang.Integer)
	 */
	@Override
	public void deleteTimeVariables(Integer settingId) {
		List<VariableGroup> groups = mapper.selectTimeVariables(settingId);
		delete(groups);
	}

	/**
	 * @see com.hirain.phm.synapsis.setting.service.VariableGroupService#deleteStoreVariables(java.lang.Integer)
	 */
	@Override
	public void deleteStoreVariables(Integer settingId) {
		List<VariableGroup> groups = mapper.selectStoreVariables(settingId);
		delete(groups);
		mapper.deleteStoreVariables(settingId);
	}

	/**
	 * @see com.hirain.phm.synapsis.setting.service.VariableGroupService#deleteBoardVariables(java.lang.Integer, java.util.List)
	 */
	@Override
	public void deleteBoardVariables(Integer boardId, List<VariableGroup> variableGroups) {
		delete(variableGroups);
		mapper.deleteBoardVariables(boardId);
	}

	/**
	 * @see com.hirain.phm.synapsis.setting.service.VariableGroupService#deleteAlgorithmVariables(java.lang.Integer, java.util.List)
	 */
	@Override
	public void deleteAlgorithmVariables(Integer algorithmId, List<VariableGroup> variableGroups) {
		delete(variableGroups);
		mapper.deleteAlgorithmVariables(algorithmId);
	}

	/**
	 * @param groups
	 */
	private void delete(List<VariableGroup> groups) {
		for (VariableGroup group : groups) {
			manager.deleteVariables(group.getType(), group.getId());
			mapper.delete(group);
		}
	}

}
