package com.hirain.phm.bd.ground.statistics.domain;

import java.util.List;

import lombok.Data;

@Data
public class YearStatisticsResult {

	private int year;

	private List<MonthStatisticsResult> results;
}
