/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.board.domain;

import lombok.Getter;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec 3, 2019 11:52:40 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 3, 2019 zepei.tao@hirain.com 1.0 create file
 */
public enum DataStatus {
	BE_DATA(0, "有数据"), NO_DATA(1, "无数据");

	@Getter
	private int dataStatus;

	@Getter
	private String description;

	private DataStatus(int dataStatus, String description) {
		this.dataStatus = dataStatus;
		this.description = description;
	}

}
