/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.fault;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.hirain.phm.bd.ground.fault.param.FaultDetailWithSuggestionParams;
import com.hirain.phm.bd.ground.fault.service.FaultDetailService;
import com.hirain.phm.bd.ground.util.RedisUtil;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 8, 2019 3:20:59 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 8, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@SpringBootTest(classes = TestApplication.class)
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class TestDashboard {

	@Autowired
	private FaultDetailService service;

	@Autowired
	private RedisUtil redisUtil;

	private List<Integer> thisYear = new ArrayList<>();

	private List<Integer> lastYear = new ArrayList<>();

	@Before
	public void setUp() {
		Map<Object, Object> map = new HashMap<>();
		Random rand = new Random();
		for (int i = 0; i < 12; i++) {
			int value = rand.nextInt(20);
			map.put(String.valueOf(i + 1), String.valueOf(value));
			thisYear.add(value);
		}
		redisUtil.hset("fault-2019", map);

		map = new HashMap<>();
		for (int i = 0; i < 12; i++) {
			int value = rand.nextInt(20);
			map.put(String.valueOf(i + 1), String.valueOf(value));
			lastYear.add(value);
		}
		redisUtil.hset("fault-2018", map);
	}

	@After
	public void tearDown() {
		redisUtil.del("fault-2019", "fault-2018");
	}

	@Test
	public void testAnnualCount() {
		int count = service.selectAnnualCount(2019);

		int sum = 0;
		for (Integer value : thisYear) {
			sum += value;
		}

		assertEquals(count, sum);
	}

	@Test
	public void selectToday() {
		List<FaultDetailWithSuggestionParams> result = service.selectToday("", "");
		assertEquals(3, result.size());

		FaultDetailWithSuggestionParams detail = result.get(0);
		assertNotNull(detail.getTreatment());
		assertNotNull(detail.getRepair());
		System.out.println(detail.getTreatment());
		System.out.println(detail.getRepair());
	}

	@Test
	public void selectToday4Bode() {
		List<FaultDetailWithSuggestionParams> result = service.selectToday();
		System.out.println(result.size());
	}
}
