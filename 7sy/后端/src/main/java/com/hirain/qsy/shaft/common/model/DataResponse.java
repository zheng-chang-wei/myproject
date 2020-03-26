/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.qsy.shaft.common.model;

import com.hirain.qsy.shaft.model.TrainInfo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Version 1.0
 * @Author changwei.zheng@hirain.com
 * @Created 2019年3月28日 下午3:08:15
 * @Description
 *              <p>
 *              平台数据管理回复
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年3月28日 changwei.zheng@hirain.com 1.0 create file
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DataResponse extends TrainInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6239822110249174006L;

	private String earliestTime;

	private String latestTime;

}
