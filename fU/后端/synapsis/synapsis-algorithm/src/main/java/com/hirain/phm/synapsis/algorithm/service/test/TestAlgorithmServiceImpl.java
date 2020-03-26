/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.algorithm.service.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.hirain.phm.synapsis.algorithm.domain.Algorithm;
import com.hirain.phm.synapsis.algorithm.domain.RunStatus;
import com.hirain.phm.synapsis.algorithm.param.AlgorithmPacket;
import com.hirain.phm.synapsis.algorithm.param.AlgorithmStatisticsResponse;
import com.hirain.phm.synapsis.algorithm.param.Record;
import com.hirain.phm.synapsis.algorithm.param.SubsystemCount;
import com.hirain.phm.synapsis.algorithm.service.AlgorithmService;
import com.hirain.phm.synapsis.algorithm.service.RecordMapper;
import com.hirain.phm.synapsis.setting.AlgorithmSetting;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 23, 2019 2:20:16 PM
 * @Description
 *              <p>
 *              测试环境
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 23, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Service
@Profile("test")
@Primary
public class TestAlgorithmServiceImpl implements AlgorithmService {

	private List<AlgorithmPacket> algorithms = new ArrayList<>();

	@Autowired
	private RecordMapper recordMapper;

	/**
	 * @see com.hirain.phm.synapsis.algorithm.service.AlgorithmService#list()
	 */
	@Override
	public List<AlgorithmPacket> list() {
		if (algorithms.isEmpty()) {
			for (int i = 0; i < 4; i++) {
				Algorithm algorithm = new Algorithm();
				algorithm.setCode(i);
				algorithm.setSlotId(i + 1);
				algorithm.setName("算法" + (i + 1));
				algorithm.setStatus(RunStatus.values()[i]);
				algorithm.setSubsystem("受电弓");
				AlgorithmPacket packet = new AlgorithmPacket();
				packet.setData(algorithm);
				Map<String, Integer> map = new HashMap<>();
				map.put("mvb", 1);
				map.put("ecn", 2);
				map.put("ad", 1);
				packet.setVariable(map);
				algorithms.add(packet);
			}
		}
		return algorithms;
	}

	/**
	 * @see com.hirain.phm.synapsis.algorithm.service.AlgorithmService#init(java.util.List)
	 */
	@Override
	public void init(List<AlgorithmSetting> settings) {

	}

	/**
	 * @see com.hirain.phm.synapsis.algorithm.service.AlgorithmService#update(java.util.Map)
	 */
	@Override
	public void update(Map<Integer, RunStatus> statusMap) {

	}

	/**
	 * @see com.hirain.phm.synapsis.algorithm.service.AlgorithmService#count()
	 */
	@Override
	public AlgorithmStatisticsResponse count() {
		AlgorithmStatisticsResponse response = new AlgorithmStatisticsResponse();
		response.setError(1);
		response.setRunning(1);
		response.setStopped(1);
		response.setTotal(4);
		SubsystemCount count = new SubsystemCount();
		count.setRunning(1);
		count.setTotal(4);
		count.setSubsystem("受电弓");
		response.setSubsystems(Arrays.asList(count));
		return response;
	}

	/**
	 * @see com.hirain.phm.synapsis.algorithm.service.AlgorithmService#getSetting(int)
	 */
	@Override
	public AlgorithmSetting getSetting(int code) {
		return null;
	}

	/**
	 * @see com.hirain.phm.synapsis.algorithm.service.AlgorithmService#getRecords()
	 */
	@Override
	public List<Record> getRecords() {
		List<Record> records = recordMapper.getRecords();
		if (records.isEmpty()) {
			recordMapper.addRecord("系统启动");
			recordMapper.addRecord("算法1【1】异常运行");
			recordMapper.addRecord("算法1【1】正常运行");
		}
		return recordMapper.getRecords();
	}

	/**
	 * @see com.hirain.phm.synapsis.algorithm.service.AlgorithmService#updateStatus(com.hirain.phm.synapsis.algorithm.domain.RunStatus)
	 */
	@Override
	public void updateStatus(RunStatus status) {
		for (AlgorithmPacket packet : algorithms) {
			packet.getData().setStatus(status);
		}
	}

}
