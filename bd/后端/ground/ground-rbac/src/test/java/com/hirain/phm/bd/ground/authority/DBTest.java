package com.hirain.phm.bd.ground.authority;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hirain.phm.bd.ground.authority.dao.GroundRoleMenuMapper;
import com.hirain.phm.bd.ground.authority.dao.ProjectUserMapper;
import com.hirain.phm.bd.ground.authority.domain.GroundRole;
import com.hirain.phm.bd.ground.authority.domain.Menu;
import com.hirain.phm.bd.ground.authority.domain.User;
import com.hirain.phm.bd.ground.authority.service.GroundRoleService;

@SpringBootTest(classes = RBACTestApplication.class)
@RunWith(SpringRunner.class)
public class DBTest {

	@Autowired
	private ProjectUserMapper mapper;

	@Autowired
	private GroundRoleMenuMapper menuMapper;

	@Autowired
	private GroundRoleService roleService;

	@Test
	public void test() {
		List<User> users = mapper.getUsersOfProject("深圳地铁7号线");
		assertFalse(users.isEmpty());
		User user = users.get(0);
		assertEquals("admin", user.getUserName());
	}

	@Test
	public void testMenu() {
		List<Menu> menus = menuMapper.findMenusByRoleId(1l);
		menus.forEach(System.out::println);
	}

	@Test
	public void testRoleWithMenu() {
		List<GroundRole> roles = roleService.findRoleWithMenuByUserId(1l);
		System.out.println(roles.get(0).getRoleName());
		roles.get(0).getMenus().forEach(System.out::println);
	}
}
