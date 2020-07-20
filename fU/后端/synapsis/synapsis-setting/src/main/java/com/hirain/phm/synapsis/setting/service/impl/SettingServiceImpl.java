/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hirain.phm.synapsis.setting.Setting;
import com.hirain.phm.synapsis.setting.dao.SettingMapper;
import com.hirain.phm.synapsis.setting.service.SettingService;

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

	@Override
	@Transactional
	public Setting save(Setting setting) {
		settingMapper.insertGenerateKey(setting);
		return setting;
	}

	/**
	 * @see com.hirain.phm.synapsis.setting.service.SettingService#update(com.hirain.phm.synapsis.setting.Setting)
	 */
	@Override
	@Transactional
	public void update(Setting setting) {
		settingMapper.updateByKey(setting);
	}

	public void updateNotNull(Setting setting) {
		settingMapper.updateNotNullByKey(setting);
	}

	/**
	 * @see com.hirain.phm.synapsis.setting.service.SettingService#changeCurrent(java.lang.Integer)
	 */
	@Override
	@Transactional
	public void changeCurrent(Integer id) {
		Setting oldCurrent = selectCurrent();
		if (oldCurrent != null && oldCurrent.getId().equals(id)) {
			return;
		}
		if (oldCurrent != null) {
			oldCurrent.setSelected(false);
			settingMapper.updateByPrimaryKeySelective(oldCurrent);
		}
		Setting newCurrent = selectById(id);
		newCurrent.setSelected(true);
		settingMapper.updateByPrimaryKeySelective(newCurrent);
	}

	/**
	 */
	@Override
	@Transactional
	public void delete(int settingId) {
		settingMapper.deleteByPrimaryKey(settingId);
	}

	@Override
	public List<Setting> selectAll() {
		return settingMapper.selectAllByTime();
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
		return settingMapper.selectCurrent();
	}

	/**
	 * @return
	 * @see com.hirain.phm.synapsis.setting.service.SettingService#selectWithDetail(int)
	 */
	@Override
	public Setting selectWithDetail(int id) {
		return settingMapper.selectDetails(id);
	}

}
