/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.excel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.enums.WriteDirectionEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import com.alibaba.excel.write.metadata.fill.FillWrapper;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jul 1, 2020 5:15:49 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jul 1, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public class EasyExcelTest {

	@Test
	public void test_multi_sheet() {
		TestObject obj1 = new TestObject();
		obj1.setSeq(1);
		obj1.setProject("test1");

		TestObject obj2 = new TestObject();
		obj2.setSeq(1);
		obj2.setProject("test2");

		ExcelWriter excelWriter = null;
		try {
			excelWriter = EasyExcel.write("D:/test/test.xlsx", TestObject.class).build();
			WriteSheet sheet1 = EasyExcel.writerSheet("test1").build();
			excelWriter.write(Arrays.asList(obj1), sheet1);

			WriteSheet sheet2 = EasyExcel.writerSheet("test2").build();
			excelWriter.write(Arrays.asList(obj2), sheet2);

		} finally {
			if (excelWriter != null) {
				excelWriter.finish();
			}
		}
	}

	@Test
	public void test_template() {
		String template = "D:\\test\\template.xlsx";
		String fileName = "D:\\test\\template_01.xlsx";
		TestObject obj1 = new TestObject();
		obj1.setSeq(1);
		obj1.setProject("test1");

		TestObject obj2 = new TestObject();
		obj2.setSeq(2);
		obj2.setProject("test2");

		EasyExcel.write(fileName).withTemplate(template).sheet().doFill(Arrays.asList(obj1, obj2));

	}

	@Test
	public void test_template_complex() {
		String template = "D:\\test\\template_multi.xlsx";
		String fileName = "D:\\test\\template_multi_02.xlsx";

		ExcelWriter excelWriter = EasyExcel.write(fileName).withTemplate(template).build();
		Map<String, Object> map = new HashMap<>();
		map.put("project", "深圳7号线一期");
		map.put("train", "717");
		fillSheet(excelWriter, map, 0);
		fillSheet(excelWriter, map, 1);
		excelWriter.finish();
	}

	public void fillSheet(ExcelWriter excelWriter, Map<String, Object> map, int sheetNum) {
		WriteSheet sheet1 = EasyExcel.writerSheet(sheetNum).build();

		FillConfig fillConfig = FillConfig.builder().direction(WriteDirectionEnum.HORIZONTAL).build();

		excelWriter.fill(new FillWrapper("data1", data(sheetNum)), fillConfig, sheet1);
		excelWriter.fill(new FillWrapper("data2", data(sheetNum)), fillConfig, sheet1);

		excelWriter.fill(map, sheet1);

	}

	/**
	 * @param sheetNum
	 * @return
	 */
	private List<TestFaultItem> data(int sheetNum) {
		List<TestFaultItem> items = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			TestFaultItem item = new TestFaultItem();
			item.setFault1(i + sheetNum);
			items.add(item);
		}
		return items;
	}
}
