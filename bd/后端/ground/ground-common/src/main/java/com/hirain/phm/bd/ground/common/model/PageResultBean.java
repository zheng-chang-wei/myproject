/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.common.model;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年6月6日 下午3:57:05
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年6月6日 jianwen.xin@hirain.com 1.0 create file
 */
@Data
@NoArgsConstructor
public class PageResultBean<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final int SUCCESS = 0;

	public static final int FAIL = 500;

	public static final int NO_PERMISSION = 2;

	private String msg = "success";

	private int code = SUCCESS;

	private List<T> data;

	private long total;

	/**
	 * @param data
	 * @param total
	 */
	public PageResultBean(List<T> data, long total) {
		super();
		this.data = data;
		this.total = total;
	}

	public PageResultBean(Throwable e) {
		super();
		this.msg = e.getMessage();
		this.code = FAIL;
	}
}
