/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.ResourceUtils;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSONObject;
import com.hirain.phm.synapsis.BaseTest;
import com.hirain.phm.synapsis.TestUtils;
import com.hirain.phm.synapsis.constant.BoardType;
import com.hirain.phm.synapsis.response.ResultBean;
import com.hirain.phm.synapsis.runtime.param.ActivateResponse;
import com.hirain.phm.synapsis.runtime.param.ProtocolResponse;
import com.hirain.phm.synapsis.runtime.param.SettingResponse;
import com.hirain.phm.synapsis.runtime.param.UpdateResponse;
import com.hirain.phm.synapsis.setting.support.param.ADGroupVO;
import com.hirain.phm.synapsis.setting.support.param.ADVariableVO;
import com.hirain.phm.synapsis.setting.support.param.AlgorithmSettingVO;
import com.hirain.phm.synapsis.setting.support.param.BoardSettingVO;
import com.hirain.phm.synapsis.setting.support.param.MVBGroupVO;
import com.hirain.phm.synapsis.setting.support.param.MVBVariableVO;
import com.hirain.phm.synapsis.setting.support.param.SettingVO;
import com.hirain.phm.synapsis.setting.support.param.StoreVariablesVO;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 7, 2020 11:21:14 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 7, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public class SettingSaveTest extends BaseTest {

	@Autowired
	public WebApplicationContext context;

	public MockMvc mockMvc;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	public void testEditAndSaveAndActivate() throws Exception {
		SettingVO front = new SettingVO();
		// 基本信息配置
		front.setName("测试配置");
		front.setLine("G02");
		front.setTrain("12");

		// 板卡配置
		BoardSettingVO board1 = getBoard("192.168.2.11", BoardType.MVB.name(), 1);
		// 上传数据流文件
		File mvbFile = ResourceUtils.getFile("classpath:mvb.xlsx");
		ProtocolResponse response = uploadProtocolFile(mvbFile);
		assertTrue(response.getErrors() == null || response.getErrors().isEmpty());
		board1.setFilename(response.getPath());
		board1.setFileOriginalName(mvbFile.getName());

		BoardSettingVO board2 = getBoard("192.168.2.12", BoardType.AD1.name(), 2);
		// AD变量配置
		ADVariableVO v1 = new ADVariableVO();
		v1.setChnId(1);
		v1.setSampleRate(2.2);
		v1.setName("ad变量1");
		board2.setVariables(Arrays.asList(v1));

		BoardSettingVO board3 = getBoard("192.168.2.13", BoardType.PHM_AGX.name(), 3);
		front.setBoardSettings(Arrays.asList(board1, board2, board3));

		// 算法配置
		AlgorithmSettingVO algorithm1 = new AlgorithmSettingVO();
		algorithm1.setCode(1);
		algorithm1.setSlotId(3);
		algorithm1.setEnable(true);
		algorithm1.setName("测试算法1");
		algorithm1.setSubsystemId(1);
		algorithm1.setType(1);
		// 上传脚本文件
		File script = ResourceUtils.getFile("classpath:algorithm.py");
		String path = uploadScript(script);
		algorithm1.setFilename(path);
		algorithm1.setFileOriginalName(script.getAbsolutePath());
		ADGroupVO group1 = new ADGroupVO();
		group1.setSlotId(2);
		group1.setVariables(Arrays.asList(copyADVariable(v1)));
		algorithm1.setAdGroups(Arrays.asList(group1));

		MVBGroupVO group2 = new MVBGroupVO();
		group2.setSlotId(1);
		String json = JSONObject.toJSONString(response.getVariables());
		System.out.println(json);
		List<MVBVariable> mvbVariables = JSONObject.parseArray(json, MVBVariable.class);
		MVBVariableVO v2 = getFrom(mvbVariables.get(0));
		group2.setVariables(Arrays.asList(v2));
		algorithm1.setMvbGroups(Arrays.asList(group2));
		front.setAlgorithmSettings(Arrays.asList(algorithm1));

		// 存储配置
		front.setRawSpace(20);
		front.setResultSpace(80);
		front.setRawStrategy(1);
		front.setResultStrategy(1);
		StoreVariablesVO storeVariables = new StoreVariablesVO();
		ADGroupVO group3 = new ADGroupVO();
		group3.setSlotId(2);
		group3.setVariables(Arrays.asList(copyADVariable(v1)));
		MVBGroupVO group4 = new MVBGroupVO();
		group4.setSlotId(1);
		group4.setVariables(Arrays.asList(getFrom(mvbVariables.get(0))));
		storeVariables.setAdGroups(Arrays.asList(group3));
		storeVariables.setMvbGroups(Arrays.asList(group4));
		front.setStoreVariables(storeVariables);

		// 时间配置
		front.setTimeOn(false);
		System.err.println(front);
		UpdateResponse updateResponse = update(front);
		System.out.println(updateResponse.getSettingId());
		assertEquals(0, updateResponse.getResult());
		SettingResponse settingResponse = select(updateResponse.getSettingId());
		assertNotNull(settingResponse);

		assertEquals(front.getName(), settingResponse.getSetting().getName());
		System.err.println(settingResponse.getSetting());

		int settingId = updateResponse.getSettingId();
		ActivateResponse activateResponse = reload(settingId);
		assertEquals(0, activateResponse.getResult());
	}

	/**
	 * @param v1
	 * @return
	 */
	private ADVariableVO copyADVariable(ADVariableVO v1) {
		ADVariableVO v3 = new ADVariableVO();
		v3.setChnId(v1.getChnId());
		v3.setName(v1.getName());
		v3.setSampleRate(v1.getSampleRate());
		return v3;
	}

	/**
	 * @param settingId
	 * @return
	 * @throws Exception
	 * @throws UnsupportedEncodingException
	 */
	public ActivateResponse reload(int settingId) throws Exception, UnsupportedEncodingException {
		MvcResult mvcResult = mockMvc.perform(post("/setting/activate").param("settingId", String.valueOf(settingId))).andExpect(status().isOk())

				.andDo(print()).andReturn();
		ResultBean<?> resultBean = TestUtils.getResultBean(mvcResult);
		ActivateResponse activateResponse = JSONObject.parseObject(resultBean.getData().toString(), ActivateResponse.class);
		return activateResponse;
	}

	/**
	 * @param settingId
	 * @return
	 * @throws Exception
	 * @throws UnsupportedEncodingException
	 */
	private SettingResponse select(int settingId) throws Exception, UnsupportedEncodingException {
		MvcResult mvcResult = mockMvc.perform(get("/setting/select").param("id", String.valueOf(settingId))).andExpect(status().isOk()).andDo(print())
				.andReturn();
		ResultBean<?> resultBean = TestUtils.getResultBean(mvcResult);
		SettingResponse settingResponse = JSONObject.parseObject(resultBean.getData().toString(), SettingResponse.class);
		return settingResponse;
	}

	/**
	 * @param front
	 * @return
	 * @throws Exception
	 * @throws UnsupportedEncodingException
	 */
	private UpdateResponse update(SettingVO front) throws Exception, UnsupportedEncodingException {
		MvcResult mvcResult = mockMvc.perform(post("/setting/update").contentType(MediaType.APPLICATION_JSON_VALUE)

				.content(JSONObject.toJSONString(front))).andExpect(status().isOk()).andDo(print()).andReturn();
		ResultBean<?> resultBean = TestUtils.getResultBean(mvcResult);
		UpdateResponse updateResponse = JSONObject.parseObject(resultBean.getData().toString(), UpdateResponse.class);
		return updateResponse;
	}

	/**
	 * @param variable
	 * @return
	 */
	private MVBVariableVO getFrom(Variable variable) {
		MVBVariableVO mvbVariable = new MVBVariableVO();
		mvbVariable.setSignalName(variable.getSignalName());
		mvbVariable.setByteOffset(1);
		mvbVariable.setBitOffset(1);
		mvbVariable.setBitLen(1);
		mvbVariable.setCarriage("1号");
		mvbVariable.setDataType(BusDataType.DOUBLE.ordinal());
		mvbVariable.setName("mvb变量1");
		mvbVariable.setUnit("m");
		mvbVariable.setSystem("受电弓");
		mvbVariable.setPort(1);
		mvbVariable.setDevice(1);
		mvbVariable.setDeviceName("受电弓");
		mvbVariable.setFcode(4);
		return mvbVariable;
	}

	/**
	 * @param ip
	 * @param type
	 * @param slotId
	 * @return
	 */
	private BoardSettingVO getBoard(String ip, String type, int slotId) {
		BoardSettingVO board = new BoardSettingVO();
		board.setEnable(true);
		board.setType(type);
		board.setSlotId(slotId);
		board.setIp(ip);
		return board;
	}

	/**
	 * @param mvbFile
	 * @return
	 * @throws IOException
	 * @throws Exception
	 * @throws UnsupportedEncodingException
	 */
	private ProtocolResponse uploadProtocolFile(File mvbFile) throws IOException, Exception, UnsupportedEncodingException {
		byte[] bs = FileUtils.readFileToByteArray(mvbFile);
		MvcResult mvcResult = mockMvc.perform(multipart("/setting/upload/protocol")

				.file(new MockMultipartFile("file", mvbFile.getAbsolutePath(), "mutilpart/form-data", bs))

				.param("type", "MVB"))

				.andExpect(status().isOk()).andDo(print()).andReturn();
		ResultBean<?> bean = TestUtils.getResultBean(mvcResult);
		ProtocolResponse response = JSONObject.parseObject(bean.getData().toString(), ProtocolResponse.class);
		return response;
	}

	/**
	 * @param script
	 * @return
	 * @throws IOException
	 * @throws Exception
	 * @throws UnsupportedEncodingException
	 */
	private String uploadScript(File script) throws IOException, Exception, UnsupportedEncodingException {
		byte[] bs = FileUtils.readFileToByteArray(script);
		MvcResult mvcResult = mockMvc.perform(multipart("/setting/upload/script")

				.file(new MockMultipartFile("file", script.getAbsolutePath(), "multipart/form-data", bs)))

				.andExpect(status().isOk()).andDo(print()).andReturn();
		ResultBean<?> bean = TestUtils.getResultBean(mvcResult);
		String path = bean.getData().toString();
		return path;
	}
}
