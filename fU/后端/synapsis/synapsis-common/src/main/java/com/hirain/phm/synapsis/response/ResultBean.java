/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.response;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 2, 2019 1:41:43 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 2, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Data
@NoArgsConstructor
public class ResultBean<T> {

	public static final int SUCCESS = 0;

	public static final int FAIL = 500;

	public static final int NO_PERMISSION = 401;

	private String msg;

	private int code = SUCCESS;

	private T data;

	public ResultBean(T data) {
		super();
		this.data = data;
	}

	public ResultBean(String msg, Throwable e) {
		super();
		this.msg = msg;
		this.code = FAIL;
	}
}
