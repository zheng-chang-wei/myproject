package com.hirain.phm.synapsis.mvb.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.hirain.phm.synapsis.mvb.rows.ExcelRowLoader;
import com.hirain.phm.synapsis.mvb.rows.ExcelSheetLoader;
import com.hirain.phm.synapsis.util.ExcelUtil;

public class MVBExcelUtil {

	public static Map<Integer, String> readCellValue(final String excelPath, final String sheetName, final int rowNum) throws Exception {
		final Map<Integer, String> mapIndexValue = new HashMap<>();
		final Workbook workbook = ExcelUtil.createWorkbook(excelPath);
		final Sheet sheet = workbook.getSheet(sheetName);
		if (sheet == null) {
			throw new IOException("");
		}
		final Row row = sheet.getRow(rowNum);
		final Iterator<Cell> iterator = row.iterator();
		while (iterator.hasNext()) {
			final Cell cell = iterator.next();
			final String cellValue = ExcelUtil.getCellValue(cell);
			mapIndexValue.put(cell.getColumnIndex(), cellValue);
		}
		return mapIndexValue;
	}

	public static List<Sheet> getSheets(final String excelPath) throws Exception {
		final List<Sheet> sheets = new ArrayList<>();
		final Workbook workbook = ExcelUtil.createWorkbook(excelPath);
		if (workbook == null) {
			return sheets;
		}
		final int numberOfSheets = workbook.getNumberOfSheets();
		for (int i = 0; i < numberOfSheets; i++) {
			final Sheet sheet = workbook.getSheetAt(i);
			sheets.add(sheet);
		}
		return sheets;
	}

	public static ExcelSheetLoader loadExcelSheet(final String excelPath, final Object sheetModel, final ExcelRowLoader rowLoader) throws Exception {
		final ExcelSheetLoader sheetLoader = loadExcelSheet(excelPath, sheetModel, rowLoader, false);
		return sheetLoader;
	}

	public static ExcelSheetLoader loadExcelSheet(final String excelPath, final Object sheetModel, final ExcelRowLoader rowLoader,
			final boolean isIngnoreSpaceWrap) throws Exception {
		final Workbook workbook = ExcelUtil.createWorkbook(excelPath);
		final Sheet sheet = workbook.getSheet(rowLoader.getSheetName());
		if (sheet == null) {
			throw new IOException("");
		}
		final ExcelSheetLoader sheetLoader = new ExcelSheetLoader();
		sheetLoader.setSheet(sheet);
		sheetLoader.setHeader(rowLoader.getHeader());
		sheetLoader.setRowLoader(rowLoader);
		sheetLoader.setSheetModel(sheetModel);
		sheetLoader.setIngnoreSpaceWrap(isIngnoreSpaceWrap);
		sheetLoader.loadSheetModel();
		return sheetLoader;
	}

	/**
	 * 表头中的空格和换行符是否考虑
	 */
	private static boolean isIngnoreSpaceWrap = false;

	/**
	 * 读取sheet页中表头列下面的所有cell单元格数据
	 * 
	 * @param sheet
	 *            sheet页
	 * @param header
	 *            需要读取的sheet页的表头
	 * @return 表头列下面的所有cell单元格数据（有多行，每行有多个单元格）
	 */
	public static Map<Integer, List<String>> getCellValuesGroupByRow(final Sheet sheet, final String[] header) {
		final Map<Integer, List<String>> cellValuesGroupByRowMap = new LinkedHashMap<>();
		if (sheet == null || header == null || header.length == 0) {
			return cellValuesGroupByRowMap;
		}

		final List<Row> headerRows = getHeaderRows(sheet, header);
		for (final Row headerRow : headerRows) {
			final List<Integer> columnIndexs = getColumnIndexs(headerRow, header);
			final int firstCellIndex = getFirstCellIndex(headerRow, header);
			final int startRow = headerRow.getRowNum();// 表头所在行index
			for (int rowIndex = startRow + 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
				Row row = sheet.getRow(rowIndex);
				if (isHeaderRow(row, header)) {
					/* 如果读到表头行，就从跨过这个表头行重新开始读 */
					rowIndex = sheet.getLastRowNum();
					row = null;
				}
				if (row == null) {
					continue;
				}
				final List<String> cellValues = new ArrayList<>();// 一行的单元格数据
				for (int j = 0; j < columnIndexs.size(); j++) {// 遍历此行中所有表头中包含的列
					cellValues.add(ExcelUtil.getCellValue(row.getCell(columnIndexs.get(j) + firstCellIndex)));
				}
				cellValuesGroupByRowMap.put(rowIndex, cellValues);
			}
		}
		return cellValuesGroupByRowMap;
	}

