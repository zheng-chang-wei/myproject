/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.constant;

import lombok.Getter;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 10, 2019 5:02:20 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 10, 2019 jianwen.xin@hirain.com 1.0 create file
 */
public enum ErrorCode {

	ERROR_SETTING(1 << 0);// bit1

	@Getter
	private int code;

	/**
	 * 
	 */
	private ErrorCode(int code) {
		this.code = code;
	}

}
