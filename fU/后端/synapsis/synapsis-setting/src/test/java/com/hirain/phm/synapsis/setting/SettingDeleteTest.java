/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hirain.phm.synapsis.setting.AlgorithmSetting;
import com.hirain.phm.synapsis.setting.BoardSetting;
import com.hirain.phm.synapsis.setting.Setting;
import com.hirain.phm.synapsis.setting.VariableGroup;
import com.hirain.phm.synapsis.setting.dao.AlgorithmSettingMapper;
import com.hirain.phm.synapsis.setting.dao.BoardSettingMapper;
import com.hirain.phm.synapsis.setting.dao.VariableGroupMapper;
import com.hirain.phm.synapsis.setting.service.SettingService;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 6, 2019 10:26:27 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 6, 2019 jianwen.xin@hirain.com 1.0 create file
 */
public class SettingDeleteTest extends BaseTest {

	@Autowired
	private SettingService service;

	@Autowired
	private VariableGroupMapper groupMapper;

	private Setting save;

	@Autowired
	private BoardSettingMapper boardMapper;

	@Autowired
	private AlgorithmSettingMapper alMapper;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		save = service.save(TestObjectUtils.getSetting());
	}

	@Test
	public void testDelete() {
		Setting setting = service.selectWithDetail(save.getId());
		service.delete(save.getId());

		Setting result = service.selectById(save.getId());
		assertNull(result);
		Integer timeGroupId = setting.getTimeVariables().getId();
		VariableGroup timeGroup = groupMapper.selectByPrimaryKey(timeGroupId);
		assertNull(timeGroup);

		List<VariableGroup> storeVariables = setting.getStoreVariables();
		Integer storeGroupId = storeVariables.get(0).getId();
		VariableGroup storeGroup = groupMapper.selectByPrimaryKey(storeGroupId);
		assertNull(storeGroup);

		List<BoardSetting> boardSettings = setting.getBoardSettings();
		Integer boardGroupId = boardSettings.get(2).getVariableGroups().get(0).getId();
		VariableGroup boardGroup = groupMapper.selectByPrimaryKey(boardGroupId);
		assertNull(boardGroup);

		BoardSetting boardSetting = boardMapper.selectByPrimaryKey(boardSettings.get(0).getId());
		assertNull(boardSetting);

		List<AlgorithmSetting> algorithmSettings = setting.getAlgorithmSettings();
		Integer alGroupId = algorithmSettings.get(0).getVariableGroups().get(0).getId();
		VariableGroup alGroup = groupMapper.selectByPrimaryKey(alGroupId);
		assertNull(alGroup);

		AlgorithmSetting algorithmSetting = alMapper.selectByPrimaryKey(algorithmSettings.get(0).getId());
		assertNull(algorithmSetting);

		System.err.println(setting);
	}

	@Test
	public void testDeleteOldestSetting() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Setting setting1 = TestObjectUtils.getSetting();
		setting1.setLastModify(sdf.parse("2019-12-01 13:43:00"));
		service.save(setting1);

		Setting setting2 = TestObjectUtils.getSetting();
		setting2.setLastModify(sdf.parse("2019-12-05 14:00:00"));
		service.save(setting2);

		int expacted = service.selectAll().size();

		service.deleteOldest();

		List<Setting> list = service.selectAll();

		assertEquals(expacted - 1, list.size());
		Setting setting = service.selectById(setting1.getId());
		assertNull(setting);
	}
}
