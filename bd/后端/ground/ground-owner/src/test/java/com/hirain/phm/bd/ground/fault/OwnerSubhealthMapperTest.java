/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.fault;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hirain.phm.bd.ground.TestApplication;
import com.hirain.phm.bd.ground.fault.dao.OwnerSubhealthMapper;
import com.hirain.phm.bd.ground.fault.param.FaultCount;
import com.hirain.phm.bd.ground.fault.param.FaultRequest;
import com.hirain.phm.bd.ground.fault.param.SubhealthResponse;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jun 28, 2020 5:34:01 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jun 28, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@SpringBootTest(classes = TestApplication.class)
@RunWith(SpringRunner.class)
public class OwnerSubhealthMapperTest {

	@Autowired
	private OwnerSubhealthMapper mapper;

	@Test
	public void test_select_top20() {
		List<SubhealthResponse> list = mapper.selectTop20("深圳7号线一期", "717");
		list.forEach(System.out::println);
	}

	@Test
	public void test_select_by_param() {
		FaultRequest request = new FaultRequest();
		request.setProject("深圳7号线一期");
		request.setTrain("717");
		List<SubhealthResponse> list = mapper.selectByParam(request);
		list.forEach(System.out::println);
	}

	@Test
	public void test_count_by_month() {
		List<FaultCount> list = mapper.countByMonth("深圳7号线一期", "717", 6, Arrays.asList("电机工况异常", "闭锁组件异常", "行程开关异常", "尺带张紧力过松"));
		list.forEach(System.err::println);
	}
}
