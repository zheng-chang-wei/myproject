package com.hirain.phm.bd.ground.statistics.param;

import lombok.Data;

@Data
public class StatisticsParm {

	private String[] trains;

	private Integer start_month;

	private Integer end_month;

	private Integer year;
}
