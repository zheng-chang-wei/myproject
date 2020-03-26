/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hirain.phm.synapsis.constant.BoardType;
import com.hirain.phm.synapsis.setting.BoardSetting;
import com.hirain.phm.synapsis.setting.ECNVariable;
import com.hirain.phm.synapsis.setting.Setting;
import com.hirain.phm.synapsis.setting.VariableGroup;
import com.hirain.phm.synapsis.setting.Variable.VariableType;
import com.hirain.phm.synapsis.setting.service.SettingService;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 5, 2019 5:30:10 PM
 * @Description
 *              <p>
 *              数据更新，全部删除再插入。
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 5, 2019 jianwen.xin@hirain.com 1.0 create file
 */
public class SettingUpdateTest extends BaseTest {

	@Autowired
	private SettingService service;

	private Setting result;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		result = service.save(TestObjectUtils.getSetting());
	}

	@Test
	public void testUpdateTimeVariableToFalse() {
		result.setTimeOn(false);
		result.setTimeVariables(null);
		service.update(result);
		Setting setting = service.selectWithDetail(result.getId());
		assertEquals(false, setting.getTimeOn());
		assertNull(setting.getTimeVariables());
	}

	@Test
	public void testUpdateTimeVariableGroup() {
		VariableGroup ecnGroup = TestObjectUtils.createVariableGroup(2, VariableType.ECN.name());
		ecnGroup.setMulticastIp("192.168.40.55");
		ECNVariable ecnVariable = new ECNVariable();
		ecnVariable.setComId(1l);
		ecnGroup.setVariables(Arrays.asList(ecnVariable));
		result.setTimeVariables(ecnGroup);

		service.update(result);

		Setting setting = service.selectWithDetail(result.getId());
		assertNotNull(setting.getTimeVariables());
		assertEquals(ecnGroup.getMulticastIp(), setting.getTimeVariables().getMulticastIp());
	}

	@Test
	public void testUpdateBoardSetting() {
		System.err.println(result);
		BoardSetting expected = result.getBoardSettings().get(0);
		expected.setIp("192.168.40.55");

		BoardSetting addBoard = TestObjectUtils.createBoardSetting(1, BoardType.PHM_AGX);
		List<BoardSetting> boards = new ArrayList<>(result.getBoardSettings());
		boards.add(addBoard);
		result.setBoardSettings(boards);

		service.update(result);

		Setting setting = service.selectWithDetail(result.getId());
		BoardSetting boardSetting = setting.getBoardSettings().get(0);
		assertEquals(expected.getIp(), boardSetting.getIp());

		assertEquals(result.getBoardSettings().size(), setting.getBoardSettings().size());

		System.err.println(setting);
	}

	@Test
	public void testDeleteBoardSetting() {
		List<BoardSetting> boards = new ArrayList<>(result.getBoardSettings());
		boards.remove(0);
		result.setBoardSettings(boards);

		service.update(result);

		Setting setting = service.selectWithDetail(result.getId());
		assertEquals(boards.size(), setting.getBoardSettings().size());
	}

	@Test
	public void testUpdateBoardVariables() {
		List<BoardSetting> boards = new ArrayList<>(result.getBoardSettings());
		BoardSetting boardSetting = boards.get(2);
		VariableGroup variableGroup = boardSetting.getVariableGroups().get(0);
		variableGroup.setMulticastIp("192.168.40.55");
		result.setBoardSettings(boards);
		service.update(result);

		Setting setting = service.selectWithDetail(result.getId());

		VariableGroup group = setting.getBoardSettings().get(2).getVariableGroups().get(0);

		assertEquals(variableGroup.getMulticastIp(), group.getMulticastIp());
	}

	@Test
	public void testChangeCurrent() {
		Setting old = service.selectCurrent();
		service.changeCurrent(result.getId());

		old = service.selectById(old.getId());
		assertFalse(old.getSelected());
		Setting current = service.selectCurrent();
		assertEquals(result.getId(), current.getId());
	}
}
