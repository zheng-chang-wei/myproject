/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.algorithm;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.hirain.phm.synapsis.algorithm.param.AlgorithmPacket;
import com.hirain.phm.synapsis.algorithm.service.AlgorithmService;
import com.hirain.phm.synapsis.setting.AlgorithmSetting;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 18, 2019 1:49:36 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 18, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@SpringBootTest(classes = TestApplication.class)
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class AlgorithmTimerTest {

	@Autowired
	private AlgorithmStatusTimer timer;

	@Autowired
	private AlgorithmService service;

	@Before
	public void setUp() {
		List<AlgorithmSetting> settings = createAlgorithmSettings();
		service.init(settings);
	}

	@Test
	public void test() throws InterruptedException {
		System.err.println("timer start");
		timer.start();
		checkStatus();
		timer.stop();
		System.err.println("timer stop");
		checkStatus();
		System.err.println("timer start again");
		timer.start();
		checkStatus();
		timer.stop();

	}

	/**
	 * @throws InterruptedException
	 */
	private void checkStatus() throws InterruptedException {
		for (int i = 0; i < 5; i++) {
			List<AlgorithmPacket> list = service.list();
			System.out.println(list);
			TimeUnit.SECONDS.sleep(2);
		}
	}

	private List<AlgorithmSetting> createAlgorithmSettings() {
		AlgorithmSetting setting1 = new AlgorithmSetting();
		setting1.setCode(1);
		setting1.setEnable(true);
		setting1.setName("算法1");
		setting1.setSubsystem("受电弓");
		setting1.setSlotId(1);

		AlgorithmSetting setting2 = new AlgorithmSetting();
		setting2.setCode(2);
		setting2.setEnable(true);
		setting2.setName("算法2");
		setting2.setSubsystem("车门");
		setting2.setSlotId(1);
		return Arrays.asList(setting1, setting2);
	}
}
