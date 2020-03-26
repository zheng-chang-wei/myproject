/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.subhealth.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.hirain.phm.bd.ground.common.data.BaseValueService;
import com.hirain.phm.bd.ground.subhealth.domain.SubhealthData;
import com.hirain.phm.bd.ground.subhealth.param.SubhealthDataResponse;
import com.hirain.phm.bd.ground.subhealth.service.SubhealthDataService;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Feb 20, 2020 1:43:34 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Feb 20, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Service
@Profile("test")
public class TestSubhealthDataService implements SubhealthDataService {

	private List<String> keys = Arrays.asList("电机电压", "电机电流", "编码器值", "开门时间", "关门时间");

	private List<SubhealthData> datas;

	@Autowired
	private BaseValueService baseValueService;

	@Override
	public SubhealthDataResponse getSubhealthData(int detailId) {
		SubhealthDataResponse response = new SubhealthDataResponse();
		response.setId(detailId);
		response.setKeys(keys);
		if (datas == null) {
			datas = new ArrayList<>();
			for (int i = 0; i < 1200; i++) {
				SubhealthData data = new SubhealthData();
				data.setTimestamp(new Date());
				data.setValues(Arrays.asList("1", "1.5", "3000", "1500", "3480"));
				datas.add(data);
			}
		}
		response.setDatas(datas);
		List<List<String>> baseValues = new ArrayList<>();
		for (String key : keys) {
			baseValues.add(baseValueService.getBaseValue(0, key));
		}
		response.setBaseValues(baseValues);
		response.setState("关门");
		return response;
	}

	/**
	 * @see com.hirain.phm.bd.ground.fault.service.FaultDataService#saveFaultData(java.lang.Long, com.hirain.phm.bd.message.train.FaultMessage)
	 */
	@Override
	public void saveData(Integer id, int state, String keys, String datas) {
		// do nothing
	}

	@Override
	public SubhealthDataResponse getDigitalData(int detailId, List<String> variables) {
		SubhealthDataResponse response = new SubhealthDataResponse();
		response.setId(detailId);
		response.setKeys(variables);
		List<SubhealthData> datas = new ArrayList<>();
		Date date = new Date();
		for (int i = 0; i < 1200; i++) {
			SubhealthData data = new SubhealthData();
			data.setTimestamp(cal(date, i));
			List<String> values = new ArrayList<>();
			for (int j = 0; j < variables.size(); j++) {
				if (i > 500) {
					values.add("1");
				} else {
					values.add("0");
				}
			}
			data.setValues(values);
			datas.add(data);
		}
		response.setDatas(datas);
		return response;
	}

	private Date cal(Date start, int index) {
		long time = start.getTime();
		long next = time + index * 50;
		return new Date(next);
	}

	/**
	 * @see com.hirain.phm.bd.ground.subhealth.service.SubhealthDataService#getSubhealthData(int, java.util.List)
	 */
	@Override
	public SubhealthDataResponse getSubhealthData(int subhealthId, List<String> variables) throws Exception {
		return getSubhealthData(subhealthId);
	}

}
