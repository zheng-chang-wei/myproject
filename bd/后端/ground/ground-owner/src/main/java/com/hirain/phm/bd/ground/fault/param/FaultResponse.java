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
 * @Created Jun 24, 2020 5:13:38 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jun 24, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Data
public class FaultResponse {

	/**
	 * 故障详情id
	 */
	private Long id;

	private String project;

	/**
	 * 列车编号
	 */
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
	 * 故障名称
	 */
	private String faultName;

	/**
	 * 故障代码
	 */
	private String faultCode;

	/**
	 * 列车模式，false表示正常模式，true表示调试模式
	 */
	private Boolean debugMode;

	private Boolean statistics;

	/**
	 * 故障发生时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date faultTime;

	private Integer level;

	private String suggestion;

	private String treatment;

	private String repair;

	private String description;

	private String solution;
}
