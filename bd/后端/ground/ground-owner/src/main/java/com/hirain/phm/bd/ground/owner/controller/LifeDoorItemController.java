/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
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

import com.hirain.phm.bd.ground.common.model.ResponseBo;
import com.hirain.phm.bd.ground.fault.param.FaultRequest;
import com.hirain.phm.bd.ground.life.OwnerLifeService;

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
	private OwnerLifeService lifeService;

	@GetMapping("/findDoorItemAVGValue")
	public ResponseBo findDoorItemAVGValue(String project, String trainNo) {
		try {
			return ResponseBo.ok(lifeService.findDoorItemAVGValue(project, trainNo));
		} catch (Exception e) {
			log.error(e.getMessage());
			return ResponseBo.error("系统异常");
		}
	}

	@PostMapping("/export")
	public void export(@RequestBody FaultRequest request, HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment);filename=" + URLEncoder.encode("fault.xlsx", "UTF-8"));
		lifeService.exportLifeItems(request, response.getOutputStream());
	}

}
