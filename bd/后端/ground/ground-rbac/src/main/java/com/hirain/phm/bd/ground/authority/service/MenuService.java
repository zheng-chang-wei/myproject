package com.hirain.phm.bd.ground.authority.service;

import java.util.List;

import org.springframework.cache.annotation.CacheConfig;

import com.hirain.phm.bd.ground.authority.domain.Menu;
import com.hirain.phm.bd.ground.authority.domain.User;
import com.hirain.phm.bd.ground.common.service.IService;

@CacheConfig(cacheNames = "MenuService")
public interface MenuService extends IService<Menu> {

	List<Menu> findUserPermissions(String userName);

	List<Menu> getMenuTree(User user);

	void addMenu(Menu menu);

	void updateMenu(Menu menu);

	void deleteMeuns(String menuIds);

	List<Menu> getAllMenu(User user);

	List<Menu> findMenuListByRoleId(Long id);
}
