/*******************************************************************************
 * Copyright (c) 2017, 2017 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.mvb.model;

/**
 * @Version 1.0
 * @Author gangjie.yang@hirain.com
 * @Created May 9, 2017 2:49:15 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               May 9, 2017 gangjie.yang@hirain.com 1.0 create file
 */
public interface IDataType extends IStandardModel {

	String getBaseType();

	void setBaseType(final String baseType);

	String getName();

	/**
	 * @param name
	 *            不能重复
	 */
	void setName(final String name);

	String getRange();

	void setRange(final String range);

	/**
	 * @return bit
	 */
	int getLength();

	/**
	 * @param length
	 *            bit
	 */
	void setLength(final int length);
}
