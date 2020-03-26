package com.hirain.qsy.shaft.service;

import java.util.List;
import java.util.Map;

import com.hirain.qsy.shaft.common.model.UserRequest;
import com.hirain.qsy.shaft.model.User;

public interface UserService extends IService<User> {

	Map<String, Object> findById(Long userId);

	User findByName(String userName);

	List<User> findUserWithRole(UserRequest user);

	void addUser(User user, Long[] roles);

	void updateUser(User user, Long[] roles);

	void deleteUsers(List<String> userIds);

	List<User> findUserWithRoleByParentId(UserRequest user);

}
