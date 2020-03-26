package com.hirain.phm.bd.ground.authority.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hirain.phm.bd.ground.authority.dao.GroundRoleMapper;
import com.hirain.phm.bd.ground.authority.dao.GroundRoleMenuMapper;
import com.hirain.phm.bd.ground.authority.domain.GroundRole;
import com.hirain.phm.bd.ground.authority.domain.GroundRoleMenu;
import com.hirain.phm.bd.ground.authority.domain.Menu;
import com.hirain.phm.bd.ground.authority.service.GroundRoleMenuServie;
import com.hirain.phm.bd.ground.authority.service.GroundRoleService;
import com.hirain.phm.bd.ground.authority.service.GroundUserRoleService;
import com.hirain.phm.bd.ground.common.service.BaseService;

import tk.mybatis.mapper.entity.Example;

@Service("groundRoleService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class GroundRoleServiceImpl extends BaseService<GroundRole> implements GroundRoleService {

	@Autowired
	private GroundRoleMapper roleMapper;

	@Autowired
	private GroundRoleMenuMapper roleMenuMapper;

	@Autowired
	private GroundUserRoleService userRoleService;

	@Autowired
	private GroundRoleMenuServie roleMenuService;

	@Override
	public List<GroundRole> findUserRole(String userName) {
		return roleMapper.findUserRoleByUserName(userName);
	}

	@Override
	public List<GroundRole> findRoleByUserId(Long userId) {
		return roleMapper.findUserRoleById(userId);
	}

	@Override
	public List<GroundRole> findRoleWithMenuByUserId(Long userId) {
		List<GroundRole> roles = roleMapper.findUserRoleById(userId);
		roles.forEach(r -> {
			List<Menu> menus = roleMenuService.findMenusByRoleId(r.getGroundRoleId());
			r.setMenus(menus);
		});
		return roles;
	}

	@Override
	public GroundRole findByName(String roleName) {
		Example example = new Example(GroundRole.class);
		example.createCriteria().andCondition("lower(role_name)=", roleName.toLowerCase());
		List<GroundRole> list = selectByExample(example);
		return list.isEmpty() ? null : list.get(0);
	}

	@Override
	@Transactional
	public void addRole(GroundRole role, Long[] menuIds) {
		role.setCreateTime(new Date());
		save(role);
		setRoleMenus(role, menuIds);
	}

	private void setRoleMenus(GroundRole role, Long[] menuIds) {
		Arrays.stream(menuIds).forEach(menuId -> {
			GroundRoleMenu rm = new GroundRoleMenu();
			rm.setMenuId(menuId);
			rm.setGroundRoleId(role.getGroundRoleId());
			roleMenuMapper.insert(rm);
		});
	}

	@Override
	@Transactional
	public void deleteRoles(String roleIds) {
		List<String> list = Arrays.asList(roleIds.split(","));
		batchDelete(list, "groundRoleId", GroundRole.class);

		roleMenuService.deleteRoleMenusByRoleId(roleIds);
		userRoleService.deleteUserRolesByRoleId(list);

	}

	@Override
	public GroundRole findRoleWithMenus(Long roleId) {
		return roleMapper.findRoleWithMenuById(roleId);
	}

	@Override
	@Transactional
	public void updateRole(GroundRole role, Long[] menuIds) {
		role.setModifyTime(new Date());
		updateNotNull(role);
		Example example = new Example(GroundRoleMenu.class);
		example.createCriteria().andCondition("ground_role_id=", role.getGroundRoleId());
		roleMenuMapper.deleteByExample(example);
		setRoleMenus(role, menuIds);
	}

	@Override
	public List<GroundRole> listRolesByRoleName(String roleName) {
		return roleMapper.listRolesByRoleName("%" + roleName + "%");
	}

}
