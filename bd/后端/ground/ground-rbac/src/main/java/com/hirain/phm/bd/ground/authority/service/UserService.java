package com.hirain.phm.bd.ground.authority.service;

import java.util.List;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import com.hirain.phm.bd.ground.authority.controller.UserRequest;
import com.hirain.phm.bd.ground.authority.domain.User;
import com.hirain.phm.bd.ground.common.service.IService;

@CacheConfig(cacheNames = "UserService")
public interface UserService extends IService<User> {

	User findById(Long userId);

	User findByName(String userName);

	@Cacheable(key = "#p0.toString()")
	List<User> findUserWithCondition(UserRequest user);

	@CacheEvict(allEntries = true)
	void addUser(User user, Long[] roleIds, Integer[] checkedApprovalRoleIds, String[] projectIds);

	/**
	 * 适用于 通过Excel批量导入用户
	 */
	@CacheEvict(allEntries = true)
	void addUser(User user);

	/**
	 * 适用于 通过Excel批量导入用户
	 */
	@CacheEvict(allEntries = true)
	void updateUser(User user);

	@CacheEvict(allEntries = true)
	void updateUser(User user, Long[] roleIds, Integer[] checkedApprovalRoleIds, String[] projectIds);

	@CacheEvict(allEntries = true)
	void deleteUsers(List<String> userIds);

	void updatePassword(String password);

	void insertUsers(List<User> users);
}
