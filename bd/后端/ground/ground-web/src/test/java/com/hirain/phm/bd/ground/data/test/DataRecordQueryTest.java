/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.data.test;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.hirain.phm.bd.ground.GroundWebApplication;
import com.hirain.phm.bd.ground.bigdata.param.DataRecordResponse;
import com.hirain.phm.bd.ground.bigdata.param.DataRequest;
import com.hirain.phm.bd.ground.bigdata.service.DataRecordService;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Mar 11, 2020 3:35:31 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Mar 11, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@SpringBootTest(classes = GroundWebApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class DataRecordQueryTest {

	@Autowired
	private DataRecordService service;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private DataRequest request;

	@Before
	public void setUp() throws ParseException {
		request = new DataRequest();
		request.setProject("深圳");
		request.setTrain("717");
		request.setTime(sdf.parse("2020-03-11 00:00:00"));
		request.setPageNum(1);
		request.setPageSize(20);
	}

	@Test
	public void testCount() {
		int count = service.count(request);
		System.out.println(count);
	}

	@Test
	public void testSelect() {
		DataRecordResponse response = service.getDataRecords(request);
		System.out.println(response.getTotal());
		assertEquals(request.getPageSize(), response.getRecords().size());
		response.getRecords().forEach(System.out::println);
	}
}
