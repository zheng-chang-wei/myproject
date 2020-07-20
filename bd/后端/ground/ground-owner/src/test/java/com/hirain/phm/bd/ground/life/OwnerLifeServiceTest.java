/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.life;

import java.io.FileOutputStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hirain.phm.bd.ground.TestApplication;
import com.hirain.phm.bd.ground.fault.param.FaultRequest;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jul 10, 2020 9:07:33 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jul 10, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@SpringBootTest(classes = TestApplication.class)
@RunWith(SpringRunner.class)
public class OwnerLifeServiceTest {

	@Autowired
	private OwnerLifeService service;

	@Test
	public void test_export() throws Exception {
		FaultRequest request = new FaultRequest();
		request.setProject("深圳7号线一期");
		request.setTrain("717");
		FileOutputStream stream = new FileOutputStream("D://test//life.xlsx");

		service.exportLifeItems(request, stream);
	}
}
