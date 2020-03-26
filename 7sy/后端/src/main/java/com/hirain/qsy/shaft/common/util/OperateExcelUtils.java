package com.hirain.qsy.shaft.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.hirain.qsy.shaft.common.config.GlobVariableConfig;
import com.hirain.qsy.shaft.common.exception.ExcelFormatException;

import tk.mybatis.mapper.util.StringUtil;

public class OperateExcelUtils {

	/**
	 * 获取excel的全部数据
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static List<List<String>> getExcelFileData(String path) throws Exception {
		List<List<String>> excelData = new ArrayList<>();
		File file = new File(path);
		FileInputStream fStream = new FileInputStream(file); // 文件流对象

		Workbook wb = null;
		if (file.isFile() && file.exists()) {
			String fileExtension = file.getName().substring(file.getName().lastIndexOf(".") + 1); // 获取扩展名
			if ("xls".equals(fileExtension)) {
				wb = new HSSFWorkbook(fStream);
			} else if ("xlsx".equals(fileExtension)) {
				wb = new XSSFWorkbook(fStream);
			} else {
				fStream.close();
				file.delete();
				return excelData;
			}
			Sheet sheet = wb.getSheetAt(0);
			int firstRowIndex = sheet.getFirstRowNum();// 第一行的行号
			int lastRowIndex = sheet.getLastRowNum();// 最后一行的行号
			if (lastRowIndex == 0) {
				wb.close();
				throw new ExcelFormatException("文档为空");
			}
			// 原始表主键所在列
			int originalprimarykeyCelNum = -1;
			Row firstRow = sheet.getRow(0);
			for (int i = 0; i < firstRow.getLastCellNum(); i++) {
				String cellValue = getCellValue(firstRow.getCell(i));
				if (GlobVariableConfig.excelFirstRowTitleList.get(0).equals(cellValue)) {
					originalprimarykeyCelNum = i;
				}
			}
			for (int rIndex = firstRowIndex; rIndex <= lastRowIndex; rIndex++) {
				List<String> rowData = new ArrayList<>();// 存储行信息
				Row row = sheet.getRow(rIndex);
				// 上一行
				Row previousRow = sheet.getRow(rIndex - 1);
				if (previousRow != null && row.getLastCellNum() != previousRow.getLastCellNum()) {
					wb.close();
					throw new ExcelFormatException("每行列数不一致");
				}
				for (int cIndex = 0; cIndex < row.getLastCellNum(); cIndex++) {
					String cellValue = getCellValue(row.getCell(cIndex));
					if (originalprimarykeyCelNum == cIndex && StringUtil.isEmpty(cellValue)) {
						wb.close();
						throw new ExcelFormatException("原始表主键为空");
					}
					rowData.add(cellValue);
				}
				excelData.add(rowData);
				if (rIndex == 0) {
					for (int index = 0; index < GlobVariableConfig.excelFirstRowTitleList.size(); index++) {
						int titleIndex = excelData.get(0).indexOf(GlobVariableConfig.excelFirstRowTitleList.get(index));
						if (titleIndex == -1) {
							wb.close();
							throw new ExcelFormatException("缺少列：" + GlobVariableConfig.excelFirstRowTitleList.get(index));
						}
					}
				}
			}
		}
		wb.close();
		return excelData;
	}

	public static String getCellValue(Cell cell) {

		String result = new String();
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
			result = "";
			break;
		default:
			result = "";
			break;
		}
		return result;
	}

}
