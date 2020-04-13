package com.hirain.ptu.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hirain.ptu.common.model.AttributeMappingConfigurationData;
import com.hirain.ptu.model.ComIdData;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.hirain.ptu.common.exception.ExcelFormatException;

import tk.mybatis.mapper.util.StringUtil;

public class OperateExcelUtils {

  /**
   * 获取excel的全部数据
   *
   * @param path
   * @return
   * @throws IOException
   */
  public static void getExcelFileData(String path) throws Exception {
    path = "E:\\01项目相关\\PTU\\英可测试数据--给长伟\\ComID20200217090619_00000111.csv";
    Workbook workbook = ReadExcelUtil.createWorkbook(path);
    Sheet sheet = workbook.getSheetAt(0);
    List<String> firstRowData = new ArrayList<>();
    Row firstRow = sheet.getRow(0);
    for (int i = 0; i < firstRow.getLastCellNum(); i++) {
      firstRowData.add(ReadExcelUtil.getCellValue(firstRow.getCell(i)));
    }
    for (int i = 1; i < sheet.getLastRowNum(); i++) {
      Row row = sheet.getRow(i);
      ComIdData comIdData = new ComIdData();
      for (int j = 0; j < row.getLastCellNum(); j++) {
        String cellValue = ReadExcelUtil.getCellValue(row.getCell(j));
		  String head = firstRowData.get(j);
		  BeanUtils.setProperty(comIdData,  AttributeMappingConfigurationData.comIdMapData.get(head), cellValue);
      }
    }
  }
}
