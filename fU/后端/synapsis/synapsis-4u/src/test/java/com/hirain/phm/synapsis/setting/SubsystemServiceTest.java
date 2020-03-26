/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hirain.phm.synapsis.BaseTest;
import com.hirain.phm.synapsis.setting.db.SubsystemService;

/**
 * @Version 1.0
 * @Author changwei.zheng@hirain.com
 * @Created 2020年2月24日 下午7:20:42
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2020年2月24日 changwei.zheng@hirain.com 1.0 create file
 */
public class SubsystemServiceTest extends BaseTest {

	@Autowired
	private SubsystemService subsystemService;

	@Test
	public void test_selcetAll() {
		List<Subsystem> list = subsystemService.selectAllSubsystems();
		System.out.println(list);
	}

	// @Test
	// public void test_selcetByName() {
	// Subsystem list = subsystemService.selectByName("system12");
	// System.out.println(list);
	// }

}
