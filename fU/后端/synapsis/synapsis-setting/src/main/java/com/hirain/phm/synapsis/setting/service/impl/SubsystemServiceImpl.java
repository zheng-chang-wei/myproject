/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirain.phm.synapsis.setting.Subsystem;
import com.hirain.phm.synapsis.setting.dao.SubsystemMapper;
import com.hirain.phm.synapsis.setting.db.SubsystemService;
import com.hirain.phm.synapsis.setting.service.AlgorithmSettingService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import tk.mybatis.mapper.util.StringUtil;

/**
 * @Version 1.0
 * @Author changwei.zheng@hirain.com
 * @Created 2019年12月24日 上午9:41:58
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年12月24日 changwei.zheng@hirain.com 1.0 create file
 */
@Service
public class SubsystemServiceImpl implements SubsystemService {

	@Autowired
	SubsystemMapper subsystemMapper;

	@Autowired
	AlgorithmSettingService algorithmSettingService;

	@Override
	public List<Subsystem> selectAllSubsystems() {
		return subsystemMapper.selectAll();
	}

	@Override
	public List<Subsystem> selectByLikeName(String name) {
		Example example = new Example(Subsystem.class);
		Criteria criteria = example.createCriteria();
		if (StringUtil.isNotEmpty(name)) {
			criteria.andLike("name", "%" + name + "%");
		}
		List<Subsystem> list = subsystemMapper.selectByExample(example);
		return list;
	}

	@Override
	public void save(Subsystem subsystem) {
		subsystemMapper.insertGenerateKey(subsystem);
	}

	@Override
	public void update(Subsystem subsystem) {
		subsystemMapper.updateByPrimaryKeySelective(subsystem);
	}

	@Override
	public void deleteById(Integer[] ids) {
		for (Integer id : ids) {
			subsystemMapper.deleteByPrimaryKey(id);
		}
	}

	/**
	 * 子系统名称是否重复
	 * 
	 * @param subsystem
	 * @return
	 */
	@Override
	public boolean isRepeat(Subsystem subsystem) {
		Subsystem selectSubsystem = selectByName(subsystem.getName());
		if (subsystem.getId() == null) {
			if (selectSubsystem == null) {
				return false;
			} else {
				return true;
			}
		} else {
			if (selectSubsystem == null) {
				return false;
			} else if (selectSubsystem.getId().equals(subsystem.getId())) {
				return false;
			} else {
				return true;
			}
		}
	}

	private Subsystem selectByName(String name) {
		Example example = new Example(Subsystem.class);
		Criteria criteria = example.createCriteria();
		if (StringUtil.isNotEmpty(name)) {
			criteria.andEqualTo("name", name);
		}
		List<Subsystem> list = subsystemMapper.selectByExample(example);
		if (list != null && list.size() != 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public void save(List<Subsystem> subsystems) {
		subsystemMapper.deleteALl();
		for (Subsystem subsystem : subsystems) {
			if (subsystem.getId() == null) {
				subsystemMapper.insertGenerateKey(subsystem);
			} else {
				subsystemMapper.insertSelective(subsystem);
			}
		}
	}

}
