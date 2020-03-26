/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.bigdata;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hirain.phm.bd.ground.bigdata.service.DataRedisService;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Mar 20, 2020 6:14:03 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Mar 20, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@SpringBootTest(classes = BigDataTestApplication.class)
@RunWith(SpringRunner.class)
public class RedisUtilTest {

	@Autowired
	private DataRedisService service;

	@Test
	public void test() {
		String key = "temp-202003201815";
		String value = "test message";
		service.set(key, value, 2, TimeUnit.HOURS);

		String message = service.get(key);
		assertEquals(value, message);
	}
}
