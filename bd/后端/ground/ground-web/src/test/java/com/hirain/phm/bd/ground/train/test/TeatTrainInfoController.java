/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.train.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;

import com.alibaba.fastjson.JSONObject;
import com.hirain.phm.bd.ground.common.model.ResponseBo;
import com.hirain.phm.bd.ground.test.BaseTest;
import com.hirain.phm.bd.ground.train.domain.Project;
import com.hirain.phm.bd.ground.train.domain.Train;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年11月8日 下午6:17:22
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年11月8日 jianwen.xin@hirain.com 1.0 create file
 */
public class TeatTrainInfoController extends BaseTest {

	@Test
	public void testSelectAllProjects() throws Exception {
		MvcResult mvcResult = mockMvc.perform(get("/train/projects").session(session)).andExpect(status().isOk())

				.andDo(print()).andReturn();
		ResponseBo bo = getResponse(mvcResult);
		List<Project> list = JSONObject.parseArray(bo.get("msg").toString(), Project.class);
		assertNotNull(list);
		assertFalse(list.isEmpty());
	}

	@Test
	public void selectTrainsByProject() throws Exception {
		MvcResult mvcResult = mockMvc.perform(get("/train/project/trains").param("project", "深圳地铁7号线").session(session))

				.andExpect(status().isOk()).andDo(print()).andReturn();
		ResponseBo bo = getResponse(mvcResult);
		List<Train> trains = JSONObject.parseArray(bo.get("msg").toString(), Train.class);
		assertFalse(trains.isEmpty());
	}
}
