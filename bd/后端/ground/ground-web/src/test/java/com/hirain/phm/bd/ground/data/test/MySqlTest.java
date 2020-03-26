/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.data.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.hirain.phm.bd.ground.GroundWebApplication;
import com.hirain.phm.bd.ground.authority.dao.UserMapper;
import com.hirain.phm.bd.ground.authority.domain.User;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Mar 11, 2020 4:10:09 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Mar 11, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@SpringBootTest(classes = GroundWebApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class MySqlTest {

	@Autowired
	private UserMapper mapper;

	@Test
	public void test() {
		List<User> users = mapper.selectAll();
		users.forEach(System.out::println);

		User user = mapper.findUserWithRole(1l);
		System.err.println(user);
	}
}
