/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.repair.export;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.hirain.phm.bd.ground.maintenance.export.CsvExportTool;
import com.hirain.phm.bd.ground.maintenance.export.ExportTool;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Feb 27, 2020 2:17:58 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Feb 27, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public class ExportToolTest {

	private ExportTool tool;

	@Before
	public void setUp() {
		tool = new CsvExportTool();
	}

	@Test
	public void test() throws Exception {
		String filename = System.getProperty("user.dir") + File.separator + "test.csv";
		tool.write(filename, data(), TestData.class);
	}

	@Test
	public void testStream() throws Exception {
		String filename = System.getProperty("user.dir") + File.separator + "output.csv";
		FileOutputStream stream = new FileOutputStream(filename);
		List<TestData> list = data();
		for (TestData data : list) {
			data.setB(true);
		}
		tool.write(stream, list, TestData.class);
	}

	/**
	 * @return
	 */
	private List<TestData> data() {
		List<TestData> datas = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			TestData data = new TestData();
			data.setValue("序号" + (i + 1));
			data.setTimestamp(new Date());
			data.setDate(new Date());
			data.setOther("ignore");
			data.setNoproperty("noproperty");
			datas.add(data);
		}
		return datas;
	}
}
