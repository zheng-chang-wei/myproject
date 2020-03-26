/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.rbac.service.test;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.hirain.phm.synapsis.rbac.domain.Role;
import com.hirain.phm.synapsis.rbac.service.RoleService;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Mar 13, 2020 5:44:55 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Mar 13, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Service
@Profile("test")
@Primary
public class InMemoryRoleService implements RoleService {

	private List<Role> roles;

	/**
	 * @see com.hirain.phm.synapsis.rbac.service.RoleService#list()
	 */
	@Override
	public List<Role> list() {
		if (roles == null) {
			roles = new ArrayList<>();
			roles.add(create(1, "管理员", "admin"));
			roles.add(create(2, "普通用户", "common"));
		}
		return roles;
	}

	/**
	 * @param id
	 * @param cname
	 * @param rolename
	 * @return
	 */
	public Role create(int id, String cname, String rolename) {
		Role role = new Role();
		role.setId(id);
		role.setCname(cname);
		role.setRolename(rolename);
		return role;
	}

}
