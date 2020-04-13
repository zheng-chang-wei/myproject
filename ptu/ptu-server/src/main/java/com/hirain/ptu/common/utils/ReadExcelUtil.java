package com.hirain.ptu.common.utils;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReadExcelUtil {

  public static Workbook createWorkbook(String excelPath) throws Exception {
    InputStream is = null;
    Workbook wb = null;
    try {
      File sourcefile = new File(excelPath);
      is = new FileInputStream(sourcefile);
      wb = WorkbookFactory.create(is);
      if (wb instanceof XSSFWorkbook) {
        wb = (XSSFWorkbook) wb;
      } else if (wb instanceof HSSFWorkbook) {
        wb = (HSSFWorkbook) wb;
      }
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

  public static Workbook createWorkbook(InputStream is) throws Exception {
    Workbook wb = null;
    try {
      wb = WorkbookFactory.create(is);
      if (wb instanceof XSSFWorkbook) {
        wb = (XSSFWorkbook) wb;
      } else if (wb instanceof HSSFWorkbook) {
        wb = (HSSFWorkbook) wb;
      }
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
      default:
        result = "";
        break;
    }
    return result;
  }
}
