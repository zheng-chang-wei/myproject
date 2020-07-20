/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.authority.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hirain.phm.bd.ground.authority.domain.Dept;
import com.hirain.phm.bd.ground.authority.domain.GroundRole;
import com.hirain.phm.bd.ground.authority.domain.User;
import com.hirain.phm.bd.ground.authority.service.DeptService;
import com.hirain.phm.bd.ground.authority.service.GroundRoleService;
import com.hirain.phm.bd.ground.authority.service.UserExcelService;

import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.util.StringUtil;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Jul 25, 2019 9:33:19 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jul 25, 2019 zepei.tao@hirain.com 1.0 create file
 */
@Service
@Slf4j
public class UserExcelServiceImpl implements UserExcelService {

	@Autowired
	private DeptService deptService;

	@Autowired
	private GroundRoleService groundRoleService;

	private static final String SUFFIX_2003 = ".xls";

	private static final String SUFFIX_2007 = ".xlsx";

	private static final String SHEET_NAME = "用户表";

	private static final String[] HEADER = new String[] { "用户名", "密码", "权限", "姓名", "部门", "邮箱", "手机号码", "微信" };

	public static final String MOBILE_NUMBER_REG = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[01356789]|18[0-9]|19[89])\\d{8}$";

	public static final String EMAIL_REG = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";

	@Override
	public List<User> resolveExcel(MultipartFile file) throws Exception {
		List<User> users = new ArrayList<>();
		if (file == null) {
			throw new Exception("文件不能为空");
		}
		// 获取文件的名字
		String originalFileName = file.getOriginalFilename();
		Workbook workbook = null;
		try {
			if (originalFileName.endsWith(SUFFIX_2003)) {
				workbook = new HSSFWorkbook(file.getInputStream());
			} else if (originalFileName.endsWith(SUFFIX_2007)) {
				workbook = new XSSFWorkbook(file.getInputStream());
			}
		} catch (Exception e) {
			log.info(originalFileName);
			throw new Exception("文件格式错误");
		}
		if (workbook == null) {
			log.info(originalFileName);
			throw new Exception("文件格式错误");
		} else {
			Sheet sheet = workbook.getSheet(SHEET_NAME);
			if (sheet == null) {
				throw new Exception("sheet页名称错误");
			}
			users = getUsers(sheet, HEADER);
		}
		return users;
	}

	/**
	 * 读取sheet页中表头列下面的所有cell单元格数据
	 * 
	 * @param sheet
	 *            sheet页
	 * @param header
	 *            需要读取的sheet页的表头
	 * @return 表头列下面的所有cell单元格数据（有多行，每行有多个单元格）
	 */
	private List<User> getUsers(final Sheet sheet, final String[] header) throws Exception {
		List<User> users = new ArrayList<>();
		final List<Row> headerRows = getHeaderRows(sheet, header);
		for (final Row headerRow : headerRows) {
			final List<Integer> columnIndexs = getColumnIndexs(headerRow, header);
			final int firstCellIndex = getFirstCellIndex(headerRow, header);
			final int startRow = headerRow.getRowNum();// 表头所在行index
			OUT_LOOP: for (int rowIndex = startRow + 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
				Row row = sheet.getRow(rowIndex);
				if (isHeaderRow(row, header)) {
					/* 如果读到表头行，就从跨过这个表头行重新开始读 */
					rowIndex = sheet.getLastRowNum();
					row = null;
				}
				if (row == null) {
					continue;
				}
				User user = new User();
				for (int j = 0; j < columnIndexs.size(); j++) {// 遍历此行中所有表头中包含的列
					Cell cell = row.getCell(columnIndexs.get(j) + firstCellIndex);
					String cellValue = cell.getStringCellValue();
					if (StringUtil.isEmpty(cellValue)) {
						continue OUT_LOOP;
					}
					switch (j) {
					case 0:
						user.setUserName(cellValue);
						break;
					case 1:
						user.setPassword(cellValue);
						break;
					case 2:
						GroundRole groundRole = groundRoleService.findByName(cellValue);
						if (groundRole == null) {
							throw new Exception("请检查用户表中的角色设置是否正确！");
						}
						user.setGroundRoles(Arrays.asList(groundRole));
						break;
					case 3:
						user.setName(cellValue);
						break;
					case 4:
						Dept dept = deptService.findByName(cellValue);
						if (dept == null) {
							throw new Exception("请检查用户表中的部门设置是否正确！");
						}
						user.setDeptId(dept.getDeptId());
						break;
					case 5:
						if (!Pattern.matches(EMAIL_REG, cellValue)) {
							throw new Exception("邮箱格式错误");
						}
						user.setEmail(cellValue);
						break;
					case 6:
						if (!Pattern.matches(MOBILE_NUMBER_REG, cellValue)) {
							throw new Exception("手机号码格式错误");
						}
						user.setMobile(cellValue);
						break;
					case 7:
						user.setWechat(cellValue);
						break;
					}
				}
				users.add(user);
			}
		}
		return users;
	}

	/**
	 * 获取表头中某列 和 实际excel表中某列 的对应关系(表头中列的顺序和实际excel中列的顺序没有关系)
	 * 
	 * @param row
	 * @param header
	 *            （header_row3,header_row1,header_row2）
	 * @return （3,1,2）
	 */
	private List<Integer> getColumnIndexs(final Row row, final String[] header) {
		final List<Integer> indexs = new ArrayList<>();
		if (row == null || header == null || header.length == 0) {
			return indexs;
		}
		final List<String> values = getCellValuesInRow(row);
		if (values.containsAll(Arrays.asList(header))) {
			for (final String clomnName : header) {
				indexs.add(values.indexOf(clomnName));
			}
		}
		return indexs;
	}

	/**
	 * 表头从Excel中第几列开始
	 * 
	 * @param headerRow
	 * @param header
	 * @return
	 */
	private int getFirstCellIndex(final Row headerRow, final String[] header) {
		final int firstCellNum = headerRow.getFirstCellNum();// 内容不一定从第一列开始
		Cell cell = headerRow.getCell(firstCellNum);
		String cellValue = cell.getStringCellValue();
		List<String> headerList = Arrays.asList(header);
		if (headerList.contains(cellValue)) {
			return firstCellNum;
		} else {
			final Iterator<Cell> cellIterator = headerRow.cellIterator();
			int firstCellNumTemp = Integer.MAX_VALUE;
			while (cellIterator.hasNext()) {
				cell = cellIterator.next();
				cellValue = cell.getStringCellValue();
				if (headerList.contains(cellValue)) {
					final int temp = cell.getColumnIndex() - 1;
					firstCellNumTemp = firstCellNumTemp > temp ? temp : firstCellNumTemp;
				}
			}
			return firstCellNumTemp;
		}
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
	private List<Row> getHeaderRows(final Sheet sheet, final String[] header) {
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
	private boolean isHeaderRow(final Row row, final String[] header) {
		if (row == null || header == null || header.length == 0) {
			return false;
		}
		final List<String> values = getCellValuesInRow(row);
		if (values.containsAll(Arrays.asList(header))) {
			return true;
		}
		return false;
	}

	/**
	 * @param row
	 *            行
	 * @return 这一行的所有单元格数据List(cellValues)
	 */
	private List<String> getCellValuesInRow(final Row row) {
		final List<String> values = new ArrayList<>();
		if (row == null) {
			return values;
		}
		final Iterator<Cell> cellIterator = row.cellIterator();
		while (cellIterator.hasNext()) {
			final Cell cell = cellIterator.next();
			// cell.setCellType(Cell.CELL_TYPE_STRING);
			cell.setCellType(CellType.STRING);
			values.add(cell.getStringCellValue());
		}
		return values;
	}

}
