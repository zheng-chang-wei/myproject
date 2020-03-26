/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.rbac.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirain.phm.synapsis.rbac.dao.UserMapper;
import com.hirain.phm.synapsis.rbac.domain.User;
import com.hirain.phm.synapsis.rbac.service.UserService;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Mar 13, 2020 6:11:57 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Mar 13, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper mapper;

	/**
	 * @see com.hirain.phm.synapsis.rbac.service.UserService#selectByName(java.lang.String)
	 */
	@Override
	public User selectByName(String username) {
		return mapper.selectByName(username);
	}

	/**
	 * @see com.hirain.phm.synapsis.rbac.service.UserService#list()
	 */
	@Override
	public List<User> list() {
		return mapper.listAll();
	}

	/**
	 * @see com.hirain.phm.synapsis.rbac.service.UserService#getUser(int)
	 */
	@Override
	public User getUser(int id) {
		return mapper.selectByPrimaryKey(id);
	}

	/**
	 * @see com.hirain.phm.synapsis.rbac.service.UserService#addOrUpdate(com.hirain.phm.synapsis.rbac.domain.User)
	 */
	@Override
	public String addOrUpdate(User user) {
		if (user.getId() != null) {
			mapper.updateByKey(user);
			mapper.updateUserRole(user.getId(), user.getRole().getId());
			return "更新成功";
		} else {
			User exist = mapper.selectByName(user.getUsername());
			if (exist != null) {
				return "用户重名";
			}
			mapper.insertGenerateKey(user);
			mapper.insertUserRole(user.getId(), user.getRole().getId());
			return "插入成功";
		}
	}

	/**
	 * @see com.hirain.phm.synapsis.rbac.service.UserService#deleteUser(int)
	 */
	@Override
	public String deleteUser(int id) {
		mapper.deleteByPrimaryKey(id);
		mapper.deleteUserRole(id);
		return "删除成功";
	}

	/**
	 * @see com.hirain.phm.synapsis.rbac.service.UserService#selectUserAll(java.lang.String)
	 */
	@Override
	public User selectUserAll(String username) {
		return mapper.selectUserInfo(username);
	}

}
