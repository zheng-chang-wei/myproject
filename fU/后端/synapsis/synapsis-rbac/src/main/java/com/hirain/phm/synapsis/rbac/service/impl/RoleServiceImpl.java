/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.rbac.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirain.phm.synapsis.rbac.dao.RoleMapper;
import com.hirain.phm.synapsis.rbac.domain.Role;
import com.hirain.phm.synapsis.rbac.service.RoleService;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Mar 13, 2020 6:28:24 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Mar 13, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleMapper mapper;

	/**
	 * @see com.hirain.phm.synapsis.rbac.service.RoleService#list()
	 */
	@Override
	public List<Role> list() {
		return mapper.selectAll();
	}

}
