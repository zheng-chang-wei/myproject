/*******************************************************************************
 * Copyright (c) 2017, 2017 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.mvb.model;


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
public interface ISignalStructure extends IStandardModel, IStructure, IPropertyChangeSupport {

	ISignal getSignal();

	void setSignal(ISignal signal);

	IPortStructure getParent();

	void setParent(IPortStructure parent);

}
