/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

import com.hirain.phm.synapsis.BaseTest;
import com.hirain.phm.synapsis.constant.BoardType;
import com.hirain.phm.synapsis.setting.property.BusBoardProperty;
import com.hirain.phm.synapsis.setting.support.SettingUpdateSupporter;
import com.hirain.phm.synapsis.util.SettingFileConfig;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Apr 22, 2020 1:45:19 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Apr 22, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@ActiveProfiles({ "dev", "xml" })
public class TestSettingUpdateSupport extends BaseTest {

	@Autowired
	private SettingUpdateSupporter supporter;

	@Autowired
	private SettingFileConfig config;

	@Test
	public void testHandleBoardResource() throws Exception {
		BoardSetting boardSetting = new BoardSetting();
		boardSetting.setType(BoardType.MVB.name());
		BusBoardProperty property = new BusBoardProperty();
		property.setFilename("04c42847-a396-446c-aac9-2bc7898b6f95.xlsx");
		boardSetting.setProperty(property);

		supporter.handleBoardResources("test", Arrays.asList(boardSetting));
	}

	@Test
	public void testHandleAlgorithmResource() throws IOException {
		AlgorithmSetting setting = new AlgorithmSetting();
		setting.setFilename("7ae47175-190e-4b45-bc16-db41daf55953.py");
		supporter.handleAlgorithmResources("test", Arrays.asList(setting));
	}

	@Test
	public void testCreateOrRename() {
		supporter.createOrRename(null, "test");

		File folder = new File(config.getResource(), "test");
		assertTrue(folder.exists());

		supporter.createOrRename("test", "test1");

		File folder2 = new File(config.getResource(), "test1");
		assertTrue(folder2.exists());
		assertFalse(folder.exists());
	}
}
