/*******************************************************************************
 * Copyright (c) 2017, 2017 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.mvb.model;

/**
 * @Version 1.0
 * @Author gangjie.yang@hirain.com
 * @Created May 19, 2017 2:18:04 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               May 19, 2017 gangjie.yang@hirain.com 1.0 create file
 */
public enum BitOrder {

	/**
	 * 例如： 0x40 [0100 0000]：布尔值1所在的位偏移量为1，从左向右查
	 */
	BIG_ENDIAN("大端"),

	/**
	 * 例如： 0x40 [0100 0000]：布尔值1所在的位偏移量为6，从右向左查
	 */
	LITTLE_ENDIAN("小端");

	private String name;

	/**
	 * @param name
	 */
	private BitOrder(final String name) {
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

}
