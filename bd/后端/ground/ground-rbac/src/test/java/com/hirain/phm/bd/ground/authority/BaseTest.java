package com.hirain.phm.bd.ground.authority;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Base64;
import java.util.Base64.Encoder;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RBACTestApplication.class)
public class BaseTest {

	@Autowired
	public WebApplicationContext context;

	public MockMvc mockMvc;

	public MockHttpSession session;

	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
		SecurityUtils.setSecurityManager(context.getBean(SecurityManager.class));
		session = (MockHttpSession) login();
	}

	public HttpSession login() throws Exception {
		Encoder encoder = Base64.getEncoder();
		String pw = encoder.encodeToString("123456".getBytes());
		MvcResult result = mockMvc.perform(post("/login").param("accountname", "tester").param("password", pw))

				.andExpect(status().isOk())

				.andDo(print())

				.andReturn();
		return result.getRequest().getSession();
	}
}
