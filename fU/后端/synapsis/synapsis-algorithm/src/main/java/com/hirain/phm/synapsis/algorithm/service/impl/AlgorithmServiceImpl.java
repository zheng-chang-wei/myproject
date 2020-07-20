/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.algorithm.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import com.hirain.phm.synapsis.board.BoardQuery;
import com.hirain.phm.synapsis.board.IBoard;
import com.hirain.phm.synapsis.page.QueryRequest;
import com.hirain.phm.synapsis.response.PageResultBean;
import com.hirain.phm.synapsis.setting.AlgorithmSetting;
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
	private BoardQuery boardQuery;

	@Autowired
	private RecordMapper recordMapper;

	@Override
	public void init(List<AlgorithmSetting> settings) {
		algorithms.clear();
		List<IBoard> boards = boardQuery.getBoards("PHM");
		Map<Integer, IBoard> map = boards.stream().collect(Collectors.toMap(b -> b.getSlotID(), b -> b));
		for (AlgorithmSetting setting : settings) {
			Algorithm algorithm = new Algorithm();
			algorithm.setCode(setting.getCode());
			algorithm.setName(setting.getName());
			algorithm.setSubsystem(setting.getSubsystem());
			algorithm.setSlotId(setting.getSlotId());
			IBoard board = map.get(algorithm.getSlotId());
			if (board != null) {
				algorithm.setBoard(board.getBoardType().name() + "[" + algorithm.getSlotId() + "]");
			}
			algorithm.setEnable(setting.getEnable());
			algorithm.setStatus(RunStatus.Idle);
			algorithms.add(algorithm);
		}
	}

	/**
	 * @see com.hirain.phm.synapsis.algorithm.service.AlgorithmService#list(QueryRequest)
	 */
	@Override
	public PageResultBean<List<AlgorithmPacket>> list(QueryRequest request) {
		System.err.println(request);
		if (request == null || request.getPageNum() == 0 && request.getPageSize() == 0) {
			request = new QueryRequest();
			request.setPageSize(algorithms.size());
			request.setPageNum(1);
		}
		int min = request.getPageSize() * (request.getPageNum() - 1) + 1;
		int max = request.getPageSize() * request.getPageNum();
		List<Algorithm> list = algorithms.stream().filter(a -> a.getCode() >= min && a.getCode() <= max).collect(Collectors.toList());
		List<AlgorithmPacket> packets = new ArrayList<>();
		for (Algorithm algorithm : list) {
			AlgorithmPacket packet = new AlgorithmPacket();
			packet.setData(algorithm);
			packets.add(packet);
		}
		PageResultBean<List<AlgorithmPacket>> result = new PageResultBean<>(packets, algorithms.size());
		return result;
	}

	@Override
	public void update(int slotId, Map<Integer, RunStatus> statusMap) {
		List<Algorithm> list = algorithms.stream().filter(a -> a.getSlotId() == slotId).collect(Collectors.toList());
		for (Algorithm algorithm : list) {
			RunStatus status = statusMap.get(algorithm.getCode());
			if (!isEqual(status, algorithm.getStatus())) {
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
