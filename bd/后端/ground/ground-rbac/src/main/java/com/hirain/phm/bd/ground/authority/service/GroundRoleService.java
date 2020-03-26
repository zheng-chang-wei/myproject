package com.hirain.phm.bd.ground.authority.service;

import java.util.List;

import com.hirain.phm.bd.ground.authority.domain.GroundRole;
import com.hirain.phm.bd.ground.common.service.IService;

public interface GroundRoleService extends IService<GroundRole> {

	List<GroundRole> findUserRole(String userName);

	List<GroundRole> findRoleByUserId(Long userId);

	GroundRole findRoleWithMenus(Long roleId);

	GroundRole findByName(String roleName);

	void addRole(GroundRole role, Long[] menuIds);

	void updateRole(GroundRole role, Long[] menuIds);

	void deleteRoles(String roleIds);

	List<GroundRole> findRoleWithMenuByUserId(Long userId);

	List<GroundRole> listRolesByRoleName(String roleName);
}
