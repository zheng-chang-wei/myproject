package com.hirain.phm.bd.ground.authority.service;

import java.util.List;

import com.hirain.phm.bd.ground.authority.domain.GroundUserRole;
import com.hirain.phm.bd.ground.common.service.IService;

public interface GroundUserRoleService extends IService<GroundUserRole> {

	void deleteUserRolesByRoleId(List<String> roleIds);

	void deleteUserRolesByUserId(List<String> userIds);
}
