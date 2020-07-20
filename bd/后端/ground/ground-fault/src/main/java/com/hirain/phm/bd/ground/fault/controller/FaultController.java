package com.hirain.phm.bd.ground.fault.controller;

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
import com.hirain.phm.bd.ground.fault.param.FaultDetailRequestParams;
import com.hirain.phm.bd.ground.fault.param.FaultDetailResponseParams;
import com.hirain.phm.bd.ground.fault.service.FaultDataService;
import com.hirain.phm.bd.ground.fault.service.FaultDetailService;
import com.hirain.phm.bd.ground.fault.service.FaultInfoService;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

/**
 * @Version 1.0
 * @Author changwei.zheng@hirain.com
 * @Created 2019年5月6日 下午5:30:13
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年5月6日 changwei.zheng@hirain.com 1.0 create file
 */
@Api(value = "故障controller", tags = { "故障操作接口" })
@RestController
@RequestMapping("/bode/fault")
@Slf4j
public class FaultController {

	@Autowired
	private FaultDetailService faultDetailService;

	@Autowired
	private FaultInfoService faultInfoService;

	@Autowired
	private PageService pageService;

	@Autowired
	private FaultDataService faultDataService;

	@GetMapping("/list")
	public ResponseBo findFaultDetailsByParams(QueryRequest request, FaultDetailRequestParams faultDetails) {
		try {
			Map<String, Object> pageObject = pageService.selectByPageNumSize(request,
					() -> faultDetailService.findFaultDetailsByParams(faultDetails));
			@SuppressWarnings("unchecked")
			List<FaultDetailResponseParams> list = (List<FaultDetailResponseParams>) pageObject.get("rows");
			faultDetailService.getRepairAndSolution(list);
			pageObject.put("rows", list);
			return ResponseBo.ok(pageObject);
		} catch (Exception e) {
			log.error(e.getMessage());
			return ResponseBo.error("系统异常");
		}
	}

	/**
	 * @return
	 */
	@GetMapping("/getAllFaultNames")
	public ResponseBo getFaultNames() {
		try {
			return ResponseBo.ok(faultInfoService.selectAllGroupByFaultName());
		} catch (Exception e) {
			log.error("获取故障信息失败", e);
			return ResponseBo.error("获取故障信息失败");
		}
	}

	@GetMapping("/faultInfo")
	public ResponseBo findFaultInfoByProject(String project) {
		return SafeRunner.run(() -> ResponseBo.ok(faultInfoService.findFaultInfoByProject(project)), ResponseBo.error("获取故障信息失败"));
	}

	@GetMapping("/faultData")
	public ResponseBo getFaultData(Long id, String[] variables) {
		return SafeRunner.run(() -> {
			try {
				return ResponseBo.ok(faultDataService.getFaultData(id, Arrays.asList(variables)));
			} catch (Exception e) {
				return ResponseBo.error(e.getMessage());
			}
		}, ResponseBo.error("查询失败"));
	}

	@GetMapping("/digitalData")
	public ResponseBo getDigitalData(Long id, String[] variables) {
		return SafeRunner.run(() -> {
			try {
				return ResponseBo.ok(faultDataService.getDigitalData(id, Arrays.asList(variables)));
			} catch (Exception e) {
				return ResponseBo.error(e.getMessage());
			}
		}, ResponseBo.error("查询失败"));
	}

	@GetMapping("/dashboard/annual")
	public ResponseBo selectAnnualCounts(int year) {
		return SafeRunner.run(() -> ResponseBo.ok(faultDetailService.selectYearCounts(year)), ResponseBo.error("查询故障统计数据失败"));
	}

	@GetMapping("/dashboard/today/bode")
	public ResponseBo selectToday() {
		return SafeRunner.run(() -> ResponseBo.ok(faultDetailService.selectToday()), ResponseBo.error("查询今日故障失败"));
	}

	@GetMapping("/dashboard/month")
	public ResponseBo selectMonthCounts(int year, int month) {
		return SafeRunner.run(() -> ResponseBo.ok(faultDetailService.selectMonthCounts(year, month)), ResponseBo.error("查询失败"));
	}

	@GetMapping("/owner/dashboard/annual")
	public ResponseBo selectAnnualCount(int year) {
		return SafeRunner.run(() -> ResponseBo.ok(faultDetailService.selectAnnualCount(year)), ResponseBo.error("查询失败"));
	}

	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request) {
		// 转换日期
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));// CustomDateEditor为自定义日期编辑器
	}
}
