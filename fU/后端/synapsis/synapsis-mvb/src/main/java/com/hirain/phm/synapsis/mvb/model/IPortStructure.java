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
public interface IPortStructure extends IStandardModel, IStructure, IPropertyChangeSupport {

	short getAddress();

	void setAddress(short address);

	short getDeviceAddrSource();

	/**
	 * 同步更新structure的源宿属性
	 *
	 * @param deviceAddrSource
	 */
	void setDeviceAddrSource(short deviceAddrSource);

	String getDeviceAddrSinks();

	void setDeviceAddrSinks(String deviceAddrSinks);

	String getDatagramId();

	void setDatagramId(String datagramId);

	boolean isEnable();

	void setEnable(boolean enable);

	// ----------------------------------

	boolean isSource();

	void setSource(boolean source);

	IPort getPort();

	/**
	 * @param port
	 *            同时调portStructure对象的setAddress()方法
	 */
	void setPort(IPort port);

	/**
	 * 同步更新structure的源宿属性
	 *
	 * @param parent
	 */
	void setParent(IDeviceStructure parent);

	IDeviceStructure getParent();

	List<ISignalStructure> getChildren();

	IDatagram getDatagram();

	/**
	 * 同步更新报文绑定ID
	 *
	 * @param datagram
	 */
	void setDatagram(final IDatagram datagram);

}
