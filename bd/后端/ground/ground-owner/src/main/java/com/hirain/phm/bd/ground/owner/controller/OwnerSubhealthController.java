/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.owner.controller;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirain.phm.bd.ground.common.exception.SafeRunner;
import com.hirain.phm.bd.ground.common.model.ResponseBo;
import com.hirain.phm.bd.ground.fault.param.FaultRequest;
import com.hirain.phm.bd.ground.fault.service.OwnerSubhealthService;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jun 23, 2020 4:19:57 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jun 23, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@RestController
@RequestMapping("/bode/subhealth")
public class OwnerSubhealthController {

	@Autowired
	private OwnerSubhealthService service;

	@GetMapping("/dashboard")
	public ResponseBo selectTop20(String project, String trainNo) {
		return SafeRunner.run(() -> ResponseBo.ok(service.selectTop20(project, trainNo)), ResponseBo.error("查询失败"));
	}

	@PostMapping("/export")
	public void export(@RequestBody FaultRequest request, HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment);filename=" + URLEncoder.encode("subhealth.xlsx", "UTF-8"));
		service.exportSubhealth(request, response.getOutputStream());
	}
}
