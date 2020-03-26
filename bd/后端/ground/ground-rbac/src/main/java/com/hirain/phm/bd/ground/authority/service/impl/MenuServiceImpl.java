package com.hirain.phm.bd.ground.authority.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hirain.phm.bd.ground.authority.dao.MenuMapper;
import com.hirain.phm.bd.ground.authority.domain.Menu;
import com.hirain.phm.bd.ground.authority.domain.User;
import com.hirain.phm.bd.ground.authority.service.GroundRoleMenuServie;
import com.hirain.phm.bd.ground.authority.service.MenuService;
import com.hirain.phm.bd.ground.authority.util.MenuTreeUtil;
import com.hirain.phm.bd.ground.common.service.BaseService;

@Service("menuService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MenuServiceImpl extends BaseService<Menu> implements MenuService {

	@Autowired
	private MenuMapper menuMapper;

	@Autowired
	private GroundRoleMenuServie groundRoleMenuService;

	@Override
	public List<Menu> findUserPermissions(String userName) {
		return this.menuMapper.findUserPermissions(userName);
	}

	@Override
	public List<Menu> getMenuTree(User user) {
		List<Menu> menus = this.menuMapper.findUserMenus(user.getUserName());
		MenuTreeUtil treeUtil = new MenuTreeUtil();
		return treeUtil.getChildTreeObjects(menus, 0);
	}

	@Override
	public List<Menu> getAllMenu(User user) {
		List<Menu> menus = this.menuMapper.findAllMenus();
		MenuTreeUtil treeUtil = new MenuTreeUtil();
		return treeUtil.getChildTreeObjects(menus, 0);
	}

	@Override
	@Transactional
	public void addMenu(Menu menu) {
		menu.setCreateTime(new Date());
		if (menu.getParentId() == null)
			menu.setParentId(0L);
		if (Menu.TYPE_BUTTON.equals(menu.getType())) {
			menu.setUrl(null);
			menu.setIcon(null);
		}
		this.save(menu);
	}

	@Override
	@Transactional
	public void deleteMeuns(String menuIds) {
		List<String> list = Arrays.asList(menuIds.split(","));
		this.batchDelete(list, "id", Menu.class);
		this.groundRoleMenuService.deleteRoleMenusByMenuId(menuIds);
		this.menuMapper.changeToTop(list);
	}

	@Override
	@Transactional
	public void updateMenu(Menu menu) {
		menu.setModifyTime(new Date());
		if (menu.getParentId() == null)
			menu.setParentId(0L);
		if (Menu.TYPE_BUTTON.equals(menu.getType())) {
			menu.setUrl(null);
			menu.setIcon(null);
		}
		this.updateNotNull(menu);
	}

	@Override
	public List<Menu> findMenuListByRoleId(Long id) {
		return menuMapper.findMenuListByRoleId(id);
	}

}
