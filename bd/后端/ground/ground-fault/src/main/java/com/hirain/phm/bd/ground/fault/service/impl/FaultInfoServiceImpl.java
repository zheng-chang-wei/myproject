package com.hirain.phm.bd.ground.fault.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirain.phm.bd.ground.common.service.BaseService;
import com.hirain.phm.bd.ground.fault.dao.FaultInfoMapper;
import com.hirain.phm.bd.ground.fault.domain.FaultInfo;
import com.hirain.phm.bd.ground.fault.service.FaultInfoService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import tk.mybatis.mapper.util.StringUtil;

@Service
public class FaultInfoServiceImpl extends BaseService<FaultInfo> implements FaultInfoService {

	@Autowired
	private FaultInfoMapper faultInfoMapper;

	@Override
	public int insertFaultInfo(FaultInfo faultInfo) {
		faultInfoMapper.insertUseGeneratedKeys(faultInfo);
		return faultInfo.getId();
	}

	@Override
	public List<FaultInfo> findByNameAndCode(String faultName, int faultCode, Integer projectId) {
		Example example = new Example(FaultInfo.class);
		Criteria criteria = example.createCriteria();
		if (projectId != null) {
			criteria.andCondition("project_id=", projectId);
		}
		if (StringUtil.isNotEmpty(faultName)) {
			criteria.andCondition("fault_name=", faultName);
		}
		criteria.andCondition("fault_code=", faultCode);
		return selectByExample(example);
	}

	@Override
	public List<FaultInfo> selectAllGroupByFaultName() {
		return faultInfoMapper.selectAllGroupByParam();
	}

	/**
	 * @see com.hirain.phm.bd.ground.fault.service.FaultInfoService#findFaultInfoByProject(java.lang.String)
	 */
	@Override
	public List<FaultInfo> findFaultInfoByProject(String project) {
		return faultInfoMapper.findFaultInfoByProject(project);
	}

	/**
	 * @see com.hirain.phm.bd.ground.fault.service.FaultInfoService#selectAllWithDetail(java.lang.String)
	 */
	@Override
	public List<FaultInfo> selectAllWithDetail(String project) {
		return faultInfoMapper.selectAllWithDetail(project);
	}

	/**
	 * @see com.hirain.phm.bd.ground.fault.service.FaultInfoService#selectWithDetail(java.lang.Integer)
	 */
	@Override
	public FaultInfo selectWithDetail(Integer id) {
		return faultInfoMapper.selectWithDetail(id);
	}

	/**
	 * @see com.hirain.phm.bd.ground.fault.service.FaultInfoService#checkDuplicate(FaultInfo)
	 */
	@Override
	public boolean checkDuplicate(FaultInfo info) {
		FaultInfo exist = isCodeExist(info.getProjectId(), info.getFaultCode());
		if (exist != null && exist.getId() != info.getId()) {
			return true;
		}
		exist = isNameExist(info.getProjectId(), info.getFaultName());
		if (exist != null && exist.getId() != info.getId()) {
			return true;
		}
		return false;
	}

	private FaultInfo isCodeExist(Integer projectId, Integer code) {
		Example example = new Example(FaultInfo.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("projectId", projectId);
		criteria.andEqualTo("faultCode", code);
		List<FaultInfo> list = mapper.selectByExample(example);
		return list == null || list.isEmpty() ? null : list.get(0);
	}

	private FaultInfo isNameExist(Integer projectId, String name) {
		Example example = new Example(FaultInfo.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("projectId", projectId);
		criteria.andEqualTo("faultName", name);
		List<FaultInfo> list = mapper.selectByExample(example);
		return list == null || list.isEmpty() ? null : list.get(0);
	}

	/**
	 * @see com.hirain.phm.bd.ground.fault.service.FaultInfoService#insertList(java.util.List)
	 */
	@Override
	public void insertList(List<FaultInfo> list) {
		faultInfoMapper.insertList(list);
	}

}
