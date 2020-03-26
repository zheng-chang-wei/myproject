/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.qsy.shaft.model;

import java.io.Serializable;

import lombok.Data;

/**
 * @Version 1.0
 * @Author changwei.zheng@hirain.com
 * @Created 2019年9月24日 上午9:46:27
 * @Description
 *              <p>
 *              首页展示数据
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年9月24日 changwei.zheng@hirain.com 1.0 create file
 */
@Data
public class HomeVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6079443572591566140L;

	/**
	 * 车型
	 */
	private String trainType;

	/**
	 * 车号
	 */
	private String trainNum;

	/**
	 * 最近一次异常时间
	 */
	private String lastExceptionDate;

	private String endTime;

	/**
	 * 最近一个月异常次数
	 */
	private int exceptionCount;
}
