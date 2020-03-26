/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.qsy.shaft.common.model;

import java.io.Serializable;

import lombok.Data;

/**
 * @Version 1.0
 * @Author changwei.zheng@hirain.com
 * @Created 2019年4月8日 上午11:35:16
 * @Description
 *              <p>
 *              异常统计请求
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年4月8日 changwei.zheng@hirain.com 1.0 create file
 */
@Data
public class StatisticsRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5739110898841138130L;

	/**
	 * 统计维度
	 */
	private Integer dimensionality;

	/**
	 * 车号
	 */
	private String num;

	private String startTime;

	private String endTime;

	/**
	 * 时间粒度
	 */
	private Integer granularityTime;

}
