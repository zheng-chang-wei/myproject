/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.algorithm.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirain.phm.synapsis.algorithm.param.AlgorithmPacket;
import com.hirain.phm.synapsis.algorithm.param.AlgorithmStatisticsResponse;
import com.hirain.phm.synapsis.algorithm.param.Record;
import com.hirain.phm.synapsis.algorithm.service.AlgorithmService;
import com.hirain.phm.synapsis.board.BoardQuery;
import com.hirain.phm.synapsis.board.IBoard;
import com.hirain.phm.synapsis.page.QueryRequest;
import com.hirain.phm.synapsis.response.ResultBean;
import com.hirain.phm.synapsis.setting.AlgorithmSetting;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 16, 2019 5:09:42 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 16, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@RestController
@RequestMapping("/algorithm")
public class AlgorithmController {

	@Autowired
	private AlgorithmService service;

	@Autowired
	private BoardQuery boardQuery;

	@GetMapping("/list")
	public ResultBean<List<AlgorithmPacket>> list(QueryRequest request) {
		return service.list(request);
	}

	@GetMapping("/count")
	public ResultBean<AlgorithmStatisticsResponse> count() {
		return new ResultBean<>(service.count());
	}

	@GetMapping("/setting")
	public ResultBean<AlgorithmSetting> getSetting(int code) {
		return new ResultBean<>(service.getSetting(code));
	}

	@GetMapping("/history")
	public ResultBean<List<Record>> getRecords() {
		return new ResultBean<>(service.getRecords());
	}

	@GetMapping("/phmboards")
	public ResultBean<List<String>> getPhmBoards() {
		List<IBoard> boards = boardQuery.getBoards("PHM");
		List<String> list = boards.stream().map(b -> b.getBoardType().name() + "[" + b.getSlotID() + "]").collect(Collectors.toList());
		return new ResultBean<>(list);
	}
}
