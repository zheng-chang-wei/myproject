package com.hirain.qsy.shaft.service;

import java.util.List;

import com.hirain.qsy.shaft.model.UserRole;

public interface UserRoleService extends IService<UserRole> {

	void deleteUserRolesByRoleId(String roleIds);

	void deleteUserRolesByUserId(List<String> userIds);
}
