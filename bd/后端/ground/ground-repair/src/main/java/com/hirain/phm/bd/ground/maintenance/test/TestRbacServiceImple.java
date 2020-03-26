/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.maintenance.test;

import java.util.Arrays;
import java.util.List;

import com.hirain.phm.bd.ground.authority.domain.User;
import com.hirain.phm.bd.ground.maintenance.service.RbacService;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Feb 28, 2020 5:02:54 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Feb 28, 2020 jianwen.xin@hirain.com 1.0 create file
 */
//@Profile("test")
//@Service
public class TestRbacServiceImple implements RbacService {

	/**
	 * @see com.hirain.phm.bd.ground.maintenance.service.RbacService#getCurrentUser()
	 */
	@Override
	public User getCurrentUser() {
		User user = new User();
		user.setUserId(1l);
		user.setName("admin");
		return user;
	}

	/**
	 * @see com.hirain.phm.bd.ground.maintenance.service.RbacService#getRoles(com.hirain.phm.bd.ground.authority.domain.User)
	 */
	@Override
	public List<Integer> getRoles(User user) {
		return null;
	}

	/**
	 * @see com.hirain.phm.bd.ground.maintenance.service.RbacService#getUserById(long)
	 */
	@Override
	public User getUserById(long userId) {
		return null;
	}

	/**
	 * @see com.hirain.phm.bd.ground.maintenance.service.RbacService#getUserWithRole(long)
	 */
	@Override
	public User getUserWithRole(long userId) {
		return null;
	}

	/**
	 * @see com.hirain.phm.bd.ground.maintenance.service.RbacService#getRepairRoles(com.hirain.phm.bd.ground.authority.domain.User)
	 */
	@Override
	public List<Integer> getRepairRoles(User user) {
		return Arrays.asList(1);
	}

	/**
	 * @see com.hirain.phm.bd.ground.maintenance.service.RbacService#getProjects(com.hirain.phm.bd.ground.authority.domain.User)
	 */
	@Override
	public List<Long> getProjects(User user) {
		return Arrays.asList(2l);
	}

}
