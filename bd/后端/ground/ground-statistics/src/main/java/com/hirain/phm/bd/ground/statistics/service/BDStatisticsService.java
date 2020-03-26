package com.hirain.phm.bd.ground.statistics.service;

import java.util.List;

import com.hirain.phm.bd.ground.maintenance.param.CommonStatisticsResult;
import com.hirain.phm.bd.ground.maintenance.param.FaultStatisticsRequestParm;
import com.hirain.phm.bd.ground.statistics.param.ProjectFaultNameStatisticsResponse;

public interface BDStatisticsService {

	List<List<CommonStatisticsResult>> statisticsFaultCountByProjectName(String projectName);

	List<List<CommonStatisticsResult>> statisticsSubhealthCountByProjectName(String projectName);

	List<List<CommonStatisticsResult>> countProjectFaultByParms(FaultStatisticsRequestParm parm) throws Exception;

	List<CommonStatisticsResult> countPartsFaultByParms(FaultStatisticsRequestParm parm);

	List<ProjectFaultNameStatisticsResponse> countFaultNameByParms(FaultStatisticsRequestParm parm) throws Exception;

}
