package com.hirain.phm.bd.ground.statistics.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirain.phm.bd.ground.common.model.ResultBean;
import com.hirain.phm.bd.ground.statistics.domain.MonthStatisticsResult;
import com.hirain.phm.bd.ground.statistics.domain.TypeStatisticsResult;
import com.hirain.phm.bd.ground.statistics.domain.YearStatisticsResult;
import com.hirain.phm.bd.ground.statistics.param.StatisticsParm;
import com.hirain.phm.bd.ground.statistics.service.SimpleStatisticsService;

@RestController
@RequestMapping("/bode/statistics")
public class SimpleStatisticsController {

	@Autowired
	private SimpleStatisticsService service;

	@GetMapping("/trains")
	public ResultBean<List<String>> listTrains() {
		return new ResultBean<>(service.listTrains());
	}

	@GetMapping("/month")
	public ResultBean<List<MonthStatisticsResult>> statisticsByMonth(StatisticsParm param) {
		return new ResultBean<>(service.countByMonth(param));
	}

	@GetMapping("/year")
	public ResultBean<YearStatisticsResult> statisticsByYear(StatisticsParm param) {
		return new ResultBean<>(service.countByYear(param));
	}

	@GetMapping("/type/fault")
	public ResultBean<TypeStatisticsResult> statisticsByFaultType(StatisticsParm param) {
		return new ResultBean<>(service.countByFaultType(param));
	}

	@GetMapping("/type/subhealth")
	public ResultBean<TypeStatisticsResult> statisticsBySubhealthType(StatisticsParm param) {
		return new ResultBean<>(service.countBySubhealthType(param));
	}

	@GetMapping("/type/top")
	public ResultBean<TypeStatisticsResult> statisticsByTopType(StatisticsParm param) {
		return new ResultBean<>(service.countByTopType(param));
	}
}
