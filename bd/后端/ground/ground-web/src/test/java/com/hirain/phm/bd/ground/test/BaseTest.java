package com.hirain.phm.bd.ground.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Base64.Encoder;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSONObject;
import com.hirain.phm.bd.ground.GroundWebApplication;
import com.hirain.phm.bd.ground.common.model.ResponseBo;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GroundWebApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@EmbeddedKafka(count = 1, ports = { 9092 })
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
		String pw = encoder.encodeToString("1".getBytes());
		MvcResult result = mockMvc.perform(post("/login").param("accountname", "admin").param("password", pw))

				.andExpect(status().isOk())

				.andDo(print())

				.andReturn();
		return result.getRequest().getSession();
	}

	protected String getContent(MvcResult mvcResult) throws UnsupportedEncodingException {
		return mvcResult.getResponse().getContentAsString();
	}

	protected ResponseBo parse(String content) {
		return JSONObject.parseObject(content, ResponseBo.class);
	}

	protected MvcResult httpPost(String url, Object object) throws Exception {
		return mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON_VALUE).content(JSONObject.toJSONString(object)).session(session))

				.andExpect(status().isOk()).andDo(print()).andReturn();
	}

	protected ResponseBo getResponse(MvcResult mvcResult) throws UnsupportedEncodingException {
		String content = getContent(mvcResult);
		ResponseBo bo = parse(content);
		return bo;
	}
}
