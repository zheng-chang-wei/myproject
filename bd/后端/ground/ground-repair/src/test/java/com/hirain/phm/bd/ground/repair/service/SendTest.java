/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.repair.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.hirain.phm.bd.ground.common.event.UnifiedFaultRecord;
import com.hirain.phm.bd.ground.push.service.PushFaultService;
import com.hirain.phm.bd.ground.repair.TestApplication;
import com.hirain.phm.bd.ground.repair.util.TestObjectCreater;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Oct 18, 2019 4:05:01 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Oct 18, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@SpringBootTest(classes = TestApplication.class)
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class SendTest {

	@Autowired
	private List<PushFaultService> pushList;

	@Test
	public void testInject() {
		assertNotNull(pushList);
		assertEquals(1, pushList.size());
	}

	@Test
	public void test() {
		UnifiedFaultRecord record = TestObjectCreater.getFaultRecord();

		pushList.forEach(push -> {
			push.push(record, 64);
		});
	}
}
