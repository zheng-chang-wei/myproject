/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
/**
 * @Version 1.0   
 * @Author weijia.kong@hirain.com
 * @Created May 29, 2019 2:04:39 PM
 * @Description
 * <p>
 * @Modification
 * <p>Date         Author      Version     Description  
 * <p>May 29, 2019     weijia.kong@hirain.com            1.0        create file 
 */
package com.hirain.phm.bd.ground.subhealth.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirain.phm.bd.ground.common.service.BaseService;
import com.hirain.phm.bd.ground.subhealth.dao.SubhealthInfoMapper;
import com.hirain.phm.bd.ground.subhealth.domain.SubhealthInfo;
import com.hirain.phm.bd.ground.subhealth.service.SubhealthInfoService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class SubhealthInfoServiceImpl extends BaseService<SubhealthInfo> implements SubhealthInfoService {

	@Autowired
	private SubhealthInfoMapper mapper;

	/**
	 * @see com.hirain.phm.bd.ground.subhealth.service.SubhealthInfoService#selectAllWithDetail(java.lang.String)
	 */
	@Override
	public List<SubhealthInfo> selectAllWithDetail(String project) {
		return mapper.selectAllWithDetail(project);
	}

	/**
	 * @see com.hirain.phm.bd.ground.subhealth.service.SubhealthInfoService#chechDuplicate(com.hirain.phm.bd.ground.subhealth.domain.SubhealthInfo)
	 */
	@Override
	public boolean chechDuplicate(SubhealthInfo info) {
		SubhealthInfo exist = isCodeExist(info.getProjectId(), info.getSubhealthCode());
		if (exist != null && exist.getId() != info.getId()) {
			return true;
		}
		exist = isNameExist(info.getProjectId(), info.getSubhealthName());
		if (exist != null && exist.getId() != info.getId()) {
			return true;
		}
		return false;
	}

	private SubhealthInfo isCodeExist(Integer projectId, Integer code) {
		Example example = new Example(SubhealthInfo.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("projectId", projectId);
		criteria.andEqualTo("subhealthCode", code);
		List<SubhealthInfo> list = mapper.selectByExample(example);
		return list == null || list.isEmpty() ? null : list.get(0);
	}

	private SubhealthInfo isNameExist(Integer projectId, String name) {
		Example example = new Example(SubhealthInfo.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("projectId", projectId);
		criteria.andEqualTo("subhealthName", name);
		List<SubhealthInfo> list = mapper.selectByExample(example);
		return list == null || list.isEmpty() ? null : list.get(0);
	}

	/**
	 * @see com.hirain.phm.bd.ground.subhealth.service.SubhealthInfoService#selectByProject(java.lang.String)
	 */
	@Override
	public List<SubhealthInfo> selectByProject(String project) {
		return mapper.selectByProject(project);
	}

	/**
	 * @see com.hirain.phm.bd.ground.subhealth.service.SubhealthInfoService#selectWithDetail(java.lang.Integer)
	 */
	@Override
	public SubhealthInfo selectWithDetail(Integer subhealthInfoId) {
		return mapper.selectWithDetail(subhealthInfoId);
	}

	/**
	 * @see com.hirain.phm.bd.ground.subhealth.service.SubhealthInfoService#insertList(java.util.List)
	 */
	@Override
	public void insertList(List<SubhealthInfo> list) {
		mapper.insertList(list);
	}
}
