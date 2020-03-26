/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.rbac;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

import com.hirain.phm.synapsis.rbac.domain.Role;
import com.hirain.phm.synapsis.rbac.domain.User;
import com.hirain.phm.synapsis.rbac.service.UserService;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Mar 13, 2020 6:31:12 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Mar 13, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@ActiveProfiles("prod")
public class UserServiceTest extends BaseTest {

	@Autowired
	private UserService service;

	@Test
	public void testSelectByName() {
		User user = service.selectByName("admin");
		assertNotNull(user);
		assertEquals("admin", user.getUsername());
		assertEquals("admin", user.getPassword());
	}

	@Test
	public void testListAll() {
		List<User> all = service.list();
		assertEquals(1, all.size());
		assertNotNull(all.get(0).getRole());
		all.forEach(System.out::println);
	}

	@Test
	public void testSelectUserInfo() {
		User user = service.selectUserAll("admin");
		assertNotNull(user.getRole());
	}

	@Test
	public void testInsert() {
		User user = create();
		String res = service.addOrUpdate(user);
		assertEquals("插入成功", res);
		assertNotNull(user.getId());
		User userAll = service.selectUserAll(user.getUsername());
		assertEquals(user.getPassword(), userAll.getPassword());
		assertNotNull(userAll.getRole());

		service.deleteUser(userAll.getId());

		User user2 = service.selectByName(user.getUsername());
		assertNull(user2);
	}

	@Test
	public void testUpdate() {
		User user = create();
		service.addOrUpdate(user);
		user.setPassword("test");
		Role role = new Role();
		role.setId(1);
		user.setRole(role);
		String res = service.addOrUpdate(user);
		assertEquals("更新成功", res);
		User newUser = service.selectUserAll(user.getUsername());
		assertEquals(user.getPassword(), newUser.getPassword());
		assertEquals(user.getRole().getId(), newUser.getRole().getId());

		service.deleteUser(user.getId());
	}

	/**
	 * @return
	 */
	public User create() {
		User user = new User();
		user.setUsername("test");
		user.setPassword("123456");
		Role role = new Role();
		role.setId(2);
		role.setCname("普通用户");
		role.setRolename("common");
		user.setRole(role);
		return user;
	}

}
