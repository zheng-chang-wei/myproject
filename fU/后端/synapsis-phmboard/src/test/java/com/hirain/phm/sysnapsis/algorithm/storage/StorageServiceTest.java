/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.sysnapsis.algorithm.storage;

import static org.junit.Assert.fail;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hirain.phm.synapsis.algorithm.BaseTest;
import com.hirain.phm.synapsis.algorithm.storage.CacheService;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec Feb 12, 2020 5:03:03 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Feb 12, 2020 zepei.tao@hirain.com 1.0 create file
 */
public class StorageServiceTest extends BaseTest {

	@Autowired
	private CacheService service;

	@Test
	public void testStorage() throws Exception {
		service.startCheck();
		CountDownLatch latch = new CountDownLatch(3);
		ExecutorService executor = Executors.newFixedThreadPool(3);
		executor.submit(() -> {
			for (int i = 0; i < 100; i++) {
				try {
					String mvb = "mvb raw datas";
					final byte[] datas = mvb.getBytes();
					// service.storage(datas, VariableType.MVB);
				} catch (Exception e) {
					e.printStackTrace();
					fail();
				}
			}
			latch.countDown();
		});
		executor.submit(() -> {
			for (int i = 0; i < 100; i++) {
				try {
					String ecn = "ecn raw datas";
					final byte[] datas = ecn.getBytes();
					// service.storage(datas, VariableType.ECN);
				} catch (Exception e) {
					e.printStackTrace();
					fail();
				}
			}
			latch.countDown();
		});
		executor.submit(() -> {
			for (int i = 0; i < 100; i++) {
				try {
					String ad = "ad raw datas";
					final byte[] datas = ad.getBytes();
					// service.storage(datas, VariableType.AD);
				} catch (Exception e) {
					e.printStackTrace();
					fail();
				}
			}
			latch.countDown();
		});
		latch.await();
	}

	// @Test
	// public void test2() throws InterruptedException {
	// CountDownLatch latch = new CountDownLatch(1);
	// ExecutorService executor = Executors.newSingleThreadExecutor();
	// executor.submit(() -> {
	// while (true) {
	// System.err.println(LocalDateTime.now().toString());
	// }
	// });
	// latch.await();
	// }
}
