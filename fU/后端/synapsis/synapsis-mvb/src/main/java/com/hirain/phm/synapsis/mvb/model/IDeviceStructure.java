/*******************************************************************************
 * Copyright (c) 2017, 2017 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.mvb.model;

import java.util.List;

/**
 * @Version 1.0
 * @Author gangjie.yang@hirain.com
 * @Created May 10, 2017 3:42:29 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               May 10, 2017 gangjie.yang@hirain.com 1.0 create file
 */
public interface IDeviceStructure extends IStandardModel, IStructure, IPropertyChangeSupport {

	short getAddress();

	void setAddress(short address);

	String getCar();

	void setCar(String car);

	String getSystem();

	void setSystem(String system);

	List<IPortStructure> getChildren();

	// ------------------------------------

	List<IPortStructure> getPortStructureSource();

	List<IPortStructure> getPortStructureSink();

	IRoot getParent();

	void setParent(IRoot parent);

	IDevice getDevice();

	/**
	 * 会同步更新structure绑定的地址
	 *
	 * @param device
	 */
	void setDevice(IDevice device);

}
