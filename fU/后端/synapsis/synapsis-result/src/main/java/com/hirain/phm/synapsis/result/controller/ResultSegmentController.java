/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.result.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirain.phm.synapsis.response.ResultBean;
import com.hirain.phm.synapsis.result.domain.ResultSegmentContainer;
import com.hirain.phm.synapsis.result.service.ResultSegmentService;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 22, 2020 3:44:42 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 22, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@RestController
@RequestMapping("/result/segment")
public class ResultSegmentController {

	@Autowired
	private ResultSegmentService service;

	@GetMapping("/header")
	public ResultBean<ResultSegmentContainer> getHeaderSegments() {
		return new ResultBean<>(service.getHeaderSegments());
	}

	@GetMapping("/data")
	public ResultBean<ResultSegmentContainer> getDataSegments() {
		return new ResultBean<>(service.getDataSegments());
	}
}
