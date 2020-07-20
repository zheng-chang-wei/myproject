/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hirain.phm.synapsis.setting.db.SettingDbService;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Apr 21, 2020 5:59:33 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Apr 21, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public class DerbyDbServiceTest extends BaseTest {

	@Autowired
	private SettingDbService service;

	@Test
	public void testDetail() {
		Setting setting = service.selectWithDetail(1);
		System.out.println(setting);
	}

	@Test
	public void testDelete() {
		service.delete(1);
	}
}
