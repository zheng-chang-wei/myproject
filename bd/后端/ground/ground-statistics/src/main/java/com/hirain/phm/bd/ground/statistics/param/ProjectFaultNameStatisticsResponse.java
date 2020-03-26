package com.hirain.phm.bd.ground.statistics.param;

import java.util.List;

import com.hirain.phm.bd.ground.maintenance.param.CommonStatisticsResult;

import lombok.Data;

@Data
public class ProjectFaultNameStatisticsResponse {

	private String projectName;

	private Integer doorCount;

	private List<CommonStatisticsResult> commonStatisticsResultList;
}
