/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.repair.export;

import java.io.File;
import java.io.FileOutputStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.hirain.phm.bd.ground.maintenance.export.WorksheetExportService;
import com.hirain.phm.bd.ground.maintenance.param.WorkSheetQueryParam;
import com.hirain.phm.bd.ground.repair.TestApplication;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Feb 28, 2020 5:00:27 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Feb 28, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class)
@ActiveProfiles("test")
public class WorksheetExportServiceTest {

	@Autowired
	private WorksheetExportService service;

	@Test
	public void test() throws Exception {
		String filename = System.getProperty("user.dir") + File.separator + "service.csv";
		FileOutputStream stream = new FileOutputStream(filename);
		service.export(new WorkSheetQueryParam(), stream);
	}

	@Test(expected = Exception.class)
	public void testException() throws Exception {
		String filename = System.getProperty("user.dir") + File.separator + "service.csv";
		FileOutputStream stream = new FileOutputStream(filename);
		WorkSheetQueryParam param = new WorkSheetQueryParam();
		param.setProject("深圳地铁7号线");
		service.export(param, stream);
	}
}
