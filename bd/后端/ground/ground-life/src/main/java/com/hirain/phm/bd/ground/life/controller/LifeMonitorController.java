/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.life.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirain.phm.bd.ground.common.model.ResponseBo;
import com.hirain.phm.bd.ground.life.param.LifeMonitorRequest;
import com.hirain.phm.bd.ground.life.service.LifeService;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created May 23, 2019 2:02:28 PM
 * @Description
 *              <p>
 *              寿命监控对应的controller
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               May 23, 2019 zepei.tao@hirain.com 1.0 create file
 */
@Api(value = "寿命监控controller", tags = { "寿命监控操作接口" })
@RestController
@RequestMapping(value = "lifemonitor")
@Slf4j
public class LifeMonitorController {

	@Autowired
	private LifeService lifeService;

	/**
	 * 查询车门各项参数寿命
	 */
	@GetMapping("/findItemLifeByDoor")
	public ResponseBo findItemLifeByDoor(LifeMonitorRequest request) {
		try {
			return ResponseBo.ok(lifeService.findItemLifeByDoor(request));
		} catch (Exception e) {
			log.error(e.getMessage());
			return ResponseBo.error("系统异常");
		}
	}

}
