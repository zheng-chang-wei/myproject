/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.fault.service;

import java.io.OutputStream;
import java.util.List;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jun 28, 2020 6:07:30 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jun 28, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public class ExcelUtils {

	public static <T> void write(OutputStream stream, String sheet, List<T> list, Class<T> clz) {
		WriteCellStyle contentStyle = new WriteCellStyle();
		contentStyle.setWrapped(true);
		HorizontalCellStyleStrategy strategy = new HorizontalCellStyleStrategy(new WriteCellStyle(), contentStyle);
		EasyExcel.write(stream, clz).sheet(sheet).registerWriteHandler(strategy).doWrite(list);
	}

	public static void write(OutputStream stream, ExcelEntry... entries) {
		ExcelWriter excelWriter = null;
		try {
			excelWriter = EasyExcel.write(stream).build();
			for (ExcelEntry entry : entries) {
				WriteSheet sheet = EasyExcel.writerSheet(entry.sheet).head(entry.clz).build();
				excelWriter.write(entry.list, sheet);
			}
		} finally {
			if (excelWriter != null) {
				excelWriter.finish();
			}
		}
	}

	class ExcelEntry {

		String sheet;

		List<?> list;

		Class<?> clz;

		public ExcelEntry(String sheet, List<?> list, Class<?> clz) {
			this.sheet = sheet;
			this.list = list;
			this.clz = clz;
		}
	}
}
