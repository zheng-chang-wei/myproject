/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.ecn.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.hirain.phm.synapsis.ecn.ECNVariableQuery;
import com.hirain.phm.synapsis.ecn.model.BusInterface;
import com.hirain.phm.synapsis.ecn.model.BusInterfaceList;
import com.hirain.phm.synapsis.ecn.model.DataSet;
import com.hirain.phm.synapsis.ecn.model.DataSetList;
import com.hirain.phm.synapsis.ecn.model.Device;
import com.hirain.phm.synapsis.ecn.model.Element;
import com.hirain.phm.synapsis.ecn.model.Telegram;
import com.hirain.phm.synapsis.setting.BusDataType;
import com.hirain.phm.synapsis.setting.ECNVariable;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 19, 2020 1:39:08 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 19, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Component
public class ECNVariableQueryImpl implements ECNVariableQuery {

	/**
	 * @see com.hirain.phm.synapsis.ecn.ECNVariableQuery#getAllVariables(com.hirain.phm.synapsis.ecn.model.Device)
	 */
	@Override
	public List<ECNVariable> getAllVariables(Device device) {
		DataSetList dataSetList = device.getDataSetList();
		List<DataSet> dataSets = dataSetList.getDataSet();
		List<ECNVariable> list = new ArrayList<>();
		for (DataSet dataSet : dataSets) {
			List<Element> elementList = dataSet.getElement();
			for (Element element : elementList) {
				ECNVariable variable = new ECNVariable();
				BusInterfaceList busInterfaceList = device.getBusInterfaceList();
				List<BusInterface> busInterfaces = busInterfaceList.getBusInterfaces();
				for (BusInterface busInterface : busInterfaces) {
					variable.setSourceIp(busInterface.getHostIp());
					Long comId = getComIdByDataSetId(dataSet.getId(), busInterface);
					variable.setComId(comId);
				}
				setVariableFromElement(element, variable);
				list.add(variable);
			}
		}
		return list;
	}

	@Override
	public List<ECNVariable> getVariables(long comId, int length, Device device) {
		ECNVariableHeader header = getDataSetId(comId, device);
		if (header == null) {
			return null;
		}
		String dataSetId = header.dataSetId;
		for (DataSet dataSet : device.getDataSetList().getDataSet()) {
			if (dataSetId.equals(dataSet.getId())) {
				List<Element> elements = dataSet.getElement();
				return elementToVariable(elements, header, length);
			}
		}
		return null;

	}

	/**
	 * @see com.hirain.phm.synapsis.ecn.ECNVariableQuery#getDataSetLength(long, Device)
	 */
	@Override
	public int getDataSetLength(long comId, Device device) {
		ECNVariableHeader header = getDataSetId(comId, device);
		String dataSetId = header.dataSetId;
		int length = 0;
		for (DataSet dataSet : device.getDataSetList().getDataSet()) {
			if (dataSetId.equals(dataSet.getId())) {
				for (Element element : dataSet.getElement()) {
					BusDataType dataType = BusDataType.valueOf(element.getType());
					length += dataType.getByteLen();
				}
			}
		}
		return length;
	}

	/**
	 * @see com.hirain.phm.synapsis.ecn.ECNVariableQuery#getComIdCycle(com.hirain.phm.synapsis.ecn.model.Device)
	 */
	@Override
	public Map<Long, String> getComIdCycle(Device device) {
		Map<Long, String> map = new HashMap<>();
		BusInterfaceList busInterfaceList = device.getBusInterfaceList();
		List<BusInterface> busInterfaces = busInterfaceList.getBusInterfaces();
		for (BusInterface busInterface : busInterfaces) {
			List<Telegram> telegrams = busInterface.getTelegram();
			for (Telegram telegram : telegrams) {
				map.put(Long.valueOf(telegram.getComId()), telegram.getPdParameter().getPD_cycle());
			}
		}
		return map;
	}

	/**
	 * @see com.hirain.phm.synapsis.ecn.ECNVariableQuery#getEndian(com.hirain.phm.synapsis.ecn.model.Device)
	 */
	@Override
	public String getEndian(Device device) {
		return device.getEndian();
	}

	/**
	 * @param elements
	 * @param header
	 * @param total
	 * @return
	 */
	private List<ECNVariable> elementToVariable(List<Element> elements, ECNVariableHeader header, int total) {
		List<ECNVariable> variables = new ArrayList<>();
		long comId = Long.parseLong(header.comId);
		int len = 0;
		for (Element element : elements) {
			ECNVariable variable = new ECNVariable();
			variable.setSourceIp(header.sourceIp);
			variable.setComId(comId);
			setVariableFromElement(element, variable);
			variables.add(variable);
			len += getByteLen(variable.getDataType());
			if (len >= total) {
				break;
			}
		}
		return variables;
	}

	private void setVariableFromElement(Element element, ECNVariable variable) {
		variable.setSignalName(element.getSignal());
		String type = element.getType();
		BusDataType busDataType = BusDataType.valueOf(type);
		variable.setDataType(busDataType.ordinal());
		String byteOffset = element.getByteOffset();
		if (byteOffset != null) {
			variable.setByteOffset(Integer.valueOf(byteOffset));
		}
		String bitOffset = element.getBitOffset();
		if (bitOffset != null) {
			variable.setBitOffset(Integer.valueOf(bitOffset));
		}
		String bitLen = element.getBitLen();
		if (bitLen != null) {
			variable.setBitLen(Integer.valueOf(bitLen));
		}
		variable.setName(element.getName());
		variable.setUnit(element.getUnit());
	}

	/**
	 * @param type
	 * @return
	 */
	private int getByteLen(int type) {
		BusDataType dataType = BusDataType.values()[type];
		return dataType.getByteLen();
	}

	/**
	 * @param comId
	 * @param device
	 * @return
	 */
	private ECNVariableHeader getDataSetId(long comId, Device device) {
		String fComId = String.valueOf(comId);
		for (BusInterface busInterface : device.getBusInterfaceList().getBusInterfaces()) {
			for (Telegram telegram : busInterface.getTelegram()) {
				if (fComId.equals(telegram.getComId())) {
					return new ECNVariableHeader(busInterface.getHostIp(), fComId, telegram.getDatasetId());
				}
			}
		}
		return null;
	}

	private Long getComIdByDataSetId(String dataSetId, BusInterface busInterface) {
		List<Telegram> telegramList = busInterface.getTelegram();
		for (Telegram telegram : telegramList) {
			String datasetId = telegram.getDatasetId();
			if (dataSetId.equals(datasetId)) {
				return Long.valueOf(telegram.getComId());
			}
		}
		return null;
	}

	private class ECNVariableHeader {

		String sourceIp;

		String comId;

		String dataSetId;

		/**
		 * @param sourceIp
		 * @param comId
		 * @param dataSetId
		 */
		public ECNVariableHeader(String sourceIp, String comId, String dataSetId) {
			this.sourceIp = sourceIp;
			this.comId = comId;
			this.dataSetId = dataSetId;
		}

	}
}
