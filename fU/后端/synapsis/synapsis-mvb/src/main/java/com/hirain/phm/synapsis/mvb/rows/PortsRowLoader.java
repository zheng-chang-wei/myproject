/*******************************************************************************
 * Copyright (c) 2016, 2016 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.mvb.rows;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hirain.phm.synapsis.mvb.model.ByteOrder;
import com.hirain.phm.synapsis.mvb.model.IDatagram;
import com.hirain.phm.synapsis.mvb.model.IPort;
import com.hirain.phm.synapsis.mvb.model.IPortStructure;
import com.hirain.phm.synapsis.mvb.model.impl.Datagram;
import com.hirain.phm.synapsis.mvb.model.impl.Port;
import com.hirain.phm.synapsis.mvb.model.impl.PortStructure;
import com.hirain.phm.synapsis.mvb.util.StringUtil;
import com.hirain.phm.synapsis.mvb.util.UUUIDGenerator;

/**
 * @Version 1.0
 * @Author gangjie.yang@hirain.com
 * @Created Dec 26, 2016 11:12:31 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 26, 2016 gangjie.yang@hirain.com 1.0 create file
 */
public class PortsRowLoader extends ExcelRowLoader {

	private Map<String, IDatagram> datagrams;

	private final Map<String, Object> checkPortAddrMap = new HashMap<>();

	private final Map<String, Object> checkDatagramIDMap = new HashMap<>();

	private Map<String, Short> deviceAddrMap;

	/**
	 * @param deviceAddrMap
	 *            the deviceAddrMap to set
	 */
	public void setDeviceAddrMap(final Map<String, Short> deviceAddrMap) {
		this.deviceAddrMap = deviceAddrMap;
	}

	/**
	 * @param sheetName
	 * @param header
	 */
	public PortsRowLoader(final String sheetName, final String[] header) {
		super(sheetName, header);
	}

	/**
	 * @see com.hirain.common.excel.read.api.ExcelRowLoader#getRowModel()
	 */
	@Override
	public Object getRowModel() {
		final IPortStructure createPortStructure = new PortStructure();
		final IPort port = new Port();
		final IDatagram datagram = new Datagram();
		createPortStructure.setPort(port);
		createPortStructure.setDatagram(datagram);
		return createPortStructure;
	}

