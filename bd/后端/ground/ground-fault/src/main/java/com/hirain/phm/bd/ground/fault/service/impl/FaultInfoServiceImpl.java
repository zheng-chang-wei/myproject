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
	FaultInfoMapper faultInfoMapper;

	@Override
	public int insertFaultInfo(FaultInfo faultInfo) {
		faultInfoMapper.insertUseGeneratedKeys(faultInfo);
		return faultInfo.getId();
	}

	@Override
	public List<FaultInfo> findByNameAndCode(String faultName, int faultCode, Integer id) {
		Example example = new Example(FaultInfo.class);
		Criteria criteria = example.createCriteria();
		if (id != null) {
			criteria.andCondition("project_id=", id);
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

}
