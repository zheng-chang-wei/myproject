/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.fault.param;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jun 28, 2020 5:29:32 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jun 28, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Data
public class SubhealthResponse {

	private Long id;

	/**
	 * 查询时表示时间段起始
	 * 回复时表示预警开始时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date startTime;

	/**
	 * 查询时表示时间段终止
	 * 回复时表示预警结束时间
	 */
	private Date endTime;

	private String project;

	private String trainNo;

	/**
	 * 车厢号
	 */
	private Integer carNo;

	/**
	 * 门地址
	 */
	private String doorAddr;

	/**
	 * 亚健康名称
	 */
	private String subhealthName;

	/**
	 * 列车模式，false表示正常模式，true表示调试模式
	 */
	private Boolean debugMode;

	private String suggestion;

	private String treatment;

	private String repair;

	private String description;

	private String solution;
}
