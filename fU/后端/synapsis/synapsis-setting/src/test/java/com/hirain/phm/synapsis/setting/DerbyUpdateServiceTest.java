/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.hirain.phm.synapsis.setting.db.SettingDbService;
import com.hirain.phm.synapsis.setting.db.SettingUpdateService;
import com.hirain.phm.synapsis.setting.service.AlgorithmSettingService;
import com.hirain.phm.synapsis.setting.service.BoardSettingService;
import com.hirain.phm.synapsis.setting.service.StoreSettingService;
import com.hirain.phm.synapsis.setting.service.TimeSettingService;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Apr 21, 2020 4:28:09 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Apr 21, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DerbyUpdateServiceTest extends BaseTest {

	@Autowired
	private SettingUpdateService service;

	@Autowired
	private SettingDbService dbService;

	@Autowired
	private BoardSettingService boardService;

	@Autowired
	private AlgorithmSettingService algorithmService;

	@Autowired
	private StoreSettingService storeService;

	@Autowired
	private TimeSettingService timeService;

	private static Integer id = 1;

	@Test
	public void test01() {
		Setting setting = TestObjectUtils.getSetting();
		service.createOrUpdate(setting);

		assertNotNull(setting.getId());
		id = setting.getId();
		Setting r1 = dbService.selectById(setting.getId());
		assertEquals(r1.getName(), setting.getName());
	}

	@Test
	public void test02_Update() {
		Setting old = dbService.selectById(id);
		Setting setting = new Setting();
		setting.setId(id);
		setting.setTrain("2");
		service.createOrUpdate(setting);

		Setting result = dbService.selectById(id);
		assertEquals(setting.getTrain(), result.getTrain());
		assertEquals(old.getSelected(), result.getSelected());
	}

	@Test
	public void test03_UpdateBoard() {
		List<BoardSetting> boards = TestObjectUtils.getBoards();
		service.updateBoardSetting(id, boards);

		List<BoardSetting> list = boardService.selectBySettingId(id);
		assertEquals(boards.size(), list.size());
	}

	@Test
	public void test04_UpdateAlgorithm() {
		List<AlgorithmSetting> algorithms = TestObjectUtils.getAlgorithms();
		service.updateAlgorithmSetting(id, algorithms);

		List<AlgorithmSetting> list = algorithmService.selectBySettingId(id);
		assertEquals(algorithms.size(), list.size());
	}

	@Test
	public void test05_UpdateStore() {
		StoreSetting storeSetting = TestObjectUtils.createStore();
		service.updateStoreSetting(id, storeSetting);

		StoreSetting result = storeService.selectByKey(id);
		assertNotNull(result);

		assertEquals(storeSetting.getResultSpace(), result.getResultSpace());
	}

	@Test
	public void test06_UpdateTime() {
		TimeSetting timeSetting = TestObjectUtils.getTime();
		service.updateTimeSetting(id, timeSetting);

		TimeSetting result = timeService.selectByKey(id);
		assertEquals(timeSetting.getType(), result.getType());
	}
}
