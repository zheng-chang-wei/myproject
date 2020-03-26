/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.rbac.service.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.hirain.phm.synapsis.rbac.domain.Permission;
import com.hirain.phm.synapsis.rbac.domain.Role;
import com.hirain.phm.synapsis.rbac.domain.User;
import com.hirain.phm.synapsis.rbac.service.UserService;

import lombok.extern.slf4j.Slf4j;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Mar 13, 2020 1:27:18 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Mar 13, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Service
@Profile("test")
@Primary
@Slf4j
public class InMemoryUserService implements UserService {

	private Map<Integer, User> users;

	private static int count = 1;

	@PostConstruct
	public void init() {
		log.info(InMemoryUserService.class.getName() + " started");
	}

	/**
	 * @see com.hirain.phm.synapsis.rbac.service.UserService#selectByName(java.lang.String)
	 */
	@Override
	public User selectByName(String username) {
		Map<Integer, User> map = getUsers();
		for (User user : map.values()) {
			if (user.getUsername().equals(username)) {
				return user;
			}
		}
		return null;
	}

	/**
	 * @see com.hirain.phm.synapsis.rbac.service.UserService#selectUserAll(java.lang.String)
	 */
	@Override
	public User selectUserAll(String username) {
		return selectByName(username);
	}

	/**
	 * 
	 */
	private Map<Integer, User> getUsers() {
		if (users == null) {
			users = new HashMap<>();
			User user = new User();
			user.setId(count++);
			user.setUsername("admin");
			user.setPassword("123456");
			Role role = new Role();
			role.setId(1);
			role.setRolename("admin");
			role.setCname("管理员");
			user.setRole(role);
			Permission permission = new Permission();
			permission.setId(1);
			permission.setPermission("all");
			role.setPermissions(Arrays.asList(permission));
			users.put(user.getId(), user);
		}
		return users;
	}

	/**
	 * @see com.hirain.phm.synapsis.rbac.service.UserService#list()
	 */
	@Override
	public List<User> list() {
		Map<Integer, User> map = getUsers();
		return new ArrayList<>(map.values());
	}

	/**
	 * @see com.hirain.phm.synapsis.rbac.service.UserService#getUser(int)
	 */
	@Override
	public User getUser(int id) {
		Map<Integer, User> map = getUsers();
		return map.get(id);
	}

	/**
	 * @see com.hirain.phm.synapsis.rbac.service.UserService#addOrUpdate(com.hirain.phm.synapsis.rbac.domain.User)
	 */
	@Override
	public String addOrUpdate(User user) {
		Map<Integer, User> map = getUsers();
		if (user.getId() != null) {
			map.put(user.getId(), user);
			return "更新成功";
		} else {
			user.setId(count++);
			map.put(user.getId(), user);
			return "插入成功";
		}
	}

	/**
	 * @see com.hirain.phm.synapsis.rbac.service.UserService#deleteUser(int)
	 */
	@Override
	public String deleteUser(int id) {
		Map<Integer, User> map = getUsers();
		map.remove(id);
		return "删除成功";
	}

}
