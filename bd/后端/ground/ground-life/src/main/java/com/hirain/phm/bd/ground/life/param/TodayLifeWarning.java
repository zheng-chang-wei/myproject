package com.hirain.phm.bd.ground.life.param;

import java.util.Date;

import lombok.Data;

@Data
public class TodayLifeWarning {

	/**
	 * 车厢号
	 */
	private String carNo;

	/**
	 * 门地址
	 */
	private String doorAddr;

	/**
	 * 部件名称
	 */
	private String itemName;

	/**
	 * 预警时间
	 */
	private Date warningTime;

	/**
	 * 剩余寿命，值为小数，例如0.12
	 */
	private Double remainderLife;

	/**
	 * 总寿命
	 */
	private Integer referenceValue;
}
