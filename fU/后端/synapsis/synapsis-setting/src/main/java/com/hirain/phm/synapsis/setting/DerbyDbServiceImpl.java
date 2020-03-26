/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirain.phm.synapsis.setting.db.SettingDbService;
import com.hirain.phm.synapsis.setting.db.SubsystemService;
import com.hirain.phm.synapsis.setting.db.VariableDbService;
import com.hirain.phm.synapsis.setting.service.SettingService;
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

	/**
	 * @see com.hirain.phm.synapsis.setting.db.SettingDbService#selectCurrent()
	 */
	@Override
	public Setting selectCurrent() {
		return settingService.selectCurrent();
	}

	/**
	 * @see com.hirain.phm.synapsis.setting.db.SettingDbService#saveOrUpdate(com.hirain.phm.synapsis.setting.Setting)
	 */
	@Override
	public void saveOrUpdate(Setting setting) {
		settingService.saveOrUpdate(setting);
	}

	/**
	 * @see com.hirain.phm.synapsis.setting.db.SettingDbService#selectWithDetail(int)
	 */
	@Override
	public Setting selectWithDetail(int settingId) {
		return settingService.selectWithDetail(settingId);
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
	public void saveGroups(int boardId, List<VariableGroup> groups) {
		groupService.saveBoardVariables(boardId, groups);
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

}
