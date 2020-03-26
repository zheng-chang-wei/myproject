/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hirain.phm.synapsis.setting.service.SettingService;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 3, 2019 6:12:06 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 3, 2019 jianwen.xin@hirain.com 1.0 create file
 */
public class SettingTest extends BaseTest {

	@Autowired
	private SettingService service;

	@Test
	public void testListAll() {
		List<Setting> list = service.selectAll();
		assertNotNull(list);

		assertFalse(list.isEmpty());
	}

	@Test
	public void testSelect() {
		Setting setting = service.selectById(1);
		assertNotNull(setting);

		assertNotNull(setting.getId());
	}

	@Test
	public void testCurrent() {
		Setting setting = service.selectCurrent();
		assertNotNull(setting);
	}

	@Test
	public void testAll() {
		Setting setting = service.selectWithDetail(104);
		assertNotNull(setting);
		assertEquals(104, setting.getId().intValue());
		System.out.println(setting);

		assertNotNull(setting.getStoreVariables());
		assertNotNull(setting.getAlgorithmSettings());
		assertNotNull(setting.getBoardSettings());
		assertNotNull(setting.getBoardSettings().get(0).getId());

		assertNotNull(setting.getBoardSettings().get(0).getFileOriginalName());

		if (setting.getTimeOn()) {
			assertNotNull(setting.getTimeVariables());
		}

		AlgorithmSetting algorithmSetting = setting.getAlgorithmSettings().get(0);
		assertNotNull(algorithmSetting.getId());
		assertNotNull(algorithmSetting.getVariableGroups());

		BoardSetting boardSetting = setting.getBoardSettings().get(0);
		assertNotNull(boardSetting.getVariableGroups());

		assertNotNull(setting.getTimeVariables().getVariables());

		assertNotNull(setting.getStoreVariables().get(0).getVariables());

		assertNotNull(algorithmSetting.getVariableGroups().get(0).getVariables());
		assertNotNull(boardSetting.getVariableGroups().get(0).getVariables());
		System.out.println(setting);
	}

	@Test
	public void testByName() {
		Setting setting1 = service.selectByName("配置1");
		assertNotNull(setting1);

		Setting setting2 = service.selectByName("配置2");
		assertNull(setting2);
	}
}
