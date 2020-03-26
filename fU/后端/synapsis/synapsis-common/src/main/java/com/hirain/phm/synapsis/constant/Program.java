/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.constant;

import lombok.Getter;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 2, 2019 6:21:34 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 2, 2019 jianwen.xin@hirain.com 1.0 create file
 */
public enum Program {

	CPU_SERVICE(1),

	CPU_CONTROL(2);

	/**
	 * target编号
	 */
	@Getter
	private int code;

	/**
	 * @param code
	 */
	private Program(int code) {
		this.code = code;
	}

	public static String valueOf(int code) {
		Program[] values = Program.values();
		for (Program value : values) {
			if (value.getCode() == code) {
				return value.name();
			}
		}
		throw new NullPointerException("no code");
	}
}
