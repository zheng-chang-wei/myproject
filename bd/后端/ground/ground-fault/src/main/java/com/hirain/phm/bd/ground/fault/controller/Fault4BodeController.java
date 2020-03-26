/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.fault.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirain.phm.bd.ground.common.exception.SafeRunner;
import com.hirain.phm.bd.ground.common.model.ResponseBo;
import com.hirain.phm.bd.ground.fault.service.FaultDetailService;

import io.swagger.annotations.Api;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec Feb 28, 2020 3:26:11 PM
 * @Description
 *              <p>  博得首页查询当天故障预警信息
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Feb 28, 2020     zepei.tao@hirain.com    1.0   create file   
 */
@Api(value = "故障controller", tags = { "故障操作接口" })
@RestController
@RequestMapping("/bode/fault")
public class Fault4BodeController {

	@Autowired
	private FaultDetailService faultDetailService;

	@GetMapping("/dashboard/today/bode")
	public ResponseBo selectToday() {
		return SafeRunner.run(() -> ResponseBo.ok(faultDetailService.selectToday()), ResponseBo.error("查询今日故障失败"));
	}

}
