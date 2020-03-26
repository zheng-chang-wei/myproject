package com.hirain.phm.bd.ground.authority.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hirain.phm.bd.ground.authority.dao.RepairUserRoleMapper;
import com.hirain.phm.bd.ground.authority.domain.RepairUserRole;
import com.hirain.phm.bd.ground.authority.service.ApprovalRoleUserService;
import com.hirain.phm.bd.ground.common.service.BaseService;

@Service
public class ApprovalRoleUserServiceImpl extends BaseService<RepairUserRole> implements ApprovalRoleUserService {

	@Autowired
	private RepairUserRoleMapper mapper;

	@Override
	@Transactional
	public void deleteApprovalRoleUserByUserId(List<String> userIds) {
		batchDelete(userIds, "userId", RepairUserRole.class);
	}

	public List<Integer> getRepairRolesByUserId(Long userId) {
		return mapper.getRepairRolesByUserId(userId);
	}
}
