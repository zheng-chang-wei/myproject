package com.hirain.phm.synapsis.mvb.model.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hirain.phm.synapsis.mvb.model.ICacheModel;
import com.hirain.phm.synapsis.mvb.model.IDataType;
import com.hirain.phm.synapsis.mvb.model.IDatagram;
import com.hirain.phm.synapsis.mvb.model.IDevice;
import com.hirain.phm.synapsis.mvb.model.IPort;
import com.hirain.phm.synapsis.mvb.model.IPortStructure;
import com.hirain.phm.synapsis.mvb.model.ISignal;

/**
 * @Version 1.0
 * @Author changwei.zheng@hirain.com
 * @Created 2019年12月4日 下午2:37:09
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年12月4日 changwei.zheng@hirain.com 1.0 create file
 */
public class CacheModel implements ICacheModel {

	private static final long serialVersionUID = -5076641250762476919L;

	private final Map<String, IDataType> dataTypesKeyBase = new HashMap<String, IDataType>();

	private final Map<String, IDataType> dataTypesKeyName = new HashMap<String, IDataType>();

	// private final Map<String, ITrain> trainsKeyId = new HashMap<String, ITrain>();
	//
	// private final Map<String, ISystem> systemsKeyId = new HashMap<String, ISystem>();

	private final Map<Short, IDevice> devicesKeyAddr = new HashMap<Short, IDevice>();

	private final Map<Short, IPort> portsKeyAddr = new HashMap<Short, IPort>();

	private final Map<String, IDatagram> datagramsKeyId = new HashMap<String, IDatagram>();

	private final Map<String, ISignal> signalsKeyId = new HashMap<String, ISignal>();

	private final Map<String, ISignal> signalsKeyVarId = new HashMap<String, ISignal>();

	private final Map<String, ISignal> signalsKeyPath = new HashMap<String, ISignal>();

	private final Map<Short, IDatagram> datagramByPortAddr = new HashMap<Short, IDatagram>();

	private final Map<String, IPort> portByDatagram = new HashMap<String, IPort>();

	private final Map<Short, List<IPortStructure>> portStructuresByPortAddr = new HashMap<Short, List<IPortStructure>>();

	private final Map<String, IDatagram> datagramBySignalId = new HashMap<String, IDatagram>();

	/**
	 * @see com.hirain.mct.model.standard.ICacheModel#getDataTypesKeyBase()
	 */
	@Override
	public Map<String, IDataType> getDataTypesKeyBase() {
		return dataTypesKeyBase;
	}

	/**
	 * @see com.hirain.mct.model.standard.ICacheModel#getDataTypesKeyName()
	 */
	@Override
	public Map<String, IDataType> getDataTypesKeyName() {
		return dataTypesKeyName;
	}

	/**
	 * @see com.hirain.mct.model.standard.ICacheModel#getDevicesKeyAddr()
	 */
	@Override
	public Map<Short, IDevice> getDevicesKeyAddr() {
		return devicesKeyAddr;
	}

	/**
	 * @see com.hirain.mct.model.standard.ICacheModel#getPortsKeyAddr()
	 */
	@Override
	public Map<Short, IPort> getPortsKeyAddr() {
		return portsKeyAddr;
	}

	/**
	 * @see com.hirain.mct.model.standard.ICacheModel#getDatagramsKeyId()
	 */
	@Override
	public Map<String, IDatagram> getDatagramsKeyId() {
		return datagramsKeyId;
	}

	/**
	 * @see com.hirain.mct.model.standard.ICacheModel#getSignalsKeyId()
	 */
	@Override
	public Map<String, ISignal> getSignalsKeyId() {
		return signalsKeyId;
	}

	/**
	 * @see com.hirain.mct.model.standard.ICacheModel#getSignalsKeyVarId()
	 */
	@Override
	public Map<String, ISignal> getSignalsKeyVarId() {
		return signalsKeyVarId;
	}

	/**
	 * @see com.hirain.mct.model.standard.ICacheModel#getSignalsKeyPath()
	 */
	@Override
	public Map<String, ISignal> getSignalsKeyPath() {
		return signalsKeyPath;
	}

	/**
	 * @see com.hirain.mct.model.standard.ICacheModel#getDatagramByPortAddr()
	 */
	@Override
	public Map<Short, IDatagram> getDatagramByPortAddr() {
		return datagramByPortAddr;
	}

	/**
	 * @see com.hirain.mct.model.standard.ICacheModel#getPortByDatagram()
	 */
	@Override
	public Map<String, IPort> getPortByDatagramId() {
		return portByDatagram;
	}

	/**
	 * @see com.hirain.mct.model.standard.ICacheModel#getPortStructureByPortAddr()
	 */
	@Override
	public Map<Short, List<IPortStructure>> getPortStructuresByPortAddr() {
		return portStructuresByPortAddr;
	}

	/**
	 * @see com.hirain.mct.model.standard.ICacheModel#getDatagramBySignalId()
	 */
	@Override
	public Map<String, IDatagram> getDatagramBySignalId() {
		return datagramBySignalId;
	}

	@Override
	public String toString() {
		return "CacheModel [dataTypesKeyBase=" + dataTypesKeyBase + ", dataTypesKeyName=" + dataTypesKeyName + ", devicesKeyAddr=" + devicesKeyAddr
				+ ", portsKeyAddr=" + portsKeyAddr + ", datagramsKeyId=" + datagramsKeyId + ", signalsKeyId=" + signalsKeyId + ", signalsKeyVarId="
				+ signalsKeyVarId + ", signalsKeyPath=" + signalsKeyPath + ", datagramByPortAddr=" + datagramByPortAddr + ", portByDatagram="
				+ portByDatagram + ", portStructuresByPortAddr=" + portStructuresByPortAddr + ", datagramBySignalId=" + datagramBySignalId + "]";
	}

}
