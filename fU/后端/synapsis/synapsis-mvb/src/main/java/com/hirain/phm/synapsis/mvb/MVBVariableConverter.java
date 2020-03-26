/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.mvb;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.hirain.phm.synapsis.exception.SynapsisException;
import com.hirain.phm.synapsis.mvb.model.IDatagram;
import com.hirain.phm.synapsis.mvb.model.IDevice;
import com.hirain.phm.synapsis.mvb.model.IDeviceStructure;
import com.hirain.phm.synapsis.mvb.model.IPort;
import com.hirain.phm.synapsis.mvb.model.IPortStructure;
import com.hirain.phm.synapsis.mvb.model.ISignal;
import com.hirain.phm.synapsis.mvb.model.impl.Root;
import com.hirain.phm.synapsis.protocol.VariableConverter;
import com.hirain.phm.synapsis.setting.MVBVariable;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 10, 2019 4:34:21 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 10, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Component("MVB-converter")
public class MVBVariableConverter implements VariableConverter {

	/**
	 * @throws Exception
	 * @see com.hirain.phm.synapsis.protocol.VariableConverter#convert(java.lang.Object)
	 */
	@Override
	public List<MVBVariable> convert(Object object) throws SynapsisException {
		Root root = (Root) object;
		List<IDatagram> datagrams = root.getDatagrams();
		List<MVBVariable> variableList = new ArrayList<>();
		for (IDatagram datagram : datagrams) {
			List<ISignal> signals = datagram.getSignals();
			for (ISignal signal : signals) {
				MVBVariable variable = new MVBVariable();
				String id = datagram.getId();
				setMVBVariable(root, variable, id);
				variable.setSignalName(signal.getSignalName());
				variable.setDataType(signal.getDataType().ordinal());
				variable.setBitOffset(signal.getBitOffset());
				variable.setByteOffset(signal.getByteOffset());
				variable.setBitLen(signal.getLength());
				variable.setUnit(signal.getUnit());
				variable.setName(signal.getVariableId());
				variableList.add(variable);
			}
		}
		return variableList;
	}

	private void setMVBVariable(Root root, MVBVariable variable, String id) {
		List<IDeviceStructure> deviceStructures = root.getDeviceStructures();
		for (IDeviceStructure deviceStructure : deviceStructures) {
			String system = deviceStructure.getSystem();
			Short deviceAddress = deviceStructure.getAddress();
			String car = deviceStructure.getCar();
			List<IPortStructure> portStructures = deviceStructure.getPortStructureSource();
			for (IPortStructure portStructure : portStructures) {
				String datagramId = portStructure.getDatagramId();
				if (datagramId.equals(id)) {
					short portAddress = portStructure.getAddress();
					variable.setDevice((int) deviceAddress);
					variable.setDeviceName(getDeviceNameByDeviceAddress(root, deviceAddress));
					variable.setCarriage(car);
					variable.setSystem(system);
					variable.setPort((int) portAddress);
					IPort port = portStructure.getPort();
					variable.setFcode(port.getFCode());
				}
			}
		}
	}

	private String getDeviceNameByDeviceAddress(Root root, Short address) {
		List<IDevice> devices = root.getDevices();
		for (IDevice device : devices) {
			if (address.equals(device.getAddress())) {
				return device.getName();
			}
		}
		return null;
	}
}
