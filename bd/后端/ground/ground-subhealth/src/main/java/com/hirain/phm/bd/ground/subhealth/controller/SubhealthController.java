/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/

package com.hirain.phm.bd.ground.subhealth.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
import com.hirain.phm.bd.ground.subhealth.param.SubhealthDetailParams;
import com.hirain.phm.bd.ground.subhealth.param.SubhealthDetailResponseParams;
import com.hirain.phm.bd.ground.subhealth.service.SubhealthDataService;
import com.hirain.phm.bd.ground.subhealth.service.SubhealthInfoService;
import com.hirain.phm.bd.ground.subhealth.service.SubhealthQueryService;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

/**
 * @Version 1.0
 * @Author weijia.kong@hirain.com
 * @Created May 30, 2019 4:31:18 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               May 30, 2019 weijia.kong@hirain.com 1.0 create file
 */
@Api(value = "Subhealth Controller", tags = { "亚健康操作接口" })
@RestController
@RequestMapping("/bode/subhealth")
@Slf4j
public class SubhealthController {

	@Autowired
	private SubhealthQueryService queryService;

	@Autowired
	private SubhealthInfoService infoService;

	@Autowired
	private SubhealthDataService dataService;

	@Autowired
	private PageService pageService;

	@GetMapping("/subhealthInfo")
	public ResponseBo getSubhealthInfoByProject(String project) {
		try {
			return ResponseBo.ok(infoService.selectByProject(project));
		} catch (Exception e) {
			log.error("获取亚健康列表失败", e);
			return ResponseBo.error("获取亚健康列表失败");
		}
	}

	@GetMapping("/list")
	public ResponseBo findSubhealthDetailsByParams(QueryRequest request, SubhealthDetailParams subhealthDetailParams) {
		try {
			Map<String, Object> pageInfo = pageService.selectByPageNumSize(request, () -> queryService.selectByParams(subhealthDetailParams));
			@SuppressWarnings("unchecked")
			List<SubhealthDetailResponseParams> details = (List<SubhealthDetailResponseParams>) pageInfo.get("rows");
			queryService.getRepairAndSolution(details);
			pageInfo.put("rows", details);
			return ResponseBo.ok(pageInfo);
		} catch (Exception e) {
			log.error("亚健康记录查询失败", e);
			return ResponseBo.error("亚健康记录查询失败");
		}
	}

	@GetMapping("/subhealthData")
	public ResponseBo getSubhealthData(Integer id, String[] variables) {
		return SafeRunner.run(() -> {
			try {
				return ResponseBo.ok(dataService.getSubhealthData(id, Arrays.asList(variables)));
			} catch (Exception e) {
				return ResponseBo.error(e.getMessage());
			}
		}, ResponseBo.error("查询失败"));
	}

	@GetMapping("/digitalData")
	public ResponseBo getDigitalData(Integer id, String[] variables) {
		return SafeRunner.run(() -> {
			try {
				return ResponseBo.ok(dataService.getDigitalData(id, Arrays.asList(variables)));
			} catch (Exception e) {
				return ResponseBo.error(e.getMessage());
			}
		}, ResponseBo.error("查询失败"));
	}

	@GetMapping("/dashboard/today/bode")
	public ResponseBo selectToday() {
		return SafeRunner.run(() -> ResponseBo.ok(queryService.selectToday()), ResponseBo.error("查询失败"));
	}

	@GetMapping("/dashboard/annual")
	public ResponseBo selectYearCounts(int year) {
		return SafeRunner.run(() -> ResponseBo.ok(queryService.selectYearCounts(year)), ResponseBo.error("查询失败"));
	}

	@GetMapping("/dashboard/month")
	public ResponseBo selectMonthCounts(int year, int month) {
		return SafeRunner.run(() -> ResponseBo.ok(queryService.selectMonthCounts(year, month)), ResponseBo.error("查询失败"));
	}

	@GetMapping("/owner/dashboard/annual")
	public ResponseBo selectAnnualCount(int year) {
		return SafeRunner.run(() -> ResponseBo.ok(queryService.selectAnnualCount(year)), ResponseBo.error("查询失败"));
	}

	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request) {
		// 转换日期
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));// CustomDateEditor为自定义日期编辑器
	}

}
