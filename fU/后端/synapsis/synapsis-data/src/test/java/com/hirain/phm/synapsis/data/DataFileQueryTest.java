/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.data;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.hirain.phm.synapsis.data.param.FileTreeNode;
import com.hirain.phm.synapsis.data.service.DataFileService;
import com.hirain.phm.synapsis.exception.SynapsisException;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 18, 2019 4:15:30 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 18, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@SpringBootTest(classes = TestApplication.class)
@RunWith(SpringRunner.class)
@ActiveProfiles("list")
public class DataFileQueryTest {

	private static String root = "D:/test/data";

	// @BeforeClass
	public static void setUp() throws IOException {
		File rootFile = new File(root);
		if (!rootFile.exists()) {
			rootFile.mkdirs();
		}
		for (int month = 10; month < 13; month++) {
			File folder = new File(root, "2019年" + month + "月");
			if (!folder.exists()) {
				folder.mkdirs();
			}
			for (int date = 15; date < 19; date++) {
				File subFolder = new File(folder, date + "日");
				if (!subFolder.exists()) {
					subFolder.mkdirs();
				}
				File file1 = new File(subFolder, folder.getName() + subFolder.getName() + "150000.rt");
				if (!file1.exists()) {
					file1.createNewFile();
				}
				File file2 = new File(subFolder, folder.getName() + subFolder.getName() + "160000.rt");
				if (!file2.exists()) {
					file2.createNewFile();
				}
			}
		}
	}

	// @AfterClass
	public static void tearDown() {
		FileUtils.deleteQuietly(new File(root));
	}

	@Autowired
	private DataFileService service;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

	@Test
	public void test() throws ParseException, SynapsisException {
		Date startDate = sdf.parse("20191101");
		Date endDate = sdf.parse("20191201");
		List<FileTreeNode> nodes = service.listRoot(startDate, endDate);
		assertEquals(2, nodes.size());
	}
}
