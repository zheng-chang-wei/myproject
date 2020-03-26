/*******************************************************************************
 * Copyright (c) 2016, 2016 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.mvb.rows;

import java.util.List;

import com.hirain.phm.synapsis.mvb.model.IDataType;
import com.hirain.phm.synapsis.mvb.model.impl.DataType;
import com.hirain.phm.synapsis.setting.BusDataType;

/**
 * @Version 1.0
 * @Author changwei.zheng@hirain.com
 * @Created 2019年12月4日 上午10:46:12
 * @Description
 *              <p>
 *              声明类型
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年12月4日 changwei.zheng@hirain.com 1.0 create file
 */
public class SignalTypesRowLoader extends ExcelRowLoader {

	/**
	 * @param sheetName
	 * @param header
	 */
	public SignalTypesRowLoader(final String sheetName, final String[] header) {
		super(sheetName, header);
	}

	/**
	 * @see com.hirain.phm.synapsis.protocol.mvb.rows.common.excel.read.api.ExcelRowLoader#getRowModel()
	 */
	@Override
	public Object getRowModel() {
		final IDataType dataType = new DataType();
		return dataType;
	}

	/**
	 * @see com.hirain.phm.synapsis.protocol.mvb.rows.common.excel.read.api.ExcelRowLoader#setCellValueToRowModel(java.lang.Object, int,
	 *      java.lang.String)
	 */
	@Override
	public Object setCellValueToRowModel(final Object rowModel, final int headerIndex, final String cellValue) throws Exception {
		if (rowModel == null && cellValue != null) {
			throw new Exception(getValidMessage(headerIndex, cellValue, "����ȱ�ٱ�����."));
		}
		if (!(rowModel instanceof DataType)) {
			return null;
		}
		DataType row = (DataType) rowModel;
		switch (headerIndex) {
		case 0:
			if (cellValue == null) {
				row = null;
				return row;
			}
			row.setBaseType(cellValue);
			break;
		case 1:
			row.setName(cellValue);
			break;
		case 2:
			row.setRange(cellValue);
			break;
		case 3:
			if (cellValue != null && !row.getBaseType().equals(BusDataType.BITS.name())) {
				row.setLength(Integer.valueOf(cellValue));
			} else {
				row.setDescription(cellValue);
			}
			break;
		default:
			break;
		}
		return row;
	}

	/**
	 * @see com.hirain.phm.synapsis.protocol.mvb.rows.common.excel.read.api.ExcelRowLoader#setRowModelToSheetModel(java.lang.Object, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void setRowModelToSheetModel(final Object sheetModel, final Object rowModel) {
		if (!(sheetModel instanceof List) || !(rowModel instanceof IDataType)) {
			return;
		}
		final List<IDataType> sheet = (List<IDataType>) sheetModel;
		final IDataType row = (IDataType) rowModel;
		sheet.add(row);
	}

}
