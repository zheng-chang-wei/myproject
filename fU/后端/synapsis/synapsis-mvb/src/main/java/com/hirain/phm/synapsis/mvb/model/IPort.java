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
public interface IPort extends IStandardModel {

	short getAddress();

	void setAddress(final short address);

	/**
	 * @return unit:bit
	 */
	int getSize();

	/**
	 * @param size
	 *            unit:bit
	 */
	void setSize(final int size);

	int getFCode();

	void setFCode(final int fCode);

	double getCircle();

	void setCircle(final double circle);

	int getTimeSlot();

	void setTimeSlot(final int timeSlot);

	String getName();

	void setName(String name);

}
