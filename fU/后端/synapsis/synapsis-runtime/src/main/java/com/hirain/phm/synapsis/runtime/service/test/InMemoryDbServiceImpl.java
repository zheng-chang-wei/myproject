/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.runtime.service.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.hirain.phm.synapsis.setting.Setting;
import com.hirain.phm.synapsis.setting.Subsystem;
import com.hirain.phm.synapsis.setting.db.SettingDbService;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 17, 2020 3:54:24 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 17, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Service
@Profile("test")
@Primary
public class InMemoryDbServiceImpl implements SettingDbService {

	private Map<Integer, Setting> cache = new HashMap<>();

	private static int count = 1;

	private List<Subsystem> subsystems;

	/**
	 * @see com.hirain.phm.synapsis.setting.db.SettingDbService#selectCurrent()
	 */
	@Override
	public Setting selectCurrent() {
		for (Entry<Integer, Setting> entry : cache.entrySet()) {
			Setting setting = entry.getValue();
			if (setting.getSelected()) {
				return setting;
			}
		}
		return null;
	}

	/**
	 * @see com.hirain.phm.synapsis.setting.db.SettingDbService#saveOrUpdate(com.hirain.phm.synapsis.setting.Setting)
	 */
	@Override
	public void saveOrUpdate(Setting setting) {
		Integer id = setting.getId();
		if (id != null) {
			cache.put(id, setting);
		} else {
			id = count++;
			setting.setId(id);
			cache.put(id, setting);
		}
	}

	/**
	 * @see com.hirain.phm.synapsis.setting.db.SettingDbService#selectWithDetail(int)
	 */
	@Override
	public Setting selectWithDetail(int settingId) {
		return cache.get(settingId);
	}

	/**
	 * @see com.hirain.phm.synapsis.setting.db.SettingDbService#changeCurrent(java.lang.Integer)
	 */
	@Override
	public void changeCurrent(Integer id) {
		Setting oldCurrent = selectCurrent();
		oldCurrent.setSelected(false);
		Setting newCurrent = cache.get(id);
		newCurrent.setSelected(true);
	}

	/**
	 * @see com.hirain.phm.synapsis.setting.db.SettingDbService#selectById(int)
	 */
	@Override
	public Setting selectById(int settingId) {
		return cache.get(settingId);
	}

	/**
	 * @see com.hirain.phm.synapsis.setting.db.SettingDbService#delete(int)
	 */
	@Override
	public void delete(int settingId) {
		cache.remove(settingId);
	}

	/**
	 * @see com.hirain.phm.synapsis.setting.db.SettingDbService#selectAllSubsystems()
	 */
	@Override
	public List<Subsystem> selectAllSubsystems() {
		if (subsystems == null) {
			Subsystem subsystem = new Subsystem();
			subsystem.setId(1);
			subsystem.setName("受电弓");
			subsystems = new ArrayList<>();
			subsystems.add(subsystem);
		}
		return subsystems;
	}

	/**
	 * @see com.hirain.phm.synapsis.setting.db.SettingDbService#selectAll()
	 */
	@Override
	public List<Setting> selectAll() {
		return new ArrayList<>(cache.values());
	}

}
