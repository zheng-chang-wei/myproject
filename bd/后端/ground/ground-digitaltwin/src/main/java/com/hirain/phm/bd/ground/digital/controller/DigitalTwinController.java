/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.digital.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirain.phm.bd.ground.common.model.ResultBean;
import com.hirain.phm.bd.ground.digital.param.ResultQueryRequest;
import com.hirain.phm.bd.ground.digital.param.ResultQueryResponse;
import com.hirain.phm.bd.ground.digital.service.DigitalTwinService;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Sep 25, 2019 11:48:07 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Sep 25, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@RestController
@RequestMapping("/digitaltwin")
public class DigitalTwinController {

	@Autowired
	private DigitalTwinService service;

	@GetMapping("/all")
	public ResultBean<ResultQueryResponse> getAllResults(ResultQueryRequest request) {
		return new ResultBean<>(service.getAllResults(request));
	}

	@GetMapping("/type")
	public ResultBean<ResultQueryResponse> getResultsByType(ResultQueryRequest request, String type) {
		return new ResultBean<>(service.getResultsByType(request, type));
	}

	@GetMapping("/name")
	public ResultBean<ResultQueryResponse> getResultsByName(ResultQueryRequest request, String name) {
		return new ResultBean<>(service.getResultsByParamName(request, name));
	}
}
