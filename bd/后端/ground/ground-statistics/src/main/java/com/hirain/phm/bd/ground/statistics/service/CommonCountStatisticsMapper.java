package com.hirain.phm.bd.ground.statistics.service;

import java.util.List;

import com.hirain.phm.bd.ground.maintenance.param.CommonStatisticsResult;

public interface CommonCountStatisticsMapper {

	List<CommonStatisticsResult> statisticsCountByProjectName(String projectName, String year);
}
