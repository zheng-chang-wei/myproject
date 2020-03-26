/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.subhealth;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hirain.phm.bd.ground.subhealth.param.SubhealthWithSuggestionParams;
import com.hirain.phm.bd.ground.subhealth.service.SubhealthQueryService;
import com.hirain.phm.bd.ground.util.RedisUtil;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 8, 2019 4:45:32 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 8, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@SpringBootTest(classes = TestApplication.class)
@RunWith(SpringRunner.class)
public class TestDashboard {

	@Autowired
	private SubhealthQueryService service;

	@Autowired
	private RedisUtil redisUtil;

	@Test
	public void testToday() {
		List<SubhealthWithSuggestionParams> list = service.selectToday("", "");
		System.out.println(list);
	}

	@Test
	public void testToday4Bode() {
		List<SubhealthWithSuggestionParams> list = service.selectToday();
		System.out.println(list.size());
	}

	@Test
	public void testAnnualCount() {
		int count = service.selectAnnualCount(2019);
		System.out.println(count);
	}

	@Test
	public void insert() {
		Map<Object, Object> map = new HashMap<>();
		Random rand = new Random();
		for (int i = 0; i < 12; i++) {
			map.put(String.valueOf(i + 1), String.valueOf(rand.nextInt(10) + 10));
		}
		redisUtil.hset("subhealth-2019", map);

		map = new HashMap<>();
		for (int i = 0; i < 12; i++) {
			map.put(String.valueOf(i + 1), String.valueOf(rand.nextInt(10) + 10));
		}
		redisUtil.hset("subhealth-2018", map);
	}
}
