package com.hirain.qsy.shaft.dao;

import java.util.List;

import com.hirain.qsy.shaft.common.config.MyMapper;
import com.hirain.qsy.shaft.common.model.UserRequest;
import com.hirain.qsy.shaft.model.User;

public interface UserMapper extends MyMapper<User> {

	List<User> findUserWithRole(UserRequest user);

	List<User> findUserWithRoleByParentId(UserRequest user);

}