/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 2, 2019 1:47:45 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 2, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PageResultBean<T> extends ResultBean<T> {

	private long total;

	/**
	 * @param data
	 * @param total
	 */
	public PageResultBean(T data, long total) {
		super(data);
		this.total = total;
	}

	public PageResultBean(Throwable e) {
		super(e.getMessage(), e);
	}
}
