/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.authority;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hirain.phm.bd.ground.authority.controller.RBACGateWay;
import com.hirain.phm.bd.ground.authority.domain.GroundRole;
import com.hirain.phm.bd.ground.authority.domain.User;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Nov 12, 2019 11:48:25 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Nov 12, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@SpringBootTest(classes = RBACTestApplication.class)
@RunWith(SpringRunner.class)
public class GateWayTest {

	@Autowired
	private RBACGateWay gw;

	@Test
	public void getRepairRolesByUserId() {
		List<Integer> roles = gw.getRepairRolesByUserId(1l);
		assertFalse(roles.isEmpty());
		assertEquals(1, roles.get(0).intValue());
	}

	@Test
	public void getProjectsByUserId() {
		List<Long> projects = gw.getProjectsByUserID(1l);
		assertFalse(projects.isEmpty());
		assertEquals(1, projects.get(0).intValue());
	}

	@Test
	public void getUsersByProjectId() {
		List<User> users = gw.getUsersByProjectID(1);
		assertFalse(users.isEmpty());
		assertEquals("admin", users.get(0).getUserName());
	}

	@Test
	public void findUserByIdWithRole() {
		User user = gw.findUserByIdWithRole(1l);
		assertNotNull(user);
		assertEquals("admin", user.getUserName());
		List<GroundRole> roles = user.getGroundRoles();
		assertNotNull(roles);
		assertFalse(roles.isEmpty());
		GroundRole role = roles.get(0);
		assertEquals("管理员", role.getRoleName());
	}

}
