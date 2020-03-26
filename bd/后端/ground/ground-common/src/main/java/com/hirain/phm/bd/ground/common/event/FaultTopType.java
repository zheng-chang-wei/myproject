/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.common.event;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年7月17日 上午10:15:01
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年7月17日 jianwen.xin@hirain.com 1.0 create file
 */
public enum FaultTopType {

	Fault(0, "故障"),

	SubHealth(1, "亚健康预警"),

	Life(2, "寿命预警"),

	External(3, "外部故障");

	private int code;

	private String name;

	FaultTopType(int code, String name) {
		this.code = code;
		this.name = name;
	}

	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	public String getName() {
		return name;
	}
}
