/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.repair.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.alibaba.fastjson.JSONObject;
import com.hirain.phm.bd.ground.common.model.ResultBean;
import com.hirain.phm.bd.ground.maintenance.domain.WorkDetail;
import com.hirain.phm.bd.ground.maintenance.domain.WorkSheet;
import com.hirain.phm.bd.ground.repair.BaseTest;
import com.hirain.phm.bd.ground.repair.util.TestObjectCreater;
import com.hirain.phm.bd.ground.util.RedisUtil;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年5月30日 上午11:14:38
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年5月30日 jianwen.xin@hirain.com 1.0 create file
 */
@ActiveProfiles("test")
public class MaintenanceControllerTest extends BaseTest {

	@Autowired
	private RedisUtil redisUtil;

	@Test
	public void testCreate() throws Exception {
		WorkDetail detail = TestObjectCreater.create();
		process1(detail, "/repair/create", "售后审核");
	}

	public String uploadFile() throws Exception {
		String filepath = "E:\\项目分类\\TCN健康分析仪\\图标\\ooopic_1545703941.png";
		byte[] bs = FileUtils.readFileToByteArray(new File(filepath));
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.multipart("/repair/upload").file(new MockMultipartFile("file", filepath, "multipart/form-data", bs)))

				.andExpect(MockMvcResultMatchers.status().isOk())

				.andDo(MockMvcResultHandlers.print())

