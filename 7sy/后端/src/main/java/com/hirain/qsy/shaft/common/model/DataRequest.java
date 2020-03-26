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
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年3月28日 changwei.zheng@hirain.com 1.0 create file
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DataRequest extends TrainInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7436530745161297774L;

	private String startTime;

	private String endTime;

	@Override
	public String toString() {
		return "DataRequest [startTime=" + startTime + ", endTime=" + endTime + "]" + super.toString();
	}

}
