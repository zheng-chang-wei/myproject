/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.fault;

import java.io.FileOutputStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hirain.phm.bd.ground.TestApplication;
import com.hirain.phm.bd.ground.fault.param.FaultRequest;
import com.hirain.phm.bd.ground.fault.service.OwnerFaultService;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jun 28, 2020 4:55:59 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jun 28, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@SpringBootTest(classes = TestApplication.class)
@RunWith(SpringRunner.class)
public class OwnerFaultServiceTest {

	@Autowired
	private OwnerFaultService service;

	@Test
	public void test_export() throws Exception {
		FaultRequest request = new FaultRequest();
		request.setProject("深圳7号线一期");
		request.setTrain("717");
		FileOutputStream stream = new FileOutputStream("D://test//fault.xlsx");
		service.exportFault(request, stream);
	}
}
