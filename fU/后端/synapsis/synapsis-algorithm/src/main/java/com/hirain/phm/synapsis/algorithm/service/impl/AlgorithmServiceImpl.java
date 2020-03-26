/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.algorithm.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.hirain.phm.synapsis.setting.VariableGroup;
import com.hirain.phm.synapsis.setting.db.AlgorithmSettingQuery;

import lombok.Setter;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 17, 2019 9:31:23 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 17, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Service
public class AlgorithmServiceImpl implements AlgorithmService {

	private List<Algorithm> algorithms = new ArrayList<>();

	@Autowired
	@Setter
	private AlgorithmSettingQuery query;

	@Autowired
	private RecordMapper recordMapper;

	@Override
	public void init(List<AlgorithmSetting> settings) {
		algorithms.clear();
		for (AlgorithmSetting setting : settings) {
			Algorithm algorithm = new Algorithm();
			algorithm.setCode(setting.getCode());
			algorithm.setName(setting.getName());
			algorithm.setSubsystem(setting.getSubsystem());
			algorithm.setSlotId(setting.getSlotId());
			algorithm.setEnable(setting.getEnable());
			algorithm.setStatus(RunStatus.Idle);
			algorithms.add(algorithm);
		}
	}

	/**
	 * @see com.hirain.phm.synapsis.algorithm.service.AlgorithmService#list()
	 */
	@Override
	public List<AlgorithmPacket> list() {
		List<AlgorithmPacket> packets = new ArrayList<>();
		for (Algorithm algorithm : algorithms) {
			AlgorithmPacket packet = new AlgorithmPacket();
			packet.setData(algorithm);
			AlgorithmSetting setting = getSetting(algorithm.getCode());
			if (setting != null) {
				List<VariableGroup> groups = setting.getVariableGroups();
				Map<String, Integer> variable = new HashMap<>();
				if (groups != null) {
					for (VariableGroup group : groups) {
						Integer count = variable.get(group.getType().toLowerCase());
						if (count == null) {
							count = 0;
							variable.put(group.getType().toLowerCase(), count);
						}
						if (group.getVariables() != null) {
							count += group.getVariables().size();
						}
					}
				}
				packet.setVariable(variable);
			}
			packets.add(packet);
		}
		return packets;
	}

	@Override
	public void update(Map<Integer, RunStatus> statusMap) {
		for (Algorithm algorithm : algorithms) {
			RunStatus status = statusMap.get(algorithm.getCode());
			if (algorithm.getStatus() != RunStatus.Idle && !isEqual(status, algorithm.getStatus())) {
				recordMapper.addRecord(algorithm, algorithm.getStatus(), status);
			}
			algorithm.setStatus(status);
		}
	}

	/**
	 * @param status
	 * @param oldStatus
	 * @return
	 */
	public boolean isEqual(RunStatus status, RunStatus oldStatus) {
		if (oldStatus == null) {
			return status == null;
		}
		if (status == null) {
			return oldStatus == null;
		}
		return oldStatus.equals(status);
	}

	/**
	 * @see com.hirain.phm.synapsis.algorithm.service.AlgorithmService#count()
	 */
	@Override
	public AlgorithmStatisticsResponse count() {
		AlgorithmStatisticsResponse response = new AlgorithmStatisticsResponse();
		response.setTotal(algorithms.size());
		Map<String, SubsystemCount> subsystemMap = new HashMap<>();
		int run = 0;
		int error = 0;
		int stopped = 0;
		for (Algorithm algorithm : algorithms) {
			SubsystemCount count = subsystemMap.get(algorithm.getSubsystem());
			if (count == null) {
				count = new SubsystemCount();
				count.setSubsystem(algorithm.getSubsystem());
				subsystemMap.put(algorithm.getSubsystem(), count);
			}
			count.increTotal();
			if (algorithm.getStatus() != null) {
				if (algorithm.getStatus().equals(RunStatus.Run)) {
					run++;
					count.increRun();
				} else if (algorithm.getStatus().equals(RunStatus.Err)) {
					error++;
				} else if (algorithm.getStatus().equals(RunStatus.Stop)) {
					stopped++;
				}
			}
		}
		response.setRunning(run);
		response.setError(error);
		response.setStopped(stopped);
		response.setSubsystems(new ArrayList<>(subsystemMap.values()));
		return response;
	}

	@Override
	public AlgorithmSetting getSetting(int code) {
		return query.getAlgorithmSetting(code);
	}

	/**
	 * @see com.hirain.phm.synapsis.algorithm.service.AlgorithmService#getRecords()
	 */
	@Override
	public List<Record> getRecords() {
		return recordMapper.getRecords();
	}

	/**
	 * @see com.hirain.phm.synapsis.algorithm.service.AlgorithmService#updateStatus(com.hirain.phm.synapsis.algorithm.domain.RunStatus)
	 */
	@Override
	public void updateStatus(RunStatus status) {
		if (algorithms != null) {
			for (Algorithm algorithm : algorithms) {
				algorithm.setStatus(status);
			}
		}
	}
}
