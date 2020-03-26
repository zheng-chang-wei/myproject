/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.maintenance.service.impl;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirain.phm.bd.ground.authority.domain.User;
import com.hirain.phm.bd.ground.common.service.BaseService;
import com.hirain.phm.bd.ground.maintenance.dao.ConfigVariableMapper;
import com.hirain.phm.bd.ground.maintenance.domain.ConfigVariable;
import com.hirain.phm.bd.ground.maintenance.service.ConfigVariableService;
import com.hirain.phm.bode.core.util.StringUtil;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * @Version 1.0
 * @Author changwei.zheng@hirain.com
 * @Created 2020年2月17日 上午10:48:02
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2020年2月17日 changwei.zheng@hirain.com 1.0 create file
 */
@Service
public class ConfigVariableServiceImpl extends BaseService<ConfigVariable> implements ConfigVariableService {

	@Autowired
	ConfigVariableMapper configVariableMapper;

	@Override
	public List<ConfigVariable> listVariable(Long id) {
		return configVariableMapper.listVariable(id);
	}

	/**
	 * 名称是否重复
	 * 
	 * @param configVariable
	 * @return
	 */
	@Override
	public boolean isRepeat(ConfigVariable configVariable) {
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		ConfigVariable selectConfigVariable = selectByName(configVariable.getName(), user.getId());
		// 添加
		if (configVariable.getId() == null) {
			if (selectConfigVariable == null) {
				return false;
			} else {
				return true;
			}
		} else {
			// 编辑
			if (selectConfigVariable == null) {
				return false;
			} else if (selectConfigVariable.getId().equals(configVariable.getId())) {
				return false;
			} else {
				return true;
			}
		}
	}

	private ConfigVariable selectByName(String name, Long userId) {
		Example example = new Example(ConfigVariable.class);
		Criteria criteria = example.createCriteria();
		if (StringUtil.isNotEmpty(name)) {
			criteria.andEqualTo("name", name);
			criteria.andEqualTo("userId", userId);
		}
		List<ConfigVariable> list = configVariableMapper.selectByExample(example);
		if (list != null && list.size() != 0) {
			return list.get(0);
		}
		return null;
	}
}
