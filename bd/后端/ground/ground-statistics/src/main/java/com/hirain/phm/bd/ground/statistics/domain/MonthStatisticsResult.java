package com.hirain.phm.bd.ground.statistics.domain;

import lombok.Data;

@Data
public class MonthStatisticsResult {

	private int year;

	private int month;

	/**
	 * 故障数量
	 */
	private int fault;

	/**
	 * 亚健康预警数量
	 */
	private int subhealth;

	/**
	 * 总数
	 */
	private int total;
}
