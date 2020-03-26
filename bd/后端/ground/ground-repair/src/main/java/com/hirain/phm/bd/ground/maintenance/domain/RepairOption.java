/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.maintenance.domain;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年7月17日 上午9:58:30
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年7月17日 jianwen.xin@hirain.com 1.0 create file
 */
public enum RepairOption {

	None("无权限"),

	Create("创建工单"),

	AfterSale("售后审核"),

	Quality("质量审核"),

	Invest("质量调查"),

	Resolve("问题解决"),

	Track("跟踪"),

	Close("关闭");

	private String desc;

	/**
	 * 
	 */
	private RepairOption(String desc) {
		this.desc = desc;
	}

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}
}
