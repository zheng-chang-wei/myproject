/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hirain.phm.synapsis.setting.service.VariableManager;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 22, 2020 10:38:00 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 22, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public class VariableServiceTest extends BaseTest {

	@Autowired
	private VariableManager service;

	@Test
	public void testInsertMVB() {
		MVBVariable variable = TestObjectUtils.createMVBVariable("mvb", 1);
		service.insertVariable("MVB", variable);
		assertNotNull(variable.getId());
	}

	@Test
	public void testInsertEcn() {
		ECNVariable variable = TestObjectUtils.createECNVariable("ecn");
		service.insertVariable("ECN", variable);
		assertNotNull(variable.getId());
	}
}
