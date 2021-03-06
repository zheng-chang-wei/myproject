/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hirain.phm.synapsis.setting.db.AlgorithmSettingQuery;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 3, 2019 2:52:52 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 3, 2019 jianwen.xin@hirain.com 1.0 create file
 */
public class AlgorithmTest extends BaseTest {

	@Autowired
	private AlgorithmSettingQuery query;

	@Test
	public void test() {
		int code = 1;
		AlgorithmSetting setting = query.getAlgorithmSetting(code);
		assertNotNull(setting);
		assertEquals(1, setting.getId().intValue());

		assertEquals("受电弓", setting.getSubsystem());
	}

}
