/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.update.domain;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec Jul 17, 2020 4:15:39 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jul 17, 2020 zepei.tao@hirain.com 1.0 create file
 */
public enum Schedule {

	READY(0, "未开始"),

	DOING(1, "更新中"),

	DONE(2, "完成"),

	FAULT(3, "失败");

	@Getter
	private int code;

	@Getter
	@JsonValue
	private String message;

	private Schedule(int code, String message) {
		this.code = code;
		this.message = message;
	}

}
