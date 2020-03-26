package com.hirain.phm.bd.ground.maintenance.param;

import lombok.Data;

@Data
public class CommonStatisticsResult {

	private String x;

	private String y;

	public CommonStatisticsResult(String x, String y) {
		this.x = x;
		this.y = y;
	}

	public CommonStatisticsResult() {
	}

}
