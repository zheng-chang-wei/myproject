package com.hirain.phm.bd.ground.statistics.domain;

import lombok.Data;

@Data
public class StatisticsParam {

	private String[] trains;

	private Integer start_month;

	private Integer end_month;

	private Integer year;
}
