/*******************************************************************************
 * Copyright (c) 2017, 2017 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.mvb.model;

import java.util.List;

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
public interface IDatagram extends IStandardModel {

	String getId();

	void setId(final String id);

	String getName();

	void setName(final String name);

	/**
	 * @return 大小端
	 */
	ByteOrder getByteOrder();

	/**
	 * @param byteOrder
	 *            大小端
	 */
	void setByteOrder(final ByteOrder byteOrder);

	/**
	 * @return 空闲位填充值
	 */
	int getIdleValue();

	/**
	 * @param idleValue
	 *            空闲位填充值
	 */
	void setIdleValue(final int idleValue);

	/**
	 * @return 在excel填写的excel页面名字
	 */
	String getSheetName();

	/**
	 * @param sheetName
	 *            在excel填写的excel页面名字
	 */
	void setSheetName(final String sheetName);

	List<ISignal> getSignals();

}
