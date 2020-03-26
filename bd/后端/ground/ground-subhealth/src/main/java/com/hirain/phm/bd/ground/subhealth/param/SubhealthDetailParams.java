/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
/**
 * @Version 1.0   
 * @Author weijia.kong@hirain.com
 * @Created May 24, 2019 3:43:36 PM
 * @Description
 * <p>
 * @Modification
 * <p>Date         Author      Version     Description  
 * <p>May 24, 2019     weijia.kong@hirain.com            1.0        create file 
 */
package com.hirain.phm.bd.ground.subhealth.param;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SubhealthDetailParams implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -445041017651780387L;

	@ApiModelProperty("亚健康记录ID")
	private Long id;

	/**
	 * 查询时表示时间段起始
	 * 回复时表示预警开始时间
	 */
	@ApiModelProperty("起始时间/开始时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date startTime;

	/**
	 * 查询时表示时间段终止
	 * 回复时表示预警结束时间
	 */
	@ApiModelProperty("终止时间/结束时间")
	private Date endTime;

	private String project;

	private String trainNo;

	/**
	 * 车厢号
	 */
	@ApiModelProperty("车厢号")
	private Integer carNo;

	/**
	 * 门地址
	 */
	@ApiModelProperty("门地址")
	private String doorAddr;

	/**
	 * 亚健康名称
	 */
	@ApiModelProperty("亚健康名称")
	private String subhealthName;

	/**
	 * 列车模式，false表示正常模式，true表示调试模式
	 */
	@ApiModelProperty("列车模式")
	private Boolean debugMode;

}
