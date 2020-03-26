/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting.service;

import java.util.List;

import com.hirain.phm.synapsis.setting.VariableGroup;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 3, 2019 4:40:27 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 3, 2019 jianwen.xin@hirain.com 1.0 create file
 */
public interface VariableGroupService {

	/**
	 * 获取算法的变量组
	 * 
	 * @param id
	 * @return
	 */
	List<VariableGroup> selectByAlgorithm(int id);

	/**
	 * 获取板卡的变量组
	 * 
	 * @param boardId
	 */
	List<VariableGroup> selectByBoard(int boardId);

	/**
	 * 查询变量组的变量
	 * 
	 * @param variableGroups
	 */
	void fillVariableGroups(List<VariableGroup> variableGroups);

	/**
	 * 查询变量组的变量
	 * 
	 * @param group
	 */
	void fillVariableGroup(VariableGroup group);

	/**
	 * 查询存储变量组
	 * 
	 * @param settingId
	 * @return
	 */
	List<VariableGroup> selectStoreVariables(int settingId);

	/**
	 * @param boardId
	 * @param variableGroups
	 */
	void saveBoardVariables(Integer boardId, List<VariableGroup> variableGroups);

	/**
	 * @param id
	 * @param variableGroups
	 */
	void saveAlgorithmVariables(Integer algorithmId, List<VariableGroup> variableGroups);

	/**
	 * @param settingId
	 * @param group
	 * @param id
	 */
	void insertTimeGroup(Integer settingId, VariableGroup group);

	/**
	 * @param settingId
	 * @param storeVariables
	 */
	void insertStoreGroups(Integer settingId, List<VariableGroup> groups);

	/**
	 * @param id
	 */
	void deleteTimeVariables(Integer settingId);

	/**
	 * @param settingId
	 */
	void deleteStoreVariables(Integer settingId);

	/**
	 * @param id
	 * @param variableGroups
	 */
	void deleteBoardVariables(Integer boardId, List<VariableGroup> variableGroups);

	/**
	 * @param id
	 * @param variableGroups
	 */
	void deleteAlgorithmVariables(Integer algorithmId, List<VariableGroup> variableGroups);

	/**
	 * @param variableGroups
	 */
	void insertVariables(List<VariableGroup> variableGroups);

	/**
	 * @param groupId
	 * @return
	 */
	VariableGroup select(int groupId);

}
