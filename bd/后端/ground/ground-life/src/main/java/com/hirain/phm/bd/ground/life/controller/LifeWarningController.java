/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.life.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.hirain.phm.bd.ground.common.exception.SafeRunner;
import com.hirain.phm.bd.ground.common.model.ResponseBo;
import com.hirain.phm.bd.ground.common.page.PageService;
import com.hirain.phm.bd.ground.common.page.QueryRequest;
import com.hirain.phm.bd.ground.life.param.LifeWarningParam;
import com.hirain.phm.bd.ground.life.service.LifeItemService;
import com.hirain.phm.bd.ground.life.service.LifeWarningService;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created May 28, 2019 3:03:54 PM
 * @Description
 *              <p>
 *              寿命预警Controller
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               May 28, 2019 zepei.tao@hirain.com 1.0 create file
 */
@Api(value = "寿命预警controller", tags = { "寿命预警操作接口" })
@RestController
@RequestMapping("/bode/lifewarning")
@Slf4j
public class LifeWarningController {

	@Autowired
	private PageService pageService;

	@Autowired
	private LifeWarningService lifeWarningService;

	@Autowired
	private LifeItemService lifeItemService;

	@GetMapping("/listLifeWarningInfoByParams")
	public ResponseBo listLifeWarinigInfo(QueryRequest request, LifeWarningParam lifeWarningParam) {
		try {
			return ResponseBo.ok(pageService.selectByPageNumSize(request, () -> lifeWarningService.findLifeWarningsByParam(lifeWarningParam)));
		} catch (Exception e) {
			log.error(e.getMessage());
			return ResponseBo.error("系统异常");
		}
	}

	@GetMapping("/getAllLifeItemNames")
	public ResponseBo getLifeItemNames() {
		try {
			return ResponseBo.ok(lifeItemService.selectAll());
		} catch (Exception e) {
			log.error("获取部件信息失败", e);
			return ResponseBo.error("获取部件信息失败");
		}
	}

	@GetMapping("/findLifeInfosByProject")
	public ResponseBo findLifeInfosByProject(String project) {
		return SafeRunner.run(() -> ResponseBo.ok(lifeWarningService.findLifeItems(project)), ResponseBo.error("获取部件信息失败"));
	}

	// @GetMapping("/listLifeWarningToday")
	// public ResponseBo listLifeWarningToday(String project, String trainNo) {
	// try {
	// return ResponseBo.ok(lifeWarningService.listLifeWarningToday(project, trainNo));
	// } catch (Exception e) {
	// log.error(e.getMessage());
	// return ResponseBo.error("系统异常");
	// }
	// }
	@GetMapping("/listLifeWarningToday")
	public ResponseBo listLifeWarningToday(String project, String trainNo) {
		try {
			return ResponseBo.ok(lifeWarningService.listLifeWarningToday(project, trainNo));
		} catch (Exception e) {
			log.error(e.getMessage());
			return ResponseBo.error("系统异常");
		}
	}

	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request) {
		// 转换日期
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));// CustomDateEditor为自定义日期编辑器
	}

}
