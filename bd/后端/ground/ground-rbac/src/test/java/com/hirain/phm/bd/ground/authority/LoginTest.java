package com.hirain.phm.bd.ground.authority;

import java.util.Base64;
import java.util.Base64.Encoder;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RBACTestApplication.class)
public class LoginTest {

	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
		SecurityUtils.setSecurityManager(context.getBean(SecurityManager.class));
	}

	@Test
	public void login() throws Exception {
		Encoder encoder = Base64.getEncoder();
		String pw = encoder.encodeToString("123456".getBytes());
		mockMvc.perform(MockMvcRequestBuilders.post("/login").param("accountname", "tester").param("password", pw))

				.andExpect(MockMvcResultMatchers.status().isOk())

				.andDo(MockMvcResultHandlers.print());
	}

}
