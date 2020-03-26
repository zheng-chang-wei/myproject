package com.hirain.phm.bd.ground.authority.service;

import java.util.List;

import com.hirain.phm.bd.ground.authority.domain.GroundRoleMenu;
import com.hirain.phm.bd.ground.authority.domain.Menu;
import com.hirain.phm.bd.ground.common.service.IService;

public interface GroundRoleMenuServie extends IService<GroundRoleMenu> {

	void deleteRoleMenusByRoleId(String roleIds);

	void deleteRoleMenusByMenuId(String menuIds);

	List<Menu> findMenusByRoleId(Long roleId);
}
