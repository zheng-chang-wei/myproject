/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.dictionary;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import com.hirain.phm.bd.ground.dictionary.domain.DoorType;
import com.hirain.phm.bd.ground.dictionary.service.DoorTypeService;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Apr 13, 2020 2:27:44 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Apr 13, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@SpringBootTest(classes = TestApplication.class)
@RunWith(SpringRunner.class)
public class DoorTypeServiceTest {

	@Autowired
	private DoorTypeService service;

	@Test
	public void testBatchInsert() throws IOException {
		DoorType doorType = new DoorType();
		doorType.setId(2);
		service.batchImport(doorType, new MultipartFile() {

			@Override
			public void transferTo(File dest) throws IOException, IllegalStateException {
			}

			@Override
			public boolean isEmpty() {
				return false;
			}

			@Override
			public long getSize() {
				return 0;
			}

			@Override
			public String getOriginalFilename() {
				return null;
			}

			@Override
			public String getName() {
				return null;
			}

			@Override
			public InputStream getInputStream() throws IOException {
				File file = ResourceUtils.getFile("classpath:test.csv");
				return new FileInputStream(file);
			}

			@Override
			public String getContentType() {
				return null;
			}

			@Override
			public byte[] getBytes() throws IOException {
				return null;
			}
		});
	}
}
