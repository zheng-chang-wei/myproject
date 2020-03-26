/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.constant;

import lombok.Getter;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 3, 2019 9:37:30 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 3, 2019 jianwen.xin@hirain.com 1.0 create file
 */
public enum BoardType {

	CPU("CPU"),

	PHM_IMX8("PHM"),

	PHM_TX2("PHM"),

	PHM_AGX("PHM"),

	PHM_MVB("PHM"),

	ECN("ECN"),

	MVB("MVB"),

	AD1("AD"),

	AD2("AD"),

	AD3("AD"),

	WIRELESS("无线"),

	SSD("SSD"),

	POWER("电源");

	@Getter
	private String type;

	/**
	 * @param type
	 */
	private BoardType(String type) {
		this.type = type;
	}

}
