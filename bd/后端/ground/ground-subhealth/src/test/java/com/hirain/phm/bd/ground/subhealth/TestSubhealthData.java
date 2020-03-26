/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.subhealth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.hirain.phm.bd.ground.subhealth.param.SubhealthDataResponse;
import com.hirain.phm.bd.ground.subhealth.service.SubhealthAnalyseService;
import com.hirain.phm.bd.ground.subhealth.service.SubhealthDataService;
import com.hirain.phm.bd.message.train.FaultRawData;
import com.hirain.phm.bd.message.train.SubhealthPacket;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 6, 2020 2:20:48 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 6, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@SpringBootTest(classes = TestApplication.class)
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class TestSubhealthData {

	@Autowired
	private SubhealthAnalyseService service;

	@Autowired
	private SubhealthDataService dataService;

	@Test
	public void test() {
		SubhealthPacket packet = new SubhealthPacket();
		packet.setCarID(1);
		packet.setDoorID(1);
		packet.setCity("深圳");
		packet.setLine("7");
		packet.setProject("深圳7号线一期");
		packet.setTrain("717");
		packet.setState(2);
		packet.setStartTime(new Date());
		packet.setEndTime(new Date());
		packet.setKeys(Arrays.asList("电机电压", "电机电流", "编码器值"));
		List<FaultRawData> datas = new ArrayList<>();
		for (int i = 0; i < 1200; i++) {
			FaultRawData data = new FaultRawData();
			data.setTimestamp(new Date());
			data.setValues(Arrays.asList("1", "1.1", "3000"));
			datas.add(data);
		}
		packet.setDatas(datas);
		packet.setSubhealthItems(Arrays.asList(1));
		service.analyse(packet);
	}

	@Test
	public void testSelect() {
		SubhealthDataResponse response = dataService.getSubhealthData(195);
		System.out.println(response);
	}
}
