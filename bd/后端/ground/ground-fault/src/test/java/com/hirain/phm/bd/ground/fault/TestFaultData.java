/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.fault;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSONObject;
import com.hirain.phm.bd.ground.fault.dao.FaultDataMapper;
import com.hirain.phm.bd.ground.fault.domain.FaultData;
import com.hirain.phm.bd.ground.fault.domain.FaultDataContainer;
import com.hirain.phm.bd.ground.fault.param.FaultDataResponse;
import com.hirain.phm.bd.ground.fault.service.FaultDataService;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 27, 2019 3:46:51 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 27, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@SpringBootTest(classes = TestApplication.class)
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class TestFaultData {

	@Autowired
	private FaultDataMapper mapper;

	@Autowired
	private FaultDataService service;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");

	@Test
	@Ignore
	public void testInsert() {
		FaultDataContainer container = new FaultDataContainer();
		container.setId(1l);
		container.setState(1);
		List<String> keys = new ArrayList<>();
		keys.add("电机电压");
		keys.add("电机电流");
		keys.add("编码器值");
		String keyJson = JSONObject.toJSONString(keys);
		container.setKeys(keyJson);
		List<FaultData> datas = new ArrayList<>();
		for (int i = 0; i < 1200; i++) {
			FaultData data = new FaultData();
			data.setTimestamp(sdf.format(new Date()));
			data.setValues(Arrays.asList("1", "1.1", "3000"));
			datas.add(data);
		}
		String dataJson = JSONObject.toJSONString(datas);
		container.setDatas(dataJson);
		mapper.insert(container);
	}

	@Test
	public void testSelect() {
		FaultDataResponse response = service.getFaultData(1l);
		System.out.println(response.getKeys());
		System.out.println(response.getBaseValues());
	}

	@Test
	public void testSelectDigitalDatas() throws Exception {
		FaultDataResponse response = service.getDigitalData(1l, Arrays.asList("变量1", "变量2"));
		assertNotNull(response);
		assertEquals(2, response.getKeys().size());
		System.out.println(response);
	}
}
