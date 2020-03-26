/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.flow.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年5月28日 下午3:22:46
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年5月28日 jianwen.xin@hirain.com 1.0 create file
 */
@Data
@NoArgsConstructor
public class ResultBean {

	private String processId;

	private String next;

	/**
	 * @param processId
	 */
	public ResultBean(String processId) {
		this.processId = processId;
	}

	/**
	 * @param processId
	 * @param next
	 */
	public ResultBean(String processId, String next) {
		this.processId = processId;
		this.next = next;
	}
}
