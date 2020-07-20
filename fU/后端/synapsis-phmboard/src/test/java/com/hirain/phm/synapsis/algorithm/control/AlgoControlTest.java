/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.algorithm.control;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hirain.phm.synapsis.algorithm.BaseTest;
import com.hirain.phm.synapsis.algorithm.service.AlgoService;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec Dec 19, 2019 11:09:11 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 19, 2019     zepei.tao@hirain.com    1.0   create file   
 */
public class AlgoControlTest extends BaseTest {

	@Autowired
	private AlgoService service;

	@Test
	public void testAlgoLaunch() {
		try {
			service.launch();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testAlgoShutdown() {
		try {
			service.shut();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

}
