/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.rbac.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirain.phm.synapsis.rbac.domain.User;
import com.hirain.phm.synapsis.rbac.service.UserService;
import com.hirain.phm.synapsis.response.ResultBean;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Mar 13, 2020 1:51:05 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Mar 13, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@RestController
@RequestMapping("/rbac")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/users")
	public ResultBean<List<User>> list() {
		return new ResultBean<>(userService.list());
	}

	@GetMapping("/user")
	public ResultBean<User> getUser(int id) {
		return new ResultBean<>(userService.getUser(id));
	}

	@PostMapping("/user")
	public ResultBean<String> insertOrUpdate(@RequestBody User user) {
		return new ResultBean<>(userService.addOrUpdate(user));
	}

	@PostMapping("/user/delete")
	public ResultBean<String> delete(int id) {
		return new ResultBean<>(userService.deleteUser(id));
	}

}
