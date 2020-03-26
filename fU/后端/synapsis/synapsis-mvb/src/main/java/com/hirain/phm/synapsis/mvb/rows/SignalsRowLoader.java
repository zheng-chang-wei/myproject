/*******************************************************************************
 * Copyright (c) 2016, 2016 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.mvb.rows;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hirain.phm.synapsis.mvb.model.IDatagram;
import com.hirain.phm.synapsis.mvb.model.IRoot;
import com.hirain.phm.synapsis.mvb.model.ISignal;
import com.hirain.phm.synapsis.mvb.model.impl.Signal;
import com.hirain.phm.synapsis.mvb.util.UUUIDGenerator;
import com.hirain.phm.synapsis.setting.BusDataType;

/**
 * @Version 1.0
 * @Author changwei.zheng@hirain.com
 * @Created 2019年12月4日 上午11:29:22
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年12月4日 changwei.zheng@hirain.com 1.0 create file
 */
public class SignalsRowLoader extends ExcelRowLoader {

	Map<String, Object> checkValueIDMap = new HashMap<>();

	Map<String, IDatagram> datagrams;

	String sheetName;

	/**
	 * @param sheetName
	 * @param header
	 */
	public SignalsRowLoader(final String sheetName, final String[] header) {
		super(sheetName, header);
		this.sheetName = sheetName;
	}

	/**
	 * @see com.hirain.common.excel.read.api.ExcelRowLoader#getRowModel()
	 */
	@Override
	public Object getRowModel() {
		final ISignal signal = new Signal();
		return signal;
	}

	/**
	 * @see com.hirain.common.excel.read.api.ExcelRowLoader#setCellValueToRowModel(java.lang.Object, int, java.lang.String)
	 */
	@Override
	public Object setCellValueToRowModel(final Object rowModel, final int headerIndex, final String cellValue) throws Exception {
		if (rowModel == null && cellValue != null) {
			throw new Exception(getValidMessage(headerIndex, cellValue, "此行缺少必填项."));
		}
		if (!(rowModel instanceof ISignal)) {
			return null;
		}
		ISignal row = (ISignal) rowModel;
		switch (headerIndex) {
		case 0:
			if (cellValue == null) {
				row = null;
				return row;
			}
			if (datagrams.containsKey(cellValue)) {
				datagrams.get(cellValue).getSignals().add(row);
				datagrams.get(cellValue).setSheetName(sheetName);
			}
			break;
		case 1:
			if (cellValue == null) {
				throw new Exception(super.getSheetName() + "页中，存在‘变量名称’为空的变量");
			} else {
				if (checkValueIDMap.containsKey(cellValue)) {
					throw new Exception(super.getSheetName() + "页中，变量名称【" + cellValue + "】有重复");
				} else {
					checkValueIDMap.put(cellValue, null);
					row.setVariableId(cellValue);
					row.setId(UUUIDGenerator.getInstance().getUUIDByTimeAndAutoIncreIndex());
				}
			}
			break;
		case 2:
			if (cellValue == null) {
				throw new Exception(super.getSheetName() + "页中，‘变量名称’为【 " + row.getVariableId() + " 】的‘字节偏移’不能为空");
			} else {
				row.setByteOffset(Integer.valueOf(cellValue));
				break;
			}
		case 3:
			if (cellValue == null) {
				throw new Exception(super.getSheetName() + "页中，‘变量名称’为【 " + row.getVariableId() + " 】的‘位偏移’不能为空");
			} else {
				row.setBitOffset(Integer.valueOf(cellValue));
				break;
			}
		case 4:
			final IRoot root = (IRoot) getExcelModel();
			if (cellValue == null) {
				throw new Exception(super.getSheetName() + "页中，‘变量名称’为【 " + row.getVariableId() + " 】的‘数据类型’不能为空");
			} else if (!root.getCacheModel().getDataTypesKeyName().containsKey(cellValue)) {
				throw new Exception(super.getSheetName() + "页中，‘变量名称’为【 " + row.getVariableId() + " 】的‘数据类型’有误");
			} else {
				row.setDataType(BusDataType.valueOf(root.getCacheModel().getDataTypesKeyName().get(cellValue).getBaseType()));
				break;
			}
		case 5:
			if (cellValue == null) {
				throw new Exception(super.getSheetName() + "页中，‘变量名称’为【 " + row.getVariableId() + " 】的‘数据长度’不能为空");
			} else {
				if (!checkTypeAndLength(row.getDataType(), Integer.valueOf(cellValue))) {
					throw new Exception(super.getSheetName() + "页中，‘变量名称’为【 " + row.getVariableId() + " 】的‘数据长度’和‘数据类型’不对应");
				}
				row.setLength(Integer.valueOf(cellValue));
				break;
			}
		case 6:
			row.setFactor(cellValue == null ? 0 : Double.valueOf(cellValue));
			break;
		case 7:
			row.setOffset(cellValue == null ? 0 : Double.valueOf(cellValue));
			break;
		case 8:
			row.setRange(convertCellValueNull2Empty(cellValue));
			break;
		case 9:
			row.setSignalName(convertCellValueNull2Empty(cellValue));
			break;
		case 10:
			row.setUnit(convertCellValueNull2Empty(cellValue));
			break;
		case 11:
			row.setDescription(convertCellValueNull2Empty(cellValue));
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
		if (!(sheetModel instanceof List) || !(rowModel instanceof Signal)) {
			return;
		}

		final List<ISignal> sheet = (List<ISignal>) sheetModel;
		final ISignal row = (ISignal) rowModel;
		sheet.add(row);
	}

	/**
	 * @param datagrams
	 *            the datagrams to set
	 */
	public void setDatagrams(final Map<String, IDatagram> datagrams) {
		this.datagrams = datagrams;
	}

	/**
	 * 校验数据类型和数据长度是否匹配
	 * 
	 * @param type
	 *            数据类型
	 * @param len
	 *            数据长度
	 * @return
	 */
	private boolean checkTypeAndLength(final BusDataType type, final int len) {
		if (type.equals(BusDataType.BOOLEAN) && len == 1) {
			return true;
		}
		if (type.equals(BusDataType.FLOAT) && len == 32) {
			return true;
		}
		if (type.equals(BusDataType.INT8) && len == 8) {
			return true;
		}
		if (type.equals(BusDataType.UINT8) && len == 8) {
			return true;
		}
		if (type.equals(BusDataType.INT16) && len == 16) {
			return true;
		}
		if (type.equals(BusDataType.UINT16) && len == 16) {
			return true;
		}
		if (type.equals(BusDataType.INT32) && len == 32) {
			return true;
		}
		if (type.equals(BusDataType.UINT32) && len == 32) {
			return true;
		}
		if (type.equals(BusDataType.BITS) && len > 0 && len <= 32) {
			return true;
		}
		return false;
	}
}
