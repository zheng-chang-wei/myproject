/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.bigdata;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hirain.phm.bd.ground.bigdata.param.DataRecordVO;
import com.hirain.phm.bd.ground.bigdata.param.DataResponse;
import com.hirain.phm.bd.ground.bigdata.service.DataRecordService;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Mar 20, 2020 6:29:15 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Mar 20, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@SpringBootTest(classes = BigDataTestApplication.class)
@RunWith(SpringRunner.class)
public class DataRecordServiceTest {

	@Autowired
	private DataRecordService service;

	@Test
	public void test() throws Exception {
		DataRecordVO vo = new DataRecordVO();
		vo.setProject("上海1号线一期");
		vo.setTrain("1");
		vo.setCarriageId(6);
		vo.setDoorId(6);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		vo.setTimestamp(sdf.parse("2020-03-20 17:43:39"));
		DataResponse response = service.getData(vo, Arrays.asList("编码器"));
		System.out.println(response);
	}
}
