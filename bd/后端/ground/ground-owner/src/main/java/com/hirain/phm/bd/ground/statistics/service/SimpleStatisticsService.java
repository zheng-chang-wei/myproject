package com.hirain.phm.bd.ground.statistics.service;

import java.util.List;

import com.hirain.phm.bd.ground.statistics.domain.MonthStatisticsResult;
import com.hirain.phm.bd.ground.statistics.domain.StatisticsParam;
import com.hirain.phm.bd.ground.statistics.domain.TypeStatisticsResult;
import com.hirain.phm.bd.ground.statistics.domain.YearStatisticsResult;

public interface SimpleStatisticsService {

	List<String> listTrains();

	List<MonthStatisticsResult> countByMonth(StatisticsParam param);

	YearStatisticsResult countByYear(StatisticsParam param);

	TypeStatisticsResult countByFaultType(StatisticsParam param);

	TypeStatisticsResult countBySubhealthType(StatisticsParam param);

	TypeStatisticsResult countByTopType(StatisticsParam param);

	List<String> getTrainId(String trainNo);

}
