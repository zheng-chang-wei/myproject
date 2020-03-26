/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.result.ecn;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.hirain.phm.synapsis.setting.BoardSetting;
import com.hirain.phm.synapsis.setting.Setting;
import com.hirain.phm.synapsis.setting.Subsystem;
import com.hirain.phm.synapsis.setting.Variable;
import com.hirain.phm.synapsis.setting.VariableGroup;
import com.hirain.phm.synapsis.setting.db.SettingDbService;
import com.hirain.phm.synapsis.setting.db.VariableDbService;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 19, 2020 4:52:35 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 19, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Component
public class TestSettingDbService implements SettingDbService, VariableDbService {

	private static Setting setting;
	static {
		setting = TestObjectUtils.getSetting();
		setting.setSelected(true);
	}

	/**
	 * @see com.hirain.phm.synapsis.setting.db.SettingDbService#selectCurrent()
	 */
	@Override
	public Setting selectCurrent() {
		return setting;
	}

	/**
	 * @see com.hirain.phm.synapsis.setting.db.SettingDbService#saveOrUpdate(com.hirain.phm.synapsis.setting.Setting)
	 */
	@Override
	public void saveOrUpdate(Setting setting) {
		TestSettingDbService.setting = setting;
	}

	/**
	 * @see com.hirain.phm.synapsis.setting.db.SettingDbService#selectWithDetail(int)
	 */
	@Override
	public Setting selectWithDetail(int settingId) {
		return setting;
	}

	/**
	 * @see com.hirain.phm.synapsis.setting.db.SettingDbService#changeCurrent(java.lang.Integer)
	 */
	@Override
	public void changeCurrent(Integer id) {

	}

	/**
	 * @see com.hirain.phm.synapsis.setting.db.SettingDbService#selectById(int)
	 */
	@Override
	public Setting selectById(int settingId) {
		return setting;
	}

	/**
	 * @see com.hirain.phm.synapsis.setting.db.SettingDbService#delete(int)
	 */
	@Override
	public void delete(int settingId) {

	}

	/**
	 * @see com.hirain.phm.synapsis.setting.db.SettingDbService#selectAllSubsystems()
	 */
	@Override
	public List<Subsystem> selectAllSubsystems() {
		return null;
	}

	/**
	 * @see com.hirain.phm.synapsis.setting.db.SettingDbService#selectAll()
	 */
	@Override
	public List<Setting> selectAll() {
		return Arrays.asList(setting);
	}

	private static int count = 1;

	/**
	 * @see com.hirain.phm.synapsis.setting.db.VariableDbService#saveGroups(int, java.util.List)
	 */
	@Override
	public void saveGroups(int boardId, List<VariableGroup> groups) {
		groups.get(0).setId(count++);
		List<BoardSetting> boardSettings = setting.getBoardSettings();
		for (BoardSetting boardSetting : boardSettings) {
			if (boardSetting.getId() == boardId) {
				boardSetting.getVariableGroups().addAll(groups);
			}
		}
	}

	/**
	 * @see com.hirain.phm.synapsis.setting.db.VariableDbService#insertVariable(java.lang.String, com.hirain.phm.synapsis.setting.Variable)
	 */
	@Override
	public void insertVariable(String type, Variable variable) {

	}

}
