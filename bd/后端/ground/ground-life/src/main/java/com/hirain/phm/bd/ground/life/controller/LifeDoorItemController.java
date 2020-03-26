/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.life.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirain.phm.bd.ground.common.model.ResponseBo;
import com.hirain.phm.bd.ground.life.service.LifeDoorItemService;

import lombok.extern.slf4j.Slf4j;

/**
 * @Version 1.0
 * @Author changwei.zheng@hirain.com
 * @Created 2020年1月8日 下午11:33:48
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2020年1月8日 changwei.zheng@hirain.com 1.0 create file
 */
@RestController
@RequestMapping("/bode/lifeDoorItem")
@Slf4j
public class LifeDoorItemController {


	@Autowired
	private LifeDoorItemService lifeDoorItemService;



	@GetMapping("/findDoorItemAVGValue")
	public ResponseBo findDoorItemAVGValue(String project, String trainNo) {
		try {
			return ResponseBo.ok(lifeDoorItemService.findDoorItemAVGValue(project, trainNo));
		} catch (Exception e) {
			log.error(e.getMessage());
			return ResponseBo.error("系统异常");
		}
	}





}
