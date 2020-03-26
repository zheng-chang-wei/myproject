/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting.support;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hirain.phm.synapsis.setting.support.MulticastAssigner;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 11, 2019 2:27:47 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 11, 2019 jianwen.xin@hirain.com 1.0 create file
 */
public class MutilcastAssignerTest extends BaseTest {

	@Autowired
	private MulticastAssigner assigner;

	private int port = 65535;

	@Test
	public void test() {
		assigner.start();
		long consumptionId = assigner.nextConsumption();
		assertEquals(1l, consumptionId);
	}

	@Test
	public void testConsumptionArray() {
		assigner.start();
		long[] idArr = new long[10];
		for (int i = 0; i < idArr.length; i++) {
			idArr[i] = assigner.nextConsumption();
		}
		assertArrayEquals(new long[] { 1l, 2l, 3l, 4l, 5l, 6l, 7l, 8l, 9l, 10l }, idArr);
	}

	@Test
	public void testIp255() {
		assigner.start();
		assertEquals("225.0.0.1", assigner.nextIp());
	}

	@Test
	public void testPort() {
		assigner.start();
		assertEquals(port, assigner.nextPort());
		assertEquals(port, assigner.nextPort());
	}

	@Test
	public void testRetryIp() {
		assigner.start();
		assertEquals("224.255.255.255", assigner.retryIp());
	}

}
