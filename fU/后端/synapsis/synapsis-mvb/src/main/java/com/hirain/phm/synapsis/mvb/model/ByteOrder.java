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
public enum ByteOrder {

	/**
	 * 高字节保存到低地址(左面的是高位值)
	 * e684 6c4e 0100 1800前四个字节代表一个int
	 * 大端: e6 84 6c 4e
	 */
	BIG_ENDIAN("大端"),

	/**
	 * 高字节保存到高地址(左面的是低位值)
	 * e684 6c4e 0100 1800前四个字节代表一个int
	 * 小端：4e 6c 84 e6
	 */
	LITTLE_ENDIAN("小端");

	private String name;

	/**
	 * @param name
	 */
	private ByteOrder(final String name) {
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

}
