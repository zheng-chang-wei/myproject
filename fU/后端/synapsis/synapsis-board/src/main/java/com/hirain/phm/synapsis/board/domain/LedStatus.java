/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.board.domain;

import lombok.Getter;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Feb 25, 2020 5:28:45 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Feb 25, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public enum LedStatus {

	OFF("none", "off"),

	GREEN_ON("green", "on"),

	GREEN_FLASHING("green", "flashing"),

	ORANGE_ON("orange", "on"),

	ORANGE_FLASHING("orange", "flashing"),

	RED_ON("red", "on"),

	RED_FLASHING("red", "flashing"),

	NULL(null, null);

	@Getter
	private String color;

	@Getter
	private String status;

	LedStatus(String color, String status) {
		this.color = color;
		this.status = status;
	}

	public static LedStatus value(int b) {
		if (b < LedStatus.values().length) {
			return LedStatus.values()[b];
		} else {
			return LedStatus.NULL;
		}
	}
}
