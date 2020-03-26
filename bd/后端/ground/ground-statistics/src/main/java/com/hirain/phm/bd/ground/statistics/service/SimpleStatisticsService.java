package com.hirain.phm.bd.ground.statistics.service;

import java.util.List;

import com.hirain.phm.bd.ground.statistics.domain.MonthStatisticsResult;
import com.hirain.phm.bd.ground.statistics.domain.TypeStatisticsResult;
import com.hirain.phm.bd.ground.statistics.domain.YearStatisticsResult;
import com.hirain.phm.bd.ground.statistics.param.StatisticsParm;

public interface SimpleStatisticsService {

	List<String> listTrains();

	List<MonthStatisticsResult> countByMonth(StatisticsParm param);

	YearStatisticsResult countByYear(StatisticsParm param);

	TypeStatisticsResult countByFaultType(StatisticsParm param);

	TypeStatisticsResult countBySubhealthType(StatisticsParm param);

	TypeStatisticsResult countByTopType(StatisticsParm param);

	List<String> getTrainId(String trainNo);

}
