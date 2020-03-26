/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.rbac.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirain.phm.synapsis.rbac.domain.Role;
import com.hirain.phm.synapsis.rbac.service.RoleService;
import com.hirain.phm.synapsis.response.ResultBean;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Mar 13, 2020 5:43:16 PM
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
public class RoleController {

	@Autowired
	private RoleService service;

	@GetMapping("/roles")
	public ResultBean<List<Role>> list() {
		return new ResultBean<>(service.list());
	}
}
