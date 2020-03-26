/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.algorithm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirain.phm.synapsis.algorithm.param.AlgorithmPacket;
import com.hirain.phm.synapsis.algorithm.param.AlgorithmStatisticsResponse;
import com.hirain.phm.synapsis.algorithm.param.Record;
import com.hirain.phm.synapsis.algorithm.service.AlgorithmService;
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

	@GetMapping("/list")
	public ResultBean<List<AlgorithmPacket>> list() {
		return new ResultBean<>(service.list());
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
}
