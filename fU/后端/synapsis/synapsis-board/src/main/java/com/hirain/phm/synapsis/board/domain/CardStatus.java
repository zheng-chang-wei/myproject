/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.board.domain;

import lombok.Getter;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec 3, 2019 11:09:02 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 3, 2019 zepei.tao@hirain.com 1.0 create file
 */
public enum CardStatus {
	IDLE(0, "空闲"),

	WORKING(1, "正常运行"),

	STOPPED(2, "停止运行"),

	ERROR(3, "异常运行"),

	OFFLINE(4, "离线");

	@Getter
	private int statusNo;

	@Getter
	private String event;

	private CardStatus(int statusNo, String event) {
		this.statusNo = statusNo;
		this.event = event;
	}

}
