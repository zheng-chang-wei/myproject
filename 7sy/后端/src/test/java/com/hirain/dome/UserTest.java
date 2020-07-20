package com.hirain.dome;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hirain.qsy.shaft.model.User;
import com.hirain.qsy.shaft.service.UserService;

public class UserTest extends BaseTest {

	@Autowired
	UserService userService;

	@Test
	public void insertUser() {
		for (int i = 4; i < 34; i++) {

			User user = new User();
			user.setName("testson" + i);
			user.setDeptName("a");
			user.setParentId(92L);
			user.setParentName("test");
			user.setPassword("MQ==");
			user.setUsername("testson" + i);
			user.setIdNum("11");
			userService.addUser(user, new Long[] { 3L });
		}
	}
}
