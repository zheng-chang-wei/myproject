/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.algorithm.util;

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
public enum PHMProgram {

	PHM_SERVICE(3), // 算法壳

	PHM_CONTROL(4), // PHM控制管理程序（嵌入式）

	PHM_ALGO(9);// 算法

	@Getter
	private int code;

	/**
	 * @param code
	 */
	private PHMProgram(int code) {
		this.code = code;
	}

	public static PHMProgram getPHMProgram(int code) {
		for (PHMProgram phmProgram : PHMProgram.values()) {
			if (code == phmProgram.getCode()) {
				return phmProgram;
			}
		}
		return null;
	}

}