	/**
	 * 表头从Excel中第几列开始
	 * 
	 * @param headerRow
	 * @param header
	 * @return
	 */
	private static int getFirstCellIndex(final Row headerRow, final String[] header) {
		final int firstCellNum = headerRow.getFirstCellNum();// 内容不一定从第一列开始
		Cell cell = headerRow.getCell(firstCellNum);
		String cellValue = ExcelUtil.getCellValue(cell);
		List<String> headerList = Arrays.asList(header);
		if (isIngnoreSpaceWrap) {
			cellValue = cellValue.replaceAll(" ", "").replaceAll("\n", "");
			headerList = getIgnoreSpaceWrapStrs(headerList);
		}
		if (headerList.contains(cellValue)) {
			return firstCellNum;
		} else {
			final Iterator<Cell> cellIterator = headerRow.cellIterator();
			int firstCellNumTemp = Integer.MAX_VALUE;
			while (cellIterator.hasNext()) {
				cell = cellIterator.next();
				cellValue = ExcelUtil.getCellValue(cell);
				if (headerList.contains(cellValue)) {
					final int temp = cell.getColumnIndex() - 1;
					firstCellNumTemp = firstCellNumTemp > temp ? temp : firstCellNumTemp;
				}
			}
			return firstCellNumTemp;
		}
	}

	public static List<String> getIgnoreSpaceWrapStrs(final List<String> strWithSpaceWrap) {
		final ArrayList<String> arrayList = new ArrayList<>();
		for (final String string : strWithSpaceWrap) {
			if (string == null) {
				continue;
			}
			arrayList.add(string.replaceAll(" ", "").replaceAll("\n", ""));
		}
		return arrayList;
	}

	/**
	 * 获取所有的表头行list(可以有N个表头行)
	 * 
	 * @param sheet
	 *            excel的sheet页
	 * @param header
	 *            表头
	 * @return 获取所有的表头行list
	 */
	private static List<Row> getHeaderRows(final Sheet sheet, final String[] header) {
		final List<Row> rows = new ArrayList<>();
		if (sheet == null || header == null || header.length == 0) {
			return rows;
		}
		final Iterator<Row> rowIterator = sheet.rowIterator();
		while (rowIterator.hasNext()) {
			final Row row = rowIterator.next();
			if (isHeaderRow(row, header)) {
				rows.add(row);
			}
		}
		return rows;
	}

	/**
	 * 判断行是否是表头行
	 * 
	 * @param row
	 *            行
	 * @param header
	 *            表头
	 * @return 是否是表头行
	 */
	private static boolean isHeaderRow(final Row row, final String[] header) {
		if (row == null || header == null || header.length == 0) {
			return false;
		}
		final List<String> values = getCellValuesInRow(row);
		if (isIngnoreSpaceWrap) {
			return isContainsAllIgnoreSpaceWrap(values, Arrays.asList(header));
		} else {
			if (values.containsAll(Arrays.asList(header))) {
				return true;
			}
		}
		return false;
	}

	public static boolean isContainsAllIgnoreSpaceWrap(final List<String> whole, final List<String> part) {
		return getIndexContainsAllIgnoreSpaceWrap(whole, part).size() > 0;
	}

	public static List<Integer> getIndexContainsAllIgnoreSpaceWrap(final List<String> whole, final List<String> part) {
		final ArrayList<Integer> arrayListIndex = new ArrayList<>();
		final List<String> wholeTemp = getIgnoreSpaceWrapStrs(whole);
		final List<String> partTemp = getIgnoreSpaceWrapStrs(part);
		if (!wholeTemp.containsAll(partTemp)) {
			return arrayListIndex;
		}
		for (final String string : partTemp) {
			arrayListIndex.add(wholeTemp.indexOf(string));
		}
		return arrayListIndex;
	}

	/**
	 * @param row
	 *            行
	 * @return 这一行的所有单元格数据List(cellValues)
	 */
	private static List<String> getCellValuesInRow(final Row row) {
		final List<String> values = new ArrayList<>();
		if (row == null) {
			return values;
		}
		final Iterator<Cell> cellIterator = row.cellIterator();
		while (cellIterator.hasNext()) {
			final Cell cell = cellIterator.next();
			values.add(ExcelUtil.getCellValue(cell));
		}
		return values;
	}

	/**
	 * 获取表头中某列 和 实际excel表中某列 的对应关系(表头中列的顺序和实际excel中列的顺序没有关系)
	 * 
	 * @param row
	 * @param header
	 *            （header_row3,header_row1,header_row2）
	 * @return （3,1,2）
	 */
	private static List<Integer> getColumnIndexs(final Row row, final String[] header) {
		final List<Integer> indexs = new ArrayList<>();
		if (row == null || header == null || header.length == 0) {
			return indexs;
		}
		final List<String> values = getCellValuesInRow(row);
		if (!isIngnoreSpaceWrap && values.containsAll(Arrays.asList(header))) {
			for (final String clomnName : header) {
				indexs.add(values.indexOf(clomnName));
			}
		} else if (isIngnoreSpaceWrap && isContainsAllIgnoreSpaceWrap(values, Arrays.asList(header))) {
			indexs.addAll(getIndexContainsAllIgnoreSpaceWrap(values, Arrays.asList(header)));
		}
		return indexs;
	}

	/**
	 * @param isIngnoreSpaceWrap
	 *            the isIngnoreSpaceWrap to set
	 */
	public static void setIngnoreSpaceWrap(final boolean isIngnoreSpaceWrap) {
		MVBExcelUtil.isIngnoreSpaceWrap = isIngnoreSpaceWrap;
	}

}
