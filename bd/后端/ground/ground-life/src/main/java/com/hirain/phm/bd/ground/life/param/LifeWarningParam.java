/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.life.param;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created May 29, 2019 4:38:20 PM
 * @Description
 *              <p>
 *              对应寿命预警查询页面所需要的参数属性
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               May 29, 2019 zepei.tao@hirain.com 1.0 create file
 */
@Data
public class LifeWarningParam implements Serializable {

	/**
	* 
	*/
	private static final long serialVersionUID = 7998060904439869870L;

	/**
	 * 该预警在数据表中的id
	 */
	@ApiModelProperty(" 该预警在数据表中的id")
	private Integer id;

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
	private Integer doorAddr;

	/**
	 * 时间范围之起始时间
	 */
	@ApiModelProperty("时间范围之起始时间")
	private Date startTime;

	/**
	 * 时间范围之结束时间
	 */
	@ApiModelProperty("时间范围之结束时间")
	private Date endTime;

	/**
	 * 寿命预警发生的时间
	 */
	@ApiModelProperty("寿命预警发生的时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date warningTime;

	/**
	 * 寿命项点ID
	 */
	@ApiModelProperty("寿命项点ID")
	private Integer lifeItemId;

	/**
	 * 寿命项点名称
	 */
	@ApiModelProperty("寿命项点名称")
	private String lifeItemName;

	/**
	 * 剩余寿命百分比值
	 */
	@ApiModelProperty("剩余寿命百分比值")
	private Double remainderLife;

	/**
	 * 是否调试
	 */
	@ApiModelProperty("是否调试")
	private Boolean debugMode;

}
