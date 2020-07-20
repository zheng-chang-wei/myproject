/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.common.csv;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import com.hirain.common.csv.annotation.CsvIgnore;
import com.hirain.common.csv.annotation.CsvProperty;
import com.hirain.common.csv.annotation.DateFormat;
import com.hirain.common.csv.converter.DateValueConverter;
import com.hirain.common.csv.converter.DefaultValueConverter;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Feb 27, 2020 2:28:58 PM
 * @Description
 *              <p>
 *              CSV导出工具
 *              <p>
 *              支持自定义格式，支持自定义表头
 *              <p>
 *              注意：不支持嵌套对象
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Feb 27, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public class CsvExport {

	private CsvExport() {
	}

	/**
	 * @throws IOException
	 * @see com.hirain.phm.bd.ground.maintenance.export.ExportTool#write(java.lang.String, java.util.List)
	 */
	public static void write(String filename, List<?> list, Class<?> clz) throws IOException {
		File file = new File(filename);
		FileUtils.writeLines(file, "GBK", getLines(list, clz));
	}

	/**
	 * @param stream
	 * @param data
	 * @param clz
	 * @throws Exception
	 */
	public static void write(OutputStream stream, List<?> data, Class<?> clz) throws Exception {
		write(stream, data, clz, "GBK");
	}

	public static void write(OutputStream stream, List<?> data, Class<?> clz, String encoding) throws Exception {
		List<String> lines = getLines(data, clz);
		IOUtils.writeLines(lines, System.lineSeparator(), stream, Charset.forName(encoding));
	}

	private static List<String> getLines(List<?> list, Class<?> clz) {
		List<String> lines = new ArrayList<>();
		List<CsvDataProperty> properties = getProperties(clz);
		StringBuilder sb = new StringBuilder();
		for (CsvDataProperty property : properties) {
			sb.append(property.name()).append(",");
		}
		lines.add(sb.toString());
		for (Object object : list) {
			sb = new StringBuilder();
			for (CsvDataProperty property : properties) {
				sb.append(property.valueOf(object)).append(",");
			}
			lines.add(sb.toString());
		}
		return lines;
	}

	private static List<CsvDataProperty> getProperties(Class<?> clz) {
		List<CsvDataProperty> properties = new ArrayList<>();
		Field[] fields = clz.getDeclaredFields();

		ConverterManager manager = new ConverterManager();
		for (Field field : fields) {
			field.setAccessible(true);
			CsvIgnore ignore = field.getAnnotation(CsvIgnore.class);
			if (ignore != null) {
				continue;
			}
			CsvProperty property = field.getAnnotation(CsvProperty.class);
			CsvDataProperty dataProperty = new CsvDataProperty();
			dataProperty.setField(field);
			dataProperty.setName(property == null ? field.getName() : property.value());
			DateFormat format = field.getAnnotation(DateFormat.class);
			if (format == null) {
				dataProperty.setConverter(manager.getConverter(property == null ? DefaultValueConverter.class : property.converter()));
			} else {
				dataProperty.setConverter(new DateValueConverter(format.pattern()));
			}
			properties.add(dataProperty);
		}
		return properties;
	}

}
