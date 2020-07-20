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
import com.hirain.phm.bd.ground.fault.dao.OwnerFaultMapper;
import com.hirain.phm.bd.ground.fault.param.FaultCount;
import com.hirain.phm.bd.ground.fault.param.FaultRequest;
import com.hirain.phm.bd.ground.fault.param.FaultResponse;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jun 28, 2020 3:47:30 PM
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
public class OwnerFaultMapperTest {

	@Autowired
	private OwnerFaultMapper mapper;

	@Test
	public void test_select_top20() {
		List<FaultResponse> list = mapper.selectTop20("深圳7号线一期", "717");
		list.forEach(System.out::println);
	}

	@Test
	public void test_select_by_param() {
		FaultRequest request = new FaultRequest();
		request.setProject("深圳7号线一期");
		request.setTrain("717");
		List<FaultResponse> list = mapper.selectByParam(request);
		list.forEach(System.out::println);
	}

	@Test
	public void test_count_by_month() {
		List<FaultCount> list = mapper.countByMonth("上海1号线一期", "1001", 6, Arrays.asList("门电机开路故障", "门电机过流故障", "门锁闭开关故障", "门编码器故障"));
		list.forEach(System.err::println);
	}
}
