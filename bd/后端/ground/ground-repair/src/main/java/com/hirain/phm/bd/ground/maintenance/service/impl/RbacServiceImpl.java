/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.maintenance.service.impl;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirain.phm.bd.ground.authority.controller.RBACGateWay;
import com.hirain.phm.bd.ground.authority.domain.User;
import com.hirain.phm.bd.ground.maintenance.service.RbacService;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Feb 28, 2020 10:29:51 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Feb 28, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Service
public class RbacServiceImpl implements RbacService {

	@Autowired
	private RBACGateWay rbacGW;

	/**
	 * @see com.hirain.phm.bd.ground.maintenance.service.RbacService#getCurrentUser()
	 */
	@Override
	public User getCurrentUser() {
		return (User) SecurityUtils.getSubject().getPrincipal();
	}

	/**
	 * @see com.hirain.phm.bd.ground.maintenance.service.RbacService#getRoles(com.hirain.phm.bd.ground.authority.domain.User)
	 */
	@Override
	public List<Integer> getRoles(User user) {
		return rbacGW.getRepairRolesByUserId(user.getUserId());
	}

	/**
	 * @see com.hirain.phm.bd.ground.maintenance.service.RbacService#getUserById(long)
	 */
	@Override
	public User getUserById(long userId) {
		return rbacGW.findUserById(userId);
	}

	@Override
	public User getUserWithRole(long userId) {
		return rbacGW.findUserByIdWithRole(userId);
	}

	/**
	 * @see com.hirain.phm.bd.ground.maintenance.service.RbacService#getRepairRoles(com.hirain.phm.bd.ground.authority.domain.User)
	 */
	@Override
	public List<Integer> getRepairRoles(User user) {
		return rbacGW.getRepairRolesByUserId(user.getUserId());
	}

	/**
	 * @see com.hirain.phm.bd.ground.maintenance.service.RbacService#getProjects(com.hirain.phm.bd.ground.authority.domain.User)
	 */
	@Override
	public List<Long> getProjects(User user) {
		return rbacGW.getProjectsByUserID(user.getId());
	}

}
