/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.bigdata;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hirain.phm.bd.ground.bigdata.service.DataManageService;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Mar 23, 2020 11:50:52 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Mar 23, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@SpringBootTest(classes = BigDataTestApplication.class)
@RunWith(SpringRunner.class)
public class DataManagerServiceTest {

	@Autowired
	private DataManageService service;

	@Test
	public void test() {
		Integer space = service.getSpace();
		assertNotNull(space);
		assertTrue(space <= 100);
		System.out.println(space);
	}
}
