/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.algorithm.domain;

import lombok.Getter;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 16, 2019 5:07:34 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 16, 2019 jianwen.xin@hirain.com 1.0 create file
 */
public enum RunStatus {

	Idle("空闲"),

	Run("正常运行"),

	Stop("停止运行"),

	Err("异常运行");

	@Getter
	private String event;

	/**
	 * 
	 */
	private RunStatus(String event) {
		this.event = event;
	}
}
