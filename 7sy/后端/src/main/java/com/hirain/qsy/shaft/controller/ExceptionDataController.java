package com.hirain.qsy.shaft.controller;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hirain.qsy.shaft.common.model.ResponseBo;
import com.hirain.qsy.shaft.service.ExceptionDataService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/exceptiondata")
@Slf4j
public class ExceptionDataController {

	@Autowired
	ExceptionDataService exceptionDataService;

	@GetMapping("/getdata")
	@RequiresAuthentication
	public ResponseBo getExceptionStateData(String trainType, String trainNum, String startDate, String endDate) {
		try {
			return ResponseBo.ok(exceptionDataService.analyseExceptionData(trainType, trainNum, startDate, endDate));
		} catch (Exception e) {
			log.error("获取异常信息失败", e);
			return ResponseBo.error("获取异常信息失败！");
		}
	}

	@GetMapping("/getAxledata")
	@RequiresAuthentication
	public ResponseBo getAxleExceptionData(String trainType, String trainNum, String startDate, String endDate, @RequestParam String axleName) {
		try {
			return ResponseBo.ok(exceptionDataService.findAxleExceptionData(trainType, trainNum, startDate, endDate, axleName));
		} catch (Exception e) {
			log.error("获取异常信息失败", e);
			return ResponseBo.error("获取异常信息失败！");
		}
	}

}
