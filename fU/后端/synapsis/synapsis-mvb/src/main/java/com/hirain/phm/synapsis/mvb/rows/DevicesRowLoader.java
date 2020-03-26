/*******************************************************************************
 * Copyright (c) 2016, 2016 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.mvb.rows;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hirain.phm.synapsis.mvb.model.IDevice;
import com.hirain.phm.synapsis.mvb.model.IDeviceStructure;
import com.hirain.phm.synapsis.mvb.model.impl.Device;
import com.hirain.phm.synapsis.mvb.model.impl.DeviceStructure;
import com.hirain.phm.synapsis.mvb.util.StringUtil;

/**
 * @Version 1.0
 * @Author changwei.zheng@hirain.com
 * @Created 2019年12月4日 上午11:27:15
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年12月4日 changwei.zheng@hirain.com 1.0 create file
 */
public class DevicesRowLoader extends ExcelRowLoader {

	private final Map<String, Object> checkDeviceAddrMap = new HashMap<>();

	private Map<String, Short> deviceAddrMap;

	/**
	 * @param sheetName
	 * @param header
	 */
	public DevicesRowLoader(final String sheetName, final String[] header) {
		super(sheetName, header);
	}

	/**
	 * @see com.hirain.common.excel.read.api.ExcelRowLoader#getRowModel()
	 */
	@Override
	public Object getRowModel() {
		final IDeviceStructure deviceStructure = new DeviceStructure();
		final IDevice createDevice = new Device();
		deviceStructure.setDevice(createDevice);
		return deviceStructure;
	}

	/**
	 * @see com.hirain.common.excel.read.api.ExcelRowLoader#setCellValueToRowModel(java.lang.Object, int, java.lang.String)
	 */
	@Override
	public Object setCellValueToRowModel(final Object rowModel, final int headerIndex, final String cellValue) throws Exception {
		if (rowModel == null && cellValue != null) {
			throw new Exception(getValidMessage(headerIndex, cellValue, "此行缺少必填项."));
		}
		if (!(rowModel instanceof IDeviceStructure)) {
			return null;
		}
		IDeviceStructure row = (IDeviceStructure) rowModel;
		switch (headerIndex) {
		case 0:
			if (cellValue == null) {
				row = null;
				return row;
			}
			if (Integer.valueOf(cellValue.replace(StringUtil.RADIX_HEX_PREFIX, StringUtil.EMPTY), 16) > 255
					|| Integer.valueOf(cellValue.replace(StringUtil.RADIX_HEX_PREFIX, StringUtil.EMPTY), 16) < 1) {
				throw new Exception("‘设备信息’页中，设备地址【" + cellValue + "】超出范围");
			}
			if (checkDeviceAddrMap.containsKey(cellValue)) {
				throw new Exception("‘设备信息’页中，设备地址【" + cellValue + "】有重复");
			} else {
				checkDeviceAddrMap.put(cellValue, null);
				try {
					row.setAddress(Short.valueOf(cellValue.replace(StringUtil.RADIX_HEX_PREFIX, StringUtil.EMPTY), 16));
					row.getDevice().setAddress(Short.valueOf(cellValue.replace(StringUtil.RADIX_HEX_PREFIX, StringUtil.EMPTY), 16));
				} catch (final Exception e) {
					throw new Exception(getValidMessage(0, cellValue, null));
				}
			}
			break;
		case 1:
			if (cellValue == null) {
				row = null;
				return row;
			}
			row.getDevice().setName(cellValue);
			deviceAddrMap.put(cellValue, row.getDevice().getAddress());
			break;
		case 2:
			// if (cellValue == null) {
			// row = null;
			// return row;
			// }
			row.setSystem(convertCellValueNull2Empty(cellValue));
			break;
		case 3:
			// if (cellValue == null) {
			// row = null;
			// return row;
			// }
			row.setCar(convertCellValueNull2Empty(cellValue));
			break;
		case 4:
			row.setDescription(convertCellValueNull2Empty(cellValue));
			row.getDevice().setDescription(convertCellValueNull2Empty(cellValue));
			break;
		default:
			break;
		}
		return row;
	}

	/**
	 * @see com.hirain.common.excel.read.api.ExcelRowLoader#setRowModelToSheetModel(java.lang.Object, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void setRowModelToSheetModel(final Object sheetModel, final Object rowModel) {
		if (!(sheetModel instanceof List) || !(rowModel instanceof IDeviceStructure)) {
			return;
		}
		final List<IDeviceStructure> sheet = (List<IDeviceStructure>) sheetModel;
		final IDeviceStructure row = (IDeviceStructure) rowModel;
		sheet.add(row);

	}

	/**
	 * @param deviceAddrMap
	 *            the deviceAddrMap to set
	 */
	public void setDeviceAddrMap(final Map<String, Short> deviceAddrMap) {
		this.deviceAddrMap = deviceAddrMap;
	}

}
