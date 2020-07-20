/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirain.phm.synapsis.constant.BoardType;
import com.hirain.phm.synapsis.exception.SynapsisException;
import com.hirain.phm.synapsis.setting.db.SettingDbService;
import com.hirain.phm.synapsis.setting.db.SubsystemService;
import com.hirain.phm.synapsis.setting.db.VariableDbService;
import com.hirain.phm.synapsis.setting.service.AlgorithmSettingService;
import com.hirain.phm.synapsis.setting.service.BoardSettingService;
import com.hirain.phm.synapsis.setting.service.SettingService;
import com.hirain.phm.synapsis.setting.service.StoreSettingService;
import com.hirain.phm.synapsis.setting.service.TimeSettingService;
import com.hirain.phm.synapsis.setting.service.VariableGroupService;
import com.hirain.phm.synapsis.setting.service.VariableManager;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 17, 2020 3:40:19 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 17, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Service
public class DerbyDbServiceImpl implements SettingDbService, VariableDbService {

	@Autowired
	private SettingService settingService;

	@Autowired
	private SubsystemService subsystemService;

	@Autowired
	private VariableGroupService groupService;

	@Autowired
	private VariableManager variableManager;

	@Autowired
	private BoardSettingService boardService;

	@Autowired
	private AlgorithmSettingService algorithmService;

	@Autowired
	private TimeSettingService timeService;

	@Autowired
	private StoreSettingService storeService;

	/**
	 * @throws SynapsisException
	 * @see com.hirain.phm.synapsis.setting.db.SettingDbService#selectCurrent()
	 */
	@Override
	public Setting selectCurrent() throws SynapsisException {
		Setting setting = settingService.selectCurrent();
		if (setting == null) {
			return null;
		}
		return selectWithDetail(setting.getId());
	}

	/**
	 * @see com.hirain.phm.synapsis.setting.db.SettingDbService#selectWithDetail(int)
	 */
	@Override
	public Setting selectWithDetail(int settingId) {
		Setting setting = settingService.selectWithDetail(settingId);
		List<BoardSetting> boardSettings = boardService.selectBySettingId(settingId);
		setting.setBoardSettings(boardSettings);
		TimeSetting timeSetting = setting.getTimeSetting();
		if (timeSetting != null && timeSetting.getTimeVariables() != null) {
			groupService.fillVariableGroup(timeSetting.getTimeVariables());
		}
		StoreSetting storeSetting = setting.getStoreSetting();
		if (storeSetting != null) {
			groupService.fillVariableGroups(storeSetting.getStoreVariables());
		}
		postAlgorithm(setting.getAlgorithmSettings());
		return setting;
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

	/**
	 * @see com.hirain.phm.synapsis.setting.db.SettingDbService#changeCurrent(java.lang.Integer)
	 */
	@Override
	public void changeCurrent(Integer id) {
		settingService.changeCurrent(id);
	}

	/**
	 * @see com.hirain.phm.synapsis.setting.db.SettingDbService#selectById(int)
	 */
	@Override
	public Setting selectById(int settingId) {
		return settingService.selectById(settingId);
	}

	/**
	 * @see com.hirain.phm.synapsis.setting.db.SettingDbService#delete(int)
	 */
	@Override
	public void delete(int settingId) {
		settingService.delete(settingId);
		boardService.deleteBySettingId(settingId);
		algorithmService.deleteBySettingId(settingId);

		timeService.deleteBySettingId(settingId);
		groupService.deleteTimeVariables(settingId);

		storeService.deleteBySettingId(settingId);
		groupService.deleteStoreVariables(settingId);
	}

	/**
	 * @see com.hirain.phm.synapsis.setting.db.SettingDbService#selectAllSubsystems()
	 */
	@Override
	public List<Subsystem> selectAllSubsystems() {
		return subsystemService.selectAllSubsystems();
	}

	/**
	 * @see com.hirain.phm.synapsis.setting.db.SettingDbService#selectAll()
	 */
	@Override
	public List<Setting> selectAll() {
		return settingService.selectAll();
	}

	/**
	 * @see com.hirain.phm.synapsis.setting.db.VariableDbService#saveGroups(int, java.util.List)
	 */
	@Override
	@Deprecated
	public void saveGroups(int boardId, List<VariableGroup> groups) {
		groupService.saveBoardVariables(boardId, groups);
	}

	@Override
	public void saveVariableGroup(VariableGroup group) {
		groupService.insertVariables(Arrays.asList(group));
	}

	/**
	 * @see com.hirain.phm.synapsis.setting.db.VariableDbService#selectVariableGroup(java.lang.Integer)
	 */
	@Override
	public VariableGroup selectVariableGroup(Integer groupId) {
		return groupService.select(groupId);
	}

	/**
	 * @see com.hirain.phm.synapsis.setting.db.VariableDbService#insertVariable(com.hirain.phm.synapsis.setting.Variable)
	 */
	@Override
	public void insertVariable(String type, Variable variable) {
		variableManager.insertVariable(type, variable);
	}

	/**
	 * @see com.hirain.phm.synapsis.setting.db.VariableDbService#delete(java.lang.String, java.lang.Long)
	 */
	@Override
	public void delete(String type, Long variableId) {
		variableManager.delete(type, variableId);
	}

	/**
	 * @see com.hirain.phm.synapsis.setting.db.SettingDbService#selectAlgorithmBySubsystemId(java.lang.Integer)
	 */
	@Override
	public List<AlgorithmSetting> selectAlgorithmBySubsystemId(Integer subsystemId) {
		return algorithmService.selectBySubsystemId(subsystemId);
	}

	/**
	 * @see com.hirain.phm.synapsis.setting.db.SettingDbService#selectByName(java.lang.String)
	 */
	@Override
	public Setting selectByName(String name) {
		return settingService.selectByName(name);
	}

	/**
	 * @see com.hirain.phm.synapsis.setting.db.SettingDbService#findBoardSetting(java.lang.Integer, java.lang.String)
	 */
	@Override
	public BoardSetting findBoardSetting(Integer settingId, String type) {
		List<BoardSetting> boardSetting = boardService.selectBySettingId(settingId);
		BoardSetting setting = boardSetting.stream().filter(b -> {
			BoardType boardType = BoardType.valueOf(b.getType());
			return boardType.getType().equals(type);
		}).findFirst().get();
		return setting;
	}

}
