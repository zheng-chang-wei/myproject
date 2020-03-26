package com.hirain.phm.bd.flow;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.google.gson.Gson;
import com.hirain.phm.bd.flow.domain.ResultBean;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FlowAppApplication.class)
public class FlowAppApplicationTests {

	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;

	private Gson gson;

	@Before
	public void contextLoads() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
		gson = new Gson();
	}

	@Test
	public void test() throws Exception {
		MvcResult startResult = process("/start");
		String processId = handle(startResult);

		Map<String, Object> input = new HashMap<>();
		input.put("processId", processId);
		input.put("result", true);
		commit(input, "售后审核");
		commit(input, "质量调查");
		commit(input, "质量审核");
		commit(input, "问题解决");
		commit(input, "跟踪");
		commit(input, "关闭");
	}

	@Test
	public void testTerminate() throws Exception {
		MvcResult startResult = process("/start");
		String processId = handle(startResult);
		Map<String, Object> input = new HashMap<>();
		input.put("processId", processId);
		input.put("result", true);
		commit(input, "售后审核");
		terminate(input);

	}

	/**
	 * @param input
	 * @param expected
	 * @throws Exception
	 * @throws UnsupportedEncodingException
	 */
	private void commit(Map<String, Object> input, String expected) throws Exception, UnsupportedEncodingException {
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.post("/commit").param("processId", input.get("processId").toString()).param("result",
						input.get("result").toString()))

				.andExpect(MockMvcResultMatchers.status().isOk())

				.andDo(MockMvcResultHandlers.print())

				.andReturn();
		String content = result.getResponse().getContentAsString();
		ResultBean resultBean = gson.fromJson(content, ResultBean.class);
		assertEquals(expected, resultBean.getNext());
	}

	private void terminate(Map<String, Object> input) throws Exception {
		String url = "/terminate";
		ResultBean resultBean = post(input, url);
		assertEquals("关闭", resultBean.getNext());
	}

	/**
	 * @param input
	 * @param url
	 * @return
	 * @throws Exception
	 * @throws UnsupportedEncodingException
	 */
	private ResultBean post(Map<String, Object> input, String url) throws Exception, UnsupportedEncodingException {
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(url).param("processId", input.get("processId").toString()))

				.andExpect(MockMvcResultMatchers.status().isOk())

				.andDo(MockMvcResultHandlers.print())

				.andReturn();
		String content = result.getResponse().getContentAsString();
		ResultBean resultBean = gson.fromJson(content, ResultBean.class);
		return resultBean;
	}

	/**
	 * @param startResult
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private String handle(MvcResult startResult) throws UnsupportedEncodingException {
		String content = startResult.getResponse().getContentAsString();
		ResultBean result = gson.fromJson(content, ResultBean.class);
		String processId = result.getProcessId();
		System.err.println(processId);
		return processId;
	}

	/**
	 * @param url
	 * @return
	 * @throws Exception
	 */
	private MvcResult process(String url) throws Exception {
		MvcResult startResult = mockMvc.perform(MockMvcRequestBuilders.post(url))

				.andExpect(MockMvcResultMatchers.status().isOk())

				.andDo(MockMvcResultHandlers.print())

				.andReturn();
		return startResult;
	}
}
