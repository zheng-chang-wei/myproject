/*******************************************************************************
 * Copyright (c) 2017, 2017 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.mvb.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @Version 1.0
 * @Author gangjie.yang@hirain.com
 * @Created May 15, 2017 3:13:40 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               May 15, 2017 gangjie.yang@hirain.com 1.0 create file
 */
public interface ICacheModel extends Serializable, Cloneable {

	/**
	 * @return key:基本类型名字;value:类型对象
	 */
	Map<String, IDataType> getDataTypesKeyBase();

	/**
	 * @return key:自定义类型名字;value:类型对象
	 */
	Map<String, IDataType> getDataTypesKeyName();

	// ----------------------------------------------------------

	// Map<String, ITrain> getTrainsKeyId();
	//
	// Map<String, ISystem> getSystemsKeyId();

	// ----------------------------------------------------------

	Map<Short, IDevice> getDevicesKeyAddr();

	Map<Short, IPort> getPortsKeyAddr();

	Map<String, IDatagram> getDatagramsKeyId();

	/**
	 * @return key:id;value:信号对象
	 */
	Map<String, ISignal> getSignalsKeyId();

	/**
	 * @return key:VariableId;value:信号对象
	 */
	Map<String, ISignal> getSignalsKeyVarId();

	/**
	 * @return key:treePath;value:信号对象
	 */
	Map<String, ISignal> getSignalsKeyPath();

	// ************************************************************
	/**
	 * @return key:SignalId;value:报文对象
	 */
	Map<String, IDatagram> getDatagramBySignalId();

	/**
	 * @return key:PortAddr;value:报文对象
	 */
	Map<Short, IDatagram> getDatagramByPortAddr();

	/**
	 * @return key:报文id;value:Port对象
	 */
	Map<String, IPort> getPortByDatagramId();

	/**
	 * @return key:PortAddr;value:PortStructure对象集合
	 */
	Map<Short, List<IPortStructure>> getPortStructuresByPortAddr();
}
