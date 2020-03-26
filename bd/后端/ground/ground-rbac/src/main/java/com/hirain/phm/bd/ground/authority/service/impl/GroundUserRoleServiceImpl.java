package com.hirain.phm.bd.ground.authority.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hirain.phm.bd.ground.authority.domain.GroundUserRole;
import com.hirain.phm.bd.ground.authority.service.GroundUserRoleService;
import com.hirain.phm.bd.ground.common.service.BaseService;

@Service("groundUserRoleService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class GroundUserRoleServiceImpl extends BaseService<GroundUserRole> implements GroundUserRoleService {

	@Override
	@Transactional
	public void deleteUserRolesByRoleId(List<String> roleIds) {
		this.batchDelete(roleIds, "groundRoleId", GroundUserRole.class);
	}

	@Override
	@Transactional
	public void deleteUserRolesByUserId(List<String> userIds) {
		this.batchDelete(userIds, "userId", GroundUserRole.class);
	}

}
