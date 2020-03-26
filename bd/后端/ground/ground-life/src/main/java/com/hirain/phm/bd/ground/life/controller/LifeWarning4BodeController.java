/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.life.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirain.phm.bd.ground.common.model.ResponseBo;
import com.hirain.phm.bd.ground.life.service.LifeWarningService;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec Feb 28, 2020 4:52:18 PM
 * @Description
 *              <p>博得首页查询当天寿命预警信息
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Feb 28, 2020     zepei.tao@hirain.com    1.0   create file   
 */
@Api(value = "寿命预警controller", tags = { "寿命预警操作接口" })
@RestController
@RequestMapping("/bode/lifewarning")
@Slf4j
public class LifeWarning4BodeController {

	@Autowired
	private LifeWarningService lifeWarningService;

	@GetMapping("/listLifeWarningToday/bode")
	public ResponseBo listLifeWarningToday() {
		try {
			return ResponseBo.ok(lifeWarningService.listLifeWarningToday());
		} catch (Exception e) {
			log.error(e.getMessage());
			return ResponseBo.error("系统异常");
		}
	}
}
