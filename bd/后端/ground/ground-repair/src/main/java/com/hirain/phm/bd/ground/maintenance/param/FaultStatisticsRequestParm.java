package com.hirain.phm.bd.ground.maintenance.param;

import lombok.Data;

@Data
public class FaultStatisticsRequestParm {

	private Integer projectId;

	/**
	 * 车门种类
	 */
	private Integer doorTypeId;

	/**
	 * 运营影响
	 */
	private Integer effectId;

	/**
	 * 故障发生阶段
	 */
	private Integer stageId;

	/**
	 * 故障模式
	 */
	private Integer modeId;

	private String[] times;

	private String startTime;

	private String endTime;

	public String getStartTime() {
		if (times != null) {
			startTime = times[0];
			return startTime;
		}
		return null;
	}

	public String getEndTime() {
		if (times != null) {
			endTime = times[1];
			return endTime;
		}
		return null;
	}

}