/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.repair.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;

import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;

import com.alibaba.fastjson.JSONObject;
import com.hirain.phm.bd.ground.common.model.PageResultBean;
import com.hirain.phm.bd.ground.maintenance.param.WorkSheetRecord;
import com.hirain.phm.bd.ground.repair.BaseTest;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Nov 1, 2019 5:38:02 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Nov 1, 2019 jianwen.xin@hirain.com 1.0 create file
 */
public class TestQuery extends BaseTest {

	@Test
	public void testPage() throws Exception {
		PageResultBean<?> bean40 = listSheets("1", "40");
		assertEquals(40, bean40.getData().size());
		assertEquals(167, bean40.getTotal());
		WorkSheetRecord record1 = parse(bean40.getData().get(0));
		WorkSheetRecord record2 = parse(bean40.getData().get(20));

		PageResultBean<?> bean20_1 = listSheets("1", "20");
		assertEquals(20, bean20_1.getData().size());
		WorkSheetRecord record3 = parse(bean20_1.getData().get(0));
		assertEquals(record1.getSheet().getId(), record3.getSheet().getId());

		PageResultBean<?> bean20_2 = listSheets("2", "20");
		assertEquals(20, bean20_2.getData().size());
		WorkSheetRecord record4 = parse(bean20_2.getData().get(0));
		assertEquals(record2.getSheet().getId(), record4.getSheet().getId());

	}

	@Test
	public void testOrder() throws UnsupportedEncodingException, Exception {
		PageResultBean<?> sheets = listSheets("1", "20");

		WorkSheetRecord record1 = parse(sheets.getData().get(0));
		WorkSheetRecord record2 = parse(sheets.getData().get(1));

		assertTrue(record1.getSheet().getFaultTime().compareTo(record2.getSheet().getFaultTime()) > 0);

	}

	private WorkSheetRecord parse(Object object) {
		return JSONObject.parseObject(object.toString(), WorkSheetRecord.class);
	}

	private PageResultBean<?> listSheets(String pageNum, String pageSize) throws Exception, UnsupportedEncodingException {
		MvcResult mvcResult = mockMvc.perform(get("/repair/worksheets").param("pageSize", pageSize).param("pageNum", pageNum))

				.andExpect(status().isOk()).andDo(print()).andReturn();
		String content = mvcResult.getResponse().getContentAsString();
		PageResultBean<?> bean = JSONObject.parseObject(content, PageResultBean.class);
		return bean;
	}

	@Test
	public void testMobile() throws Exception {
		MvcResult mvcResult = mockMvc.perform(get("/mobile/repair/worksheets").param("pageSize", "5").param("pageNum", "2"))

				.andExpect(status().isOk()).andDo(print()).andReturn();
		String content = mvcResult.getResponse().getContentAsString();
		PageResultBean<?> bean = JSONObject.parseObject(content, PageResultBean.class);
		assertEquals(2, bean.getData().size());
	}
}
