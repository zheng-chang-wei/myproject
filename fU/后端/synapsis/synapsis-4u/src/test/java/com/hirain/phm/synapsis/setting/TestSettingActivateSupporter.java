/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

import com.hirain.phm.synapsis.BaseTest;
import com.hirain.phm.synapsis.setting.db.SettingDbService;
import com.hirain.phm.synapsis.setting.support.SettingActivateSupporter;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Apr 22, 2020 10:56:29 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Apr 22, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@ActiveProfiles({ "dev", "xml" })
public class TestSettingActivateSupporter extends BaseTest {

	@Autowired
	private SettingActivateSupporter supporter;

	@Autowired
	private SettingDbService dbService;

	@Test
	public void testActivate() throws Exception {
		Setting setting = dbService.selectWithDetail(1);
		supporter.acivate(setting);
	}
}
