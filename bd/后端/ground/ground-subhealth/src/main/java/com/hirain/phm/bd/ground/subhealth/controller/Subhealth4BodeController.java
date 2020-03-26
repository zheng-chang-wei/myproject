/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.subhealth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirain.phm.bd.ground.common.exception.SafeRunner;
import com.hirain.phm.bd.ground.common.model.ResponseBo;
import com.hirain.phm.bd.ground.subhealth.service.SubhealthQueryService;

import io.swagger.annotations.Api;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec Feb 28, 2020 4:24:59 PM
 * @Description
 *              <p>博得首页查询当天亚健康预警信息
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Feb 28, 2020     zepei.tao@hirain.com    1.0   create file   
 */
@Api(value = "Subhealth Controller", tags = { "亚健康操作接口" })
@RestController
@RequestMapping("/bode/subhealth")
public class Subhealth4BodeController {

	@Autowired
	private SubhealthQueryService queryService;

	@GetMapping("/dashboard/today/bode")
	public ResponseBo selectToday() {
		return SafeRunner.run(() -> ResponseBo.ok(queryService.selectToday()), ResponseBo.error("查询失败"));
	}

}
