/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hirain.phm.synapsis.constant.BoardType;
import com.hirain.phm.synapsis.setting.dao.SettingMapper;
import com.hirain.phm.synapsis.setting.service.SettingService;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 4, 2019 5:42:31 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 4, 2019 jianwen.xin@hirain.com 1.0 create file
 */
public class SettingSaveTest extends BaseTest {

	@Autowired
	private SettingService service;

	@Autowired
	private SettingMapper mapper;

	@Test
	public void testSave() {
		Setting result = service.save(TestObjectUtils.getSetting());
		assertNotNull(result.getBoardSettings().get(0).getId());
		Setting setting = service.selectWithDetail(result.getId());

		System.out.println(setting);
		System.out.println(result);
		assertNotNull(setting);

		assertFalse(setting.getBoardSettings().isEmpty());
		assertNotNull(setting.getBoardSettings().get(0).getId());
		assertNotNull(result.getBoardSettings().get(0).getId());

		List<BoardSetting> settings = setting.getBoardSettings();
		for (BoardSetting board : settings) {
			if (BoardType.valueOf(board.getType()).getType().equals("AD")) {
				assertFalse(board.getVariableGroups().isEmpty());
				assertFalse(board.getVariableGroups().get(0).getVariables().isEmpty());
			}
		}

		assertFalse(setting.getAlgorithmSettings().isEmpty());
		assertNotNull(setting.getAlgorithmSettings().get(0).getId());
		assertNotNull(result.getAlgorithmSettings().get(0).getId());

		List<AlgorithmSetting> asettings = setting.getAlgorithmSettings();
		for (AlgorithmSetting algorithm : asettings) {
			assertFalse(algorithm.getVariableGroups().isEmpty());
			assertFalse(algorithm.getVariableGroups().get(0).getVariables().isEmpty());
		}

		assertNotNull(setting.getTimeVariables());

		assertFalse(setting.getStoreVariables().isEmpty());
		assertFalse(setting.getStoreVariables().get(0).getVariables().isEmpty());
	}

	@Test
	public void testSave1() {
		Setting setting = TestObjectUtils.getSetting();
		setting.setTimeOn(null);
		mapper.insertGenerateKey(setting);
		System.out.println(setting.getId());

		setting.setTimeOn(true);
		setting.setLine(null);
		mapper.updateByKey(setting);
	}
}
