/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.rbac.service;

import java.util.List;

import com.hirain.phm.synapsis.rbac.domain.User;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Mar 13, 2020 11:24:40 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Mar 13, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public interface UserService {

	/**
	 * @param username
	 */
	User selectByName(String username);

	/**
	 * @return
	 */
	List<User> list();

	/**
	 * @param id
	 * @return
	 */
	User getUser(int id);

	/**
	 * @param user
	 * @return
	 */
	String addOrUpdate(User user);

	/**
	 * @param id
	 * @return
	 */
	String deleteUser(int id);

	/**
	 * @param username
	 */
	User selectUserAll(String username);

}