	/**
	 * @see com.hirain.common.excel.read.api.ExcelRowLoader#setCellValueToRowModel(java.lang.Object, int, java.lang.String)
	 */
	@Override
	public Object setCellValueToRowModel(final Object rowModel, final int headerIndex, String cellValue) throws Exception {
		if (rowModel == null && cellValue != null) {
			throw new Exception(getValidMessage(headerIndex, cellValue, "此行缺少必填项."));
		}
		if (!(rowModel instanceof IPortStructure)) {
			return null;
		}
		IPortStructure row = (IPortStructure) rowModel;
		switch (headerIndex) {
		case 0:
			if (cellValue == null) {
				row = null;
				return row;
			}
			if (Integer.valueOf(cellValue.replace(StringUtil.RADIX_HEX_PREFIX, StringUtil.EMPTY), 16) > 4095
					|| Integer.valueOf(cellValue.replace(StringUtil.RADIX_HEX_PREFIX, StringUtil.EMPTY), 16) < 1) {
				throw new Exception("‘端口信息’页中，端口地址【" + cellValue + "】超出范围");
			}
			if (checkPortAddrMap.containsKey(cellValue)) {
				throw new Exception("‘端口信息’页中，端口地址【" + cellValue + "】有重复");
			} else {
				checkPortAddrMap.put(cellValue, null);
				try {
					row.setAddress(Short.valueOf(cellValue.replace(StringUtil.RADIX_HEX_PREFIX, StringUtil.EMPTY), 16));
					row.getPort().setAddress(Short.valueOf(cellValue.replace(StringUtil.RADIX_HEX_PREFIX, StringUtil.EMPTY), 16));
					row.getPort().setName("端口" + String.valueOf(row.getAddress()));
				} catch (final Exception e) {
					throw new Exception(getValidMessage(0, cellValue, null));
				}
			}
			break;
		case 1:
			if (cellValue == null) {
				throw new Exception("‘端口信息’页中，‘端口地址’为【 " + StringUtil.RADIX_HEX_PREFIX
						+ Integer.toHexString(Integer.valueOf(row.getPort().getAddress())) + " 】的‘报文编号’不能为空");
			} else {
				if (checkDatagramIDMap.containsKey(cellValue)) {
					throw new Exception("‘端口信息’页中，报文编号【" + cellValue + "】有重复");
				} else {
					if (!datagrams.containsKey(cellValue)) {
						checkDatagramIDMap.put(cellValue, null);
						final IDatagram datagram = new Datagram();
						datagram.setId(UUUIDGenerator.getInstance().getUUIDByTimeAndAutoIncreIndex());
						datagram.setName(cellValue);
						datagrams.put(cellValue, datagram);
						row.setDatagramId(cellValue);
						row.setDatagram(datagram);
					}
				}
			}
			break;
		case 2:
			if (cellValue == null) {
				throw new Exception("‘端口信息’页中，‘端口地址’为【 " + StringUtil.RADIX_HEX_PREFIX
						+ Integer.toHexString(Integer.valueOf(row.getPort().getAddress())) + " 】的‘端口宽度’不能为空");
			} else {
				row.getPort().setSize(8 * (int) Math.pow(2, Integer.valueOf(cellValue) + 1));// Excel填写的是Fcode，这里的size是byte
				row.getPort().setFCode(Integer.valueOf(cellValue));
				break;
			}
		case 3:
			if (cellValue == null) {
				throw new Exception("‘端口信息’页中，‘端口地址’为【 " + StringUtil.RADIX_HEX_PREFIX
						+ Integer.toHexString(Integer.valueOf(row.getPort().getAddress())) + " 】的‘特征周期’不能为空");
			} else {
				row.getPort().setCircle(Double.valueOf(cellValue));
				break;
			}
		case 4:
			if (cellValue == null) {
				cellValue = "0";
			}
			try {
				Integer.valueOf(cellValue);
			} catch (final Exception e) {
				throw new Exception(getValidMessage(headerIndex, cellValue, "不合法"));
			}
			if (Integer.valueOf(cellValue) > row.getPort().getCircle()) {
				throw new Exception("‘端口信息’页中，‘端口地址’为【 " + StringUtil.RADIX_HEX_PREFIX
						+ Integer.toHexString(Integer.valueOf(row.getPort().getAddress())) + " 】的‘时间偏移’不能大于‘特征周期’");
			}
			row.getPort().setTimeSlot(Integer.valueOf(cellValue));
			break;
		case 5:
			if (cellValue == null) {
				cellValue = "TRUE";
			}
			row.setEnable(Boolean.valueOf(cellValue));
			break;
		case 6:
			if (deviceAddrMap.get(cellValue) == null) {
				throw new Exception("‘端口信息’页中，‘端口地址’为【 " + StringUtil.RADIX_HEX_PREFIX
						+ Integer.toHexString(Integer.valueOf(row.getPort().getAddress())) + " 】的‘源设备’ 不存在");
			}
			row.setDeviceAddrSource(deviceAddrMap.get(cellValue));
			break;
		case 7:
			if (cellValue == null) {
				row.setDeviceAddrSinks("");
				break;
			}
			if (cellValue.contains("，") || cellValue.contains("、")) {
				throw new Exception("‘端口信息’页中，‘端口地址’为【 " + StringUtil.RADIX_HEX_PREFIX
						+ Integer.toHexString(Integer.valueOf(row.getPort().getAddress())) + " 】的 宿设备信息填写格式错误");
			}
			final String[] split = cellValue.split(StringUtil.COMMA);

			for (int i = 0; i < split.length; i++) {
				if (!deviceAddrMap.containsKey(split[i])) {// 校验宿设备是否存在
					throw new Exception("‘端口信息’页中，‘端口地址’为【 " + StringUtil.RADIX_HEX_PREFIX
							+ Integer.toHexString(Integer.valueOf(row.getPort().getAddress())) + " 】的 宿设备【" + split[i] + "】 不存在");
				} else if (deviceAddrMap.get(split[i]) == row.getDeviceAddrSource()) {// 校验源、宿设备地址是否相同
					throw new Exception("‘端口信息’页中，‘端口地址’为【 " + StringUtil.RADIX_HEX_PREFIX
							+ Integer.toHexString(Integer.valueOf(row.getPort().getAddress())) + " 】的源、宿设备【" + split[i] + "】不能相同");
				}
			}
			final StringBuffer sb = new StringBuffer();
			for (int i = 0; i < split.length; i++) {
				sb.append(String.valueOf(deviceAddrMap.get(split[i])));
				if (i != split.length - 1) {
					sb.append(",");
				}
			}
			row.setDeviceAddrSinks(sb.toString());
			break;
		case 8:// 大小端设置
			if (cellValue == null) {
				break;
			}
			if (cellValue.equals("LE")) {
				row.getDatagram().setByteOrder(ByteOrder.LITTLE_ENDIAN);
			} else {
				row.getDatagram().setByteOrder(ByteOrder.BIG_ENDIAN);
			}
			break;
		case 9:
			row.setDescription(cellValue);
			row.getPort().setDescription(cellValue);
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
		if (!(sheetModel instanceof List) || !(rowModel instanceof IPortStructure)) {
			return;
		}
		final List<IPortStructure> sheet = (List<IPortStructure>) sheetModel;
		final IPortStructure row = (IPortStructure) rowModel;
		sheet.add(row);
	}

	/**
	 * @param datagrams
	 *            the datagrams to set
	 */
	public void setDatagrams(final Map<String, IDatagram> datagrams) {
		this.datagrams = datagrams;
	}

}
