/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.algorithm;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.hirain.phm.synapsis.algorithm.domain.Algorithm;
import com.hirain.phm.synapsis.algorithm.domain.RunStatus;
import com.hirain.phm.synapsis.algorithm.param.AlgorithmPacket;
import com.hirain.phm.synapsis.algorithm.param.AlgorithmStatisticsResponse;
import com.hirain.phm.synapsis.algorithm.param.SubsystemCount;
import com.hirain.phm.synapsis.algorithm.service.AlgorithmService;
import com.hirain.phm.synapsis.algorithm.service.impl.AlgorithmServiceImpl;
import com.hirain.phm.synapsis.algorithm.service.test.TestAlgorithmSettingQuery;
import com.hirain.phm.synapsis.setting.AlgorithmSetting;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 18, 2019 11:45:49 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 18, 2019 jianwen.xin@hirain.com 1.0 create file
 */
public class AlgorithmTest {

	private AlgorithmService service = new AlgorithmServiceImpl();

	private List<AlgorithmSetting> settings;

	@Before
	public void setUp() {
		settings = createAlgorithmSettings();
		((AlgorithmServiceImpl) service).setQuery(new TestAlgorithmSettingQuery());
		service.init(settings);
	}

	@Test
	public void testInit() {
		List<AlgorithmPacket> list = service.list();
		assertEquals(settings.size(), list.size());

		AlgorithmSetting setting = settings.get(0);
		Algorithm algorithm = list.get(0).getData();

		assertEquals(setting.getCode().intValue(), algorithm.getCode());
		assertEquals(setting.getEnable().booleanValue(), algorithm.isEnable());
		assertEquals(setting.getName(), algorithm.getName());
		assertEquals(setting.getSubsystem(), algorithm.getSubsystem());
	}

	@Test
	public void testUpdate() {
		Map<Integer, RunStatus> statusMap = new HashMap<>();
		statusMap.put(1, RunStatus.Run);
		statusMap.put(2, RunStatus.Err);
		service.update(statusMap);
		List<AlgorithmPacket> list = service.list();

		for (AlgorithmPacket packet : list) {
			assertEquals(statusMap.get(packet.getData().getCode()), packet.getData().getStatus());
		}
	}

	@Test
	public void testCount() {
		AlgorithmStatisticsResponse response = service.count();
		assertEquals(settings.size(), response.getTotal());
		assertEquals(0, response.getRunning());

		List<SubsystemCount> subsystems = response.getSubsystems();
		assertEquals(2, subsystems.size());

		Map<Integer, RunStatus> statusMap = new HashMap<>();
		statusMap.put(1, RunStatus.Run);
		statusMap.put(2, RunStatus.Err);
		service.update(statusMap);

		response = service.count();
		assertEquals(1, response.getRunning());
		assertEquals(1, response.getError());
	}

	/**
	 * 
	 */
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
