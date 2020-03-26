package com.hirain.qsy.shaft.dao;

import java.util.List;

import com.hirain.qsy.shaft.common.config.MyMapper;
import com.hirain.qsy.shaft.model.Role;

public interface RoleMapper extends MyMapper<Role> {

	List<Role> findUserRole(String userName);

	List<Role> findRoleByUserId(Long userId);

}