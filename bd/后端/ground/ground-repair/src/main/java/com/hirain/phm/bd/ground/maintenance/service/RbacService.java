/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.maintenance.service;

import java.util.List;

import com.hirain.phm.bd.ground.authority.domain.User;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Feb 28, 2020 10:14:47 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Feb 28, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public interface RbacService {

	User getCurrentUser();

	List<Integer> getRoles(User user);

	User getUserById(long userId);

	/**
	 * @param userId
	 * @return
	 */
	User getUserWithRole(long userId);

	List<Integer> getRepairRoles(User user);

	/**
	 * @param user
	 */
	List<Long> getProjects(User user);
}
