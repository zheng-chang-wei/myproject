package com.hirain.phm.bd.ground.authority.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hirain.phm.bd.ground.authority.dao.GroundRoleMenuMapper;
import com.hirain.phm.bd.ground.authority.domain.GroundRoleMenu;
import com.hirain.phm.bd.ground.authority.domain.Menu;
import com.hirain.phm.bd.ground.authority.service.GroundRoleMenuServie;
import com.hirain.phm.bd.ground.common.service.BaseService;

@Service("groundRoleMenuService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class GroundRoleMenuServiceImpl extends BaseService<GroundRoleMenu> implements GroundRoleMenuServie {

	@Autowired
	private GroundRoleMenuMapper mapper;

	@Override
	@Transactional
	public void deleteRoleMenusByRoleId(String roleIds) {
		List<String> list = Arrays.asList(roleIds.split(","));
		batchDelete(list, "groundRoleId", GroundRoleMenu.class);
	}

	@Override
	@Transactional
	public void deleteRoleMenusByMenuId(String menuIds) {
		List<String> list = Arrays.asList(menuIds.split(","));
		batchDelete(list, "menuId", GroundRoleMenu.class);
	}

	@Override
	public List<Menu> findMenusByRoleId(Long roleId) {
		return mapper.findMenusByRoleId(roleId);
	}
}
