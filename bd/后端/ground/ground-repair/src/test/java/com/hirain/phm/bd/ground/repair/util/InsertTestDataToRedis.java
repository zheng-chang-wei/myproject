/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.repair.util;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hirain.phm.bd.ground.maintenance.redis.WorksheetRedisMapper;
import com.hirain.phm.bd.ground.repair.TestApplication;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Nov 19, 2019 9:02:28 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Nov 19, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class)
public class InsertTestDataToRedis {

	@Autowired
	private WorksheetRedisMapper mapper;

	@Test
	public void insert() {
		Date now = new Date();
		for (int i = 0; i < 20; i++) {
			mapper.addSheet(now);
		}

		for (int i = 0; i < 11; i++) {
			mapper.handleSheet(now);
		}

		Calendar calendar = Calendar.getInstance();
		calendar.set(2019, 9, 1);
		for (int i = 0; i < 9; i++) {
			mapper.addSheet(calendar.getTime());
		}

		mapper.integrateSheets();

		int newSheet = mapper.countNewSheet(now);
		assertEquals(20, newSheet);

		int handledSheet = mapper.countHandledSheet(now);
		assertEquals(11, handledSheet);

		int unHandled = mapper.countUnHandled();
		assertEquals(9, unHandled);
	}

}