				.andReturn();
		String content = result.getResponse().getContentAsString();
		ResultBean<?> bean = JSONObject.parseObject(content, ResultBean.class);
		return bean.getData().toString();
	}

	@Test
	public void testUploadAndDelete() throws Exception {
		String filepath = "7d723a2c-f3ac-4726-9048-93d741c2a09e.png,52a55546-f918-4477-a349-80de87744304.png,805fa24e-9414-4151-b0db-e1bf9e9c258b.png";
		mockMvc.perform(MockMvcRequestBuilders.post("/repair/delete").content(filepath).contentType(MediaType.APPLICATION_JSON_VALUE))

				.andExpect(MockMvcResultMatchers.status().isOk())

				.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void testRecommit() throws UnsupportedEncodingException, Exception {
		Map<String, Object> input = new HashMap<>();
		input.put("sheetId", 12);
		input.put("object", TestObjectCreater.create());
		process1(input, "/repair/commit", "售后审核");
	}

	@Test
	public void testPassAfterSale() throws Exception {
		Map<String, Object> input = new HashMap<>();
		input.put("sheetId", 49);
		input.put("result", true);
		input.put("object", TestObjectCreater.createAfterSale());
		process(input, "/repair/aftersale", "质量调查");
	}

	@Test
	public void testRejectAfterSale() throws UnsupportedEncodingException, Exception {
		Map<String, Object> input = new HashMap<>();
		input.put("sheetId", 36);
		input.put("result", false);
		input.put("object", TestObjectCreater.createAfterSale());
		process(input, "/repair/aftersale", "创建工单");
	}

	@Test
	public void testCommitQualityInvest() throws UnsupportedEncodingException, Exception {
		Map<String, Object> input = new HashMap<>();
		int sheetId = 49;
		input.put("sheetId", sheetId);
		input.put("result", true);
		input.put("object", TestObjectCreater.createQualityInvest());
		process(input, "/repair/qualityinvest", "质量审核");
		assertEquals("1", redisUtil.get(sheetId + "-total"));
		assertEquals("0", redisUtil.get(sheetId + "-count"));
		Map<Object, Object> map = redisUtil.hget(sheetId + "-department");
		assertEquals(1, map.size());
		assertEquals("开发部", map.keySet().toArray(new String[] {})[0]);
	}

	@Test
	public void testCommitQualityInvestWithMultiDepart() throws UnsupportedEncodingException, Exception {
		Map<String, Object> input = new HashMap<>();
		int sheetId = 46;
		input.put("sheetId", sheetId);
		input.put("result", true);
		input.put("object", TestObjectCreater.createMultiDeparts());
		process(input, "/repair/qualityinvest", "质量审核");
		assertEquals("2", redisUtil.get(sheetId + "-total"));
		assertEquals("0", redisUtil.get(sheetId + "-count"));
		Map<Object, Object> map = redisUtil.hget(sheetId + "-department");
		assertTrue(map.containsKey("项目部"));
		assertTrue(map.containsKey("质保部"));
		assertEquals(2, map.size());
	}

	@Test
	public void testPassQuality() throws Exception {
		Map<String, Object> input = new HashMap<>();
		input.put("sheetId", 49);
		input.put("result", true);
		input.put("object", TestObjectCreater.createQuality());
		process(input, "/repair/quality", "问题解决");
	}

	@Test
	public void testRejectQuality() throws UnsupportedEncodingException, Exception {
		Map<String, Object> input = new HashMap<>();
		input.put("sheetId", 20);
		input.put("result", false);
		input.put("object", TestObjectCreater.createQuality());
		process(input, "/repair/quality", "质量调查");
	}

	@Test
	public void testResolveProblem() throws Exception {
		Map<String, Object> input = new HashMap<>();
		int sheetId = 49;
		input.put("sheetId", sheetId);
		input.put("result", true);
		input.put("object", TestObjectCreater.createResolve());
		process(input, "/repair/resolve", "跟踪");

		Object object = redisUtil.hmget(sheetId + "-department", "开发部");
		assertEquals("true", object);
	}

	@Test
	public void testResolveProblemWithMultiDepart() throws UnsupportedEncodingException, Exception {
		Map<String, Object> input = new HashMap<>();
		int sheetId = 31;
		input.put("sheetId", sheetId);
		input.put("result", true);
		input.put("object", TestObjectCreater.createResolve());
		process(input, "/repair/resolve", "问题解决");
		process(input, "/repair/resolve", "跟踪");
	}

	@Test
	public void testAppeal() throws UnsupportedEncodingException, Exception {
		Map<String, Object> input = new HashMap<>();
		int sheetId = 35;
		input.put("sheetId", sheetId);
		input.put("result", false);
		input.put("object", TestObjectCreater.createResolve());
		process(input, "/repair/appeal", "质量调查");
	}

	@Test
	public void testClose() throws UnsupportedEncodingException, Exception {
		Map<String, Object> input = new HashMap<>();
		int sheetId = 49;
		input.put("sheetId", sheetId);
		input.put("result", true);
		input.put("object", TestObjectCreater.createResolve());
		process(input, "/repair/close", "关闭");
		assertFalse(redisUtil.hasKey(sheetId + "-total"));
		assertFalse(redisUtil.hasKey(sheetId + "-count"));
		assertFalse(redisUtil.hasKey(sheetId + "-department"));
	}

	@Test
	public void testRecurrent() throws Exception {
		Map<String, Object> input = new HashMap<>();
		int sheetId = 35;
		input.put("sheetId", sheetId);
		input.put("result", false);
		input.put("object", TestObjectCreater.createResolve());
		process(input, "/repair/repetition", "问题解决");
		assertEquals("0", redisUtil.get(sheetId + "-count"));
		Map<Object, Object> map = redisUtil.hget(sheetId + "-department");
		for (Object key : map.keySet()) {
			assertEquals("false", map.get(key));
		}
	}

	private void process(Map<String, Object> input, String url, String expectState) throws Exception, UnsupportedEncodingException {
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(url)

				.contentType(MediaType.APPLICATION_JSON_VALUE).content(JSONObject.toJSONString(input)).session(session))

				.andExpect(MockMvcResultMatchers.status().isOk())

				.andDo(MockMvcResultHandlers.print())

				.andReturn();

		String content = result.getResponse().getContentAsString();
		System.err.println(content);
		ResultBean<?> resultBean = JSONObject.parseObject(content, ResultBean.class);
		WorkSheet sheet = JSONObject.parseObject(resultBean.getData().toString(), WorkSheet.class);
		assertEquals(expectState, sheet.getState());

	}

	private void process1(Object input, String url, String expectState) throws Exception, UnsupportedEncodingException {
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(url)

				.contentType(MediaType.APPLICATION_JSON_VALUE).content(JSONObject.toJSONString(input)).session(session))

				.andExpect(MockMvcResultMatchers.status().isOk())

				.andDo(MockMvcResultHandlers.print())

				.andReturn();

		String content = result.getResponse().getContentAsString();
		System.err.println(content);
		ResultBean<?> resultBean = JSONObject.parseObject(content, ResultBean.class);
		assertEquals("提交成功", resultBean.getData().toString());
	}

}
