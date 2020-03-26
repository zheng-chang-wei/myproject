/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.ResourceUtils;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hirain.phm.synapsis.BaseTest;
import com.hirain.phm.synapsis.TestUtils;
import com.hirain.phm.synapsis.response.ResultBean;
import com.hirain.phm.synapsis.runtime.controller.SettingController;
import com.hirain.phm.synapsis.runtime.param.ActivateResponse;
import com.hirain.phm.synapsis.runtime.param.ProtocolResponse;
import com.hirain.phm.synapsis.runtime.param.SettingResponse;
import com.hirain.phm.synapsis.runtime.param.UpdateResponse;
import com.hirain.phm.synapsis.setting.service.SettingService;
import com.hirain.phm.synapsis.setting.support.impl.SettingProperties;
import com.hirain.phm.synapsis.setting.support.param.SettingVO;
import com.hirain.phm.synapsis.util.JsonUtil;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec 13, 2019 3:29:02 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 13, 2019 zepei.tao@hirain.com 1.0 create file
 */
@ActiveProfiles({ "prod", "xml" })
public class SettingControllerTest extends BaseTest {

	@Autowired
	public WebApplicationContext context;

	@Autowired
	public SettingController settingController;

	public MockMvc mockMvc;

	@Autowired
	private SettingProperties properties;

	@Autowired
	private SettingService settingService;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	public void testUploadProtocol() throws Exception {
		clearTempFolder();
		File file = ResourceUtils.getFile("classpath:mvb.xlsx");
		byte[] bs = FileUtils.readFileToByteArray(file);
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.multipart("/setting/upload/protocol")
						.file(new MockMultipartFile("file", file.getAbsolutePath(), "multipart/form-data", bs)).param("type", "MVB"))

				.andExpect(MockMvcResultMatchers.status().isOk())

				.andDo(MockMvcResultHandlers.print())

				.andReturn();

		ResultBean<?> resultBean = TestUtils.getResultBean(result);
		ProtocolResponse response = JSONObject.parseObject(resultBean.getData().toString(), ProtocolResponse.class);
		File tempFile = new File(properties.getTemporaryDirectory(), response.getPath());
		assertTrue(tempFile.exists());
		assertNotNull(response.getVariables());
		assertNull(response.getErrors());
	}

	/**
	 * 
	 */
	private void clearTempFolder() {
		String root = properties.getTemporaryDirectory();
		clearFolder(root);
	}

	/**
	 * @param root
	 */
	private void clearFolder(String root) {
		File folder = new File(root);
		File[] files = folder.listFiles();
		for (File file : files) {
			FileUtils.deleteQuietly(file);
		}
	}

	@Test
	public void testUploadScript() throws Exception {
		clearTempFolder();
		File file = ResourceUtils.getFile("classpath:algorithm.py");
		byte[] bs = FileUtils.readFileToByteArray(file);
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.multipart("/setting/upload/script")
						.file(new MockMultipartFile("file", file.getAbsolutePath(), "multipart/form-data", bs)))

				.andExpect(MockMvcResultMatchers.status().isOk())

				.andDo(MockMvcResultHandlers.print())

				.andReturn();
		ResultBean<?> resultBean = TestUtils.getResultBean(result);
		File tempFile = new File(properties.getTemporaryDirectory(), resultBean.getData().toString());
		assertTrue(tempFile.exists());
	}

	@Test
	public void testSaveSetting() {
		try {
			File file = ResourceUtils.getFile("classpath:request.json");
			String jsonResult = JsonUtil.readJSONStringFromFile(file);
			SettingVO frontSetting = JSON.parseObject(jsonResult, SettingVO.class);
			ResultBean<UpdateResponse> resultBean = settingController.saveSetting(frontSetting);
			int settingId = resultBean.getData().getSettingId();
			Setting setting = settingService.selectById(settingId);
			assertNotNull(setting);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testActivateSetting() {
		clearFolder(properties.getRootDirectory());
		try {
			ResultBean<ActivateResponse> resultBean = settingController.activateSetting(405);
			int result = resultBean.getData().getResult();
			assertEquals(0, result);

			File folder = new File(properties.getRootDirectory());
			File[] files = folder.listFiles();
			assertTrue(files != null && files.length > 0);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testSelect() {
		ResultBean<SettingResponse> resultBean = settingController.select(304);
		System.out.println(resultBean.getData().getSetting());
		System.out.println(resultBean.getData().getVariableGroups());
	}
}
