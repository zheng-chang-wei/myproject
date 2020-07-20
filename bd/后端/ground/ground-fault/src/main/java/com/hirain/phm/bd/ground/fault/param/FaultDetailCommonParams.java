package com.hirain.phm.bd.ground.fault.param;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FaultDetailCommonParams implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3281056506911326095L;

	private String project;

	/**
	 * 列车编号
	 */
	@ApiModelProperty("列车编号")
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
	 * 故障名称
	 */
	@ApiModelProperty("故障名称")
	private String faultName;

	/**
	 * 故障代码
	 */
	@ApiModelProperty("故障代码")
	private String faultCode;

	/**
	 * 列车模式，false表示正常模式，true表示调试模式
	 */
	@ApiModelProperty("列车模式")
	private Boolean debugMode;

	private Boolean statistics;

}
