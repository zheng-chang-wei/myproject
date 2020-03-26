/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.common.csv;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Feb 28, 2020 9:32:24 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Feb 28, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public class CsvExportTest {

	private static String root = System.getProperty("user.dir");

	@Test
	public void testSimple() throws IOException {
		String filename = root + File.separator + "simple.csv";
		CsvExport.write(filename, SimpleData.data(), SimpleData.class);
	}

	@Test
	public void testDateFormat() throws IOException {
		String filename = root + File.separator + "dataformat.csv";
		CsvExport.write(filename, DateFormatData.data(), DateFormatData.class);
	}

	@Test
	public void testIgnore() throws IOException {
		String filename = root + File.separator + "ignore.csv";
		CsvExport.write(filename, IgnoreData.data(), IgnoreData.class);
	}

	@Test
	public void testCustomFormat() throws IOException {
		String filename = root + File.separator + "custom.csv";
		CsvExport.write(filename, CustomFormatData.data(), CustomFormatData.class);
	}

}
