/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.web.servlet.MvcResult;

import com.alibaba.fastjson.JSONObject;
import com.hirain.phm.bd.ground.authority.domain.Dept;
import com.hirain.phm.bd.ground.authority.service.DeptService;
import com.hirain.phm.bd.ground.common.model.ResponseBo;
import com.hirain.phm.bd.ground.common.model.Tree;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Nov 4, 2019 6:47:54 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Nov 4, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@EmbeddedKafka(count = 1, ports = { 9092 })
public class TestDept extends BaseTest {

	@Autowired
	private DeptService deptService;

	@Test
	public void testDeptTree() throws Exception {
		MvcResult mvcResult = mockMvc.perform(get("/dept/tree").session(session)).andExpect(status().isOk())

				.andDo(print()).andReturn();
		ResponseBo responseBo = getResponse(mvcResult);
		@SuppressWarnings("rawtypes")
		List<Tree> trees = JSONObject.parseArray(responseBo.get("msg").toString(), Tree.class);
		assertEquals(4, trees.size());
		trees.forEach(System.out::println);
	}

	@Test
	public void testGetDept() throws Exception {
		MvcResult mvcResult = mockMvc.perform(get("/dept/getDept").param("deptId", "5").session(session)).andExpect(status().isOk())

				.andDo(print()).andReturn();
		ResponseBo responseBo = getResponse(mvcResult);
		Dept dept = JSONObject.parseObject(responseBo.get("msg").toString(), Dept.class);
		assertNotNull(dept);
		assertEquals("人事部", dept.getDeptName());
	}

	@Test
	public void testList() throws Exception {
		MvcResult mvcResult = mockMvc.perform(get("/dept/list").session(session)).andExpect(status().isOk())

				.andDo(print()).andReturn();
		ResponseBo responseBo = getResponse(mvcResult);
		List<Dept> depts = JSONObject.parseArray(responseBo.get("msg").toString(), Dept.class);
		List<Dept> list = deptService.selectAll();
		assertEquals(list.size(), depts.size());
	}

	@Test
	public void testAdd() throws Exception {
		Dept dept = new Dept();
		dept.setDeptName("业务部");
		MvcResult mvcResult = mockMvc.perform(post("/dept/add").param("deptName", dept.getDeptName()).session(session))

				.andExpect(status().isOk()).andDo(print()).andReturn();
		ResponseBo bo = getResponse(mvcResult);
		assertEquals(0, bo.get("code"));
		Dept result = deptService.findByName(dept.getDeptName());
		assertNotNull(result);
	}

	@Test
	public void testAddSameName() throws Exception {
		MvcResult mvcResult = mockMvc.perform(post("/dept/add").param("deptName", "测试部").session(session)).andExpect(status().isOk())

				.andDo(print()).andReturn();
		ResponseBo bo = getResponse(mvcResult);
		assertEquals(500, bo.get("code"));
		assertEquals("部门名称重复", bo.get("msg"));
	}

	@Test
	public void testDeleteDepts() throws Exception {
		MvcResult mvcResult = mockMvc.perform(post("/dept/delete").param("ids", "2,4").session(session)).andExpect(status().isOk()).andDo(print())
				.andReturn();
		ResponseBo bo = getResponse(mvcResult);
		assertEquals(0, bo.get("code"));

		Dept dept = deptService.findById(2l);
		assertNull(dept);

		Dept dept2 = deptService.findById(4l);
		assertNull(dept2);
	}

	@Test
	public void testDeleteParentDept() throws Exception {
		MvcResult mvcResult = mockMvc.perform(post("/dept/delete").param("ids", "1").session(session)).andExpect(status().isOk())

				.andDo(print()).andReturn();
		ResponseBo bo = getResponse(mvcResult);
		assertEquals(0, bo.get("code"));

		Dept dept = deptService.findById(1l);
		assertNull(dept);

		Dept dept2 = deptService.findById(3l);
		assertEquals(0, dept2.getParentId().intValue());
	}

	@Test
	@Ignore
	public void testDeleteDeptWithUserHold() throws Exception {
		MvcResult mvcResult = mockMvc.perform(post("/dept/delete").param("ids", "3").session(session))

				.andExpect(status().isOk()).andDo(print()).andReturn();
		ResponseBo bo = getResponse(mvcResult);
		assertEquals(500, bo.get("code"));
	}

	@Test
	public void testUpdateDept() throws Exception {
		MvcResult mvcResult = mockMvc.perform(post("/dept/update").param("deptId", "1").param("deptName", "研发部").session(session))

				.andExpect(status().isOk()).andDo(print()).andReturn();
		ResponseBo bo = getResponse(mvcResult);
		assertEquals(0, bo.get("code"));

		Dept dept = deptService.findById(1l);
		assertEquals("研发部", dept.getDeptName());
	}

	@Test
	public void testUpdateDeptWithDuplicate() throws Exception {
		MvcResult mvcResult = mockMvc.perform(post("/dept/update").param("deptId", "1").param("deptName", "测试部")).andExpect(status().isOk())

				.andDo(print()).andReturn();
		ResponseBo bo = getResponse(mvcResult);
		assertEquals(500, bo.get("code"));
		assertEquals("部门名称重复", bo.get("msg"));
	}

	@Test
	public void testUpdateDeptWithSame() throws Exception {
		MvcResult mvcResult = mockMvc.perform(post("/dept/update").param("deptId", "1").param("deptName", "开发部").session(session))

				.andExpect(status().isOk()).andDo(print()).andReturn();
		ResponseBo bo = getResponse(mvcResult);
		assertEquals(0, bo.get("code"));
	}
}
