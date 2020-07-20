/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.subhealth;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hirain.phm.bd.ground.subhealth.service.impl.SubhealthInfoCopyService;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Apr 16, 2020 12:02:35 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Apr 16, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@SpringBootTest(classes = TestApplication.class)
@RunWith(SpringRunner.class)
public class TestSubhealthInfoCopy {

	@Autowired
	private SubhealthInfoCopyService service;

	@Test
	public void test() {
		service.copy("深圳7号线一期", "上海1号线一期");
	}
}
