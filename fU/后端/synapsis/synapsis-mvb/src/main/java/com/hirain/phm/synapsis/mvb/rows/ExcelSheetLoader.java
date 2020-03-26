/*******************************************************************************
 * Copyright (c) 2014, 2014 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.mvb.rows;

import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.poi.ss.usermodel.Sheet;

import com.hirain.phm.synapsis.mvb.util.MVBExcelUtil;

/**
 * @Version 1.0
 * @Author changwei.zheng@hirain.com
 * @Created 2019年12月4日 上午11:14:59
 * @Description
 *              <p>
 *              Excel的Sheet页数据加载器
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年12月4日 changwei.zheng@hirain.com 1.0 create file
 */
public class ExcelSheetLoader {

	/**
	 * Excel中的sheet页（api定义类型）
	 */
	private Sheet sheet;

	private String[] header;

	/**
	 * Excel中的行数据加载器
	 */
	private ExcelRowLoader rowLoader;

	/**
	 * Excel中的sheet页数据模型，用来保存读进来的数据
	 */
	private Object sheetModel;

	private boolean isIngnoreSpaceWrap = false;

	public Sheet getSheet() {
		return sheet;
	}

	public void setSheet(final Sheet sheet) {
		this.sheet = sheet;
	}

	public String[] getHeader() {
		return header;
	}

	public void setHeader(final String[] header) {
		this.header = header;
	}

	public ExcelRowLoader getRowLoader() {
		return rowLoader;
	}

	/**
	 * sheet数据收集器_设置_行数据收集器
	 */
	public void setRowLoader(final ExcelRowLoader rowLoader) {
		this.rowLoader = rowLoader;
	}

	public Object getSheetModel() {
		return sheetModel;
	}

	public void setSheetModel(final Object sheetModel) {
		this.sheetModel = sheetModel;
	}

	/**
	 * ExcelSheetLoader的属性设定完毕后，
	 * 开始把sheet页中数据一行一行的加载到sheet数据对象中；
	 * 
	 * @throws Exception
	 */
	public void loadSheetModel() throws Exception {
		MVBExcelUtil.setIngnoreSpaceWrap(isIngnoreSpaceWrap);
		/* 收集所有行(表头)列数据 */
		final Set<Entry<Integer, List<String>>> entrySet = MVBExcelUtil.getCellValuesGroupByRow(sheet, header).entrySet();
		for (final Entry<Integer, List<String>> entry : entrySet) {
			/* 把这一行的数据赋给RowModel数据模型，然后把RowModel赋给SheetModel对象 */
			rowLoader.setCurrentRowIndex(entry.getKey());
			rowLoader.loadRowModel(sheetModel, entry.getValue());
		}
	}

	/**
	 * @param isIngnoreSpaceWrap
	 *            the isIngnoreSpaceWrap to set
	 */
	public void setIngnoreSpaceWrap(final boolean isIngnoreSpaceWrap) {
		this.isIngnoreSpaceWrap = isIngnoreSpaceWrap;
	}

}
