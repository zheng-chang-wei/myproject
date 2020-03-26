/*******************************************************************************
 * Copyright (c) 2014, 2014 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.mvb.rows;

import java.util.List;

/**
 * @Version 1.0
 * @Author changwei.zheng@hirain.com
 * @Created 2019年12月4日 上午11:13:01
 * @Description
 *              <p>
 *              Excel的行数据加载器
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年12月4日 changwei.zheng@hirain.com 1.0 create file
 */
public abstract class ExcelRowLoader {

	private final String sheetName;

	private final String[] header;

	protected int currentRowIndex;

	/**
	 * @param sheetName
	 * @param header
	 */
	public ExcelRowLoader(final String sheetName, final String[] header) {
		super();
		this.sheetName = sheetName;
		this.header = header;
	}

	/**
	 * 整个Excel模型（sheet页面之间有关系时可能会用）
	 */
	private Object excelModel;

	/**
	 * 整个Excel模型（sheet页面之间有关系时可能会用）
	 * 
	 * @return the excelModel
	 */
	public Object getExcelModel() {
		return excelModel;
	}

	/**
	 * 整个Excel模型（sheet页面之间有关系时可能会用）
	 * 
	 * @param excelModel
	 *            the excelModel to set
	 */
	public void setExcelModel(final Object excelModel) {
		this.excelModel = excelModel;
	}

	/**
	 * @return the sheetName
	 */
	public String getSheetName() {
		return sheetName;
	}

	/**
	 * @return the header
	 */
	public String[] getHeader() {
		return header;
	}

	/**
	 * @param currentRowIndex
	 *            the currentRowIndex to set
	 */
	public void setCurrentRowIndex(final int currentRowIndex) {
		this.currentRowIndex = currentRowIndex;
	}

	/**
	 * @param cellValue
	 * @return 当单元格没有赋值时，按空字符串处理
	 */
	public String convertCellValueNull2Empty(final String cellValue) {
		if (cellValue == null) {
			return "";
		}
		return cellValue;
	}

	/**
	 * @param headerIndex
	 * @param cellValue
	 * @param validMessage
	 * @return
	 */
	public String getValidMessage(final int headerIndex, final String cellValue, final String validMessage) {
		final StringBuffer buffer = new StringBuffer();
		buffer.append("Sheet名[");
		buffer.append(sheetName);
		buffer.append("],行号[");
		buffer.append(currentRowIndex + 1);
		buffer.append("],列名[");
		buffer.append(header[headerIndex]);
		buffer.append("];\n单元格[");
		buffer.append(cellValue);
		buffer.append("] ");
		buffer.append(validMessage == null ? "" : validMessage);
		return buffer.toString();
	}

	/**
	 * 把这一行的数据赋给RowModel数据模型，然后把RowModel赋给sheetModel对象
	 * 
	 * @param sheetModel
	 *            Sheet页数据模型对象
	 * @param rowValue
	 *            这一行的若干列（表头列）的单元格cell中的数据list
	 * @return 行RowModel数据模型对象
	 * @throws Exception
	 */
	public Object loadRowModel(final Object sheetModel, final List<String> rowValue) throws Exception {
		Object rowModel = getRowModel();
		for (int headerIndex = 0; headerIndex < rowValue.size(); headerIndex++) {
			rowModel = setCellValueToRowModel(rowModel, headerIndex, rowValue.get(headerIndex));
		}
		setRowModelToSheetModel(sheetModel, rowModel);
		return rowModel;
	}

	/**
	 * @return new一个行RowModel数据模型新对象
	 */
	public abstract Object getRowModel();

	/**
	 * 把某个单元格cellValue数据赋给行RowModel数据对象
	 * 
	 * @param rowModel
	 *            行rowData数据对象
	 * @param headerIndex
	 *            这个单元格所在的列对应表头中的列
	 * @param cellValue
	 *            单元格的数据内容
	 * @return 行rowModel数据对象
	 */
	public abstract Object setCellValueToRowModel(final Object rowModel, final int headerIndex, final String cellValue) throws Exception;

	/**
	 * 行数据对象赋给sheet数据对象
	 * 
	 * @param sheetModel
	 *            sheet页数据对象
	 * @param rowModel
	 *            行数据对象
	 */
	public abstract void setRowModelToSheetModel(Object sheetModel, Object rowModel) throws Exception;
}
