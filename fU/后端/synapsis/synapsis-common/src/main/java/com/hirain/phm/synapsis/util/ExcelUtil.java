/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 10, 2019 1:38:56 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 10, 2019 jianwen.xin@hirain.com 1.0 create file
 */
public class ExcelUtil {

	public static Workbook createWorkbook(String excelPath) throws Exception {
		InputStream is = null;
		Workbook wb = null;
		try {
			File sourcefile = new File(excelPath);
			is = new FileInputStream(sourcefile);
			wb = WorkbookFactory.create(is);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				throw e;
			}
		}
		return wb;
	}

	public static Workbook createWorkbook(File sourcefile) throws Exception {
		InputStream is = null;
		Workbook wb = null;
		try {
			is = new FileInputStream(sourcefile);
			wb = WorkbookFactory.create(is);
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				throw e;
			}
		}
		return wb;
	}

	/**
	 * @param cell
	 * @return
	 */
	public static String getCellValue(Cell cell) {
		if (cell == null) {
			return null;
		}
		String result = null;
		switch (cell.getCellTypeEnum()) {
		case NUMERIC:
			if (HSSFDateUtil.isCellDateFormatted(cell)) {
				SimpleDateFormat sdf = null;
				if (cell.getCellStyle().getDataFormat() == HSSFDataFormat.getBuiltinFormat("h:mm")) {
					sdf = new SimpleDateFormat("HH:mm");
				} else {
					sdf = new SimpleDateFormat("yyyy-MM-dd");
				}
				Date date = cell.getDateCellValue();
				result = sdf.format(date);
			} else if (cell.getCellStyle().getDataFormat() == 58) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				double value = cell.getNumericCellValue();
				Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value);
				result = sdf.format(date);
			} else {
				double value = cell.getNumericCellValue();
				// result = String.valueOf(value);
				CellStyle style = cell.getCellStyle();
				DecimalFormat format = new DecimalFormat();
				String temp = style.getDataFormatString();
				if (temp.equals("General")) {
					format.applyPattern("#");
				}
				result = format.format(value);
			}
			break;
		case STRING:
			result = cell.getRichStringCellValue().toString();
			break;
		case BLANK:
			result = null;
			break;
		default:
			result = null;
			break;
		}
		return result;
	}

}
