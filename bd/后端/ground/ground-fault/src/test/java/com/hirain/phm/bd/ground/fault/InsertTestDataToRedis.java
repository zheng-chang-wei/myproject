/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.fault;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hirain.phm.bd.ground.fault.param.AnnualCountResponse;
import com.hirain.phm.bd.ground.fault.service.FaultDetailService;
import com.hirain.phm.bd.ground.util.RedisUtil;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Nov 19, 2019 9:39:18 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Nov 19, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@SpringBootTest(classes = TestApplication.class)
@RunWith(SpringRunner.class)
public class InsertTestDataToRedis {

	@Autowired
	private RedisUtil redisUtil;

	@Autowired
	private FaultDetailService service;

	@Test
	public void insert() {
		Map<Object, Object> map = new HashMap<>();
		Random rand = new Random();
		for (int i = 0; i < 12; i++) {
			map.put(String.valueOf(i + 1), String.valueOf(rand.nextInt(10) + 10));
		}
		redisUtil.hset("fault-2019", map);

		map = new HashMap<>();
		for (int i = 0; i < 12; i++) {
			map.put(String.valueOf(i + 1), String.valueOf(rand.nextInt(10) + 10));
		}
		redisUtil.hset("fault-2018", map);

		AnnualCountResponse thisYear = service.selectYearCounts(2019);
		System.out.println(thisYear);
		AnnualCountResponse lastYear = service.selectYearCounts(2018);
		System.out.println(lastYear);
	}
}
