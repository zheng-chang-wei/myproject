/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.authority.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hirain.phm.bd.ground.authority.domain.Dept;
import com.hirain.phm.bd.ground.authority.domain.User;
import com.hirain.phm.bd.ground.authority.service.ApprovalRoleUserService;
import com.hirain.phm.bd.ground.authority.service.DeptService;
import com.hirain.phm.bd.ground.authority.service.ProjectUserService;
import com.hirain.phm.bd.ground.authority.service.UserService;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Nov 4, 2019 3:59:21 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Nov 4, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Component
public class RBACGateWay {

	@Autowired
	private ApprovalRoleUserService roleService;

	@Autowired
	private ProjectUserService projectUserService;

	@Autowired
	private UserService userService;

	private DeptService deptService;

	public List<Integer> getRepairRolesByUserId(Long userId) {
		return roleService.getRepairRolesByUserId(userId);
	}

	public List<Long> getProjectsByUserID(Long userId) {
		return projectUserService.getProjectsByUserID(userId);
	}

	public List<User> getUsersByProjectID(Integer projectId) {
		return projectUserService.getUsersByProjectID(projectId);
	}

	public User findUserByIdWithRole(Long userId) {
		return userService.findById(userId);
	}

	public User findUserById(Long userId) {
		return userService.selectByKey(userId);
	}

	public Dept findDeptById(Long deptId) {
		return deptService.selectByKey(deptId);
	}
}
