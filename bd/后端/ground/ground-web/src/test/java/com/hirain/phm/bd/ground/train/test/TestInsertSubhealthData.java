/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.train.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSONObject;
import com.hirain.phm.bd.ground.GroundWebApplication;
import com.hirain.phm.bd.ground.subhealth.dao.SubhealthDataMapper;
import com.hirain.phm.bd.ground.subhealth.dao.SubhealthDetailMapper;
import com.hirain.phm.bd.ground.subhealth.domain.SubhealthDataContainer;
import com.hirain.phm.bd.ground.subhealth.domain.SubhealthDetail;
import com.hirain.phm.bd.ground.subhealth.service.SubhealthDataService;
import com.hirain.phm.bd.message.train.FaultRawData;

/**
 * @Version 1.0
 * @Author changwei.zheng@hirain.com
 * @Created 2020年1月7日 上午9:50:35
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2020年1月7日 changwei.zheng@hirain.com 1.0 create file
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GroundWebApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestInsertSubhealthData {

	@Autowired
	private SubhealthDetailMapper subhealthDetailMapper;

	@Autowired
	SubhealthDataService dataService;

	@Autowired
	private SubhealthDataMapper subhealthDataMapper;

	@Test
	public void test() {
		List<SubhealthDataContainer> subhealthDataContainers = subhealthDataMapper.selectAll();
		for (SubhealthDataContainer subhealthDataContainer : subhealthDataContainers) {
			subhealthDataMapper.deleteByPrimaryKey(subhealthDataContainer);
		}
		List<SubhealthDetail> details = subhealthDetailMapper.selectAll();
		for (SubhealthDetail subhealthDetail : details) {
			// 电机电压，电机电流，编码器值
			List<String> keys = Arrays.asList(new String[] { "电机电压", "电机电流", "编码器值", "test1", "test2" });
			int state = 0;
			List<FaultRawData> datas = new ArrayList<>();
			for (int i = state; i < 50; i++) {
				FaultRawData data = new FaultRawData();
				data.setTimestamp(new Date());
				List<String> list = new ArrayList<>();
				for (int j = state; j < keys.size(); j++) {
					list.add(String.valueOf(j + 1));
				}
				data.setValues(list);
				datas.add(data);
			}
			dataService.saveData(subhealthDetail.getId(), state, JSONObject.toJSONString(keys), JSONObject.toJSONString(datas));
		}
	}

}
