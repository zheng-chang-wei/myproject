/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.fault.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.hirain.phm.bd.ground.dictionary.service.BaseValueService;
import com.hirain.phm.bd.ground.fault.domain.FaultData;
import com.hirain.phm.bd.ground.fault.param.FaultDataResponse;
import com.hirain.phm.bd.ground.fault.service.FaultDataService;
import com.hirain.phm.bd.message.train.FaultMessage;

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
public class TestFaultDataService implements FaultDataService {

	private List<String> keys = Arrays.asList("电机电压", "电机电流", "编码器值", "开门时间", "关门时间");

	private List<FaultData> datas;

	@Autowired
	private BaseValueService baseValueService;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");

	/**
	 * @see com.hirain.phm.bd.ground.fault.service.FaultDataService#getFaultData(java.lang.Long)
	 */
	@Override
	public FaultDataResponse getFaultData(Long id) {
		FaultDataResponse response = new FaultDataResponse();
		response.setId(id);
		response.setKeys(keys);
		if (datas == null) {
			datas = new ArrayList<>();
			for (int i = 0; i < 1200; i++) {
				FaultData data = new FaultData();
				data.setTimestamp(sdf.format(new Date()));
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
	public void saveFaultData(Long faultId, FaultMessage faultMessage) {
		// do nothing
	}

	/**
	 * @see com.hirain.phm.bd.ground.fault.service.FaultDataService#getDigitalData(long, java.util.List)
	 */
	@Override
	public FaultDataResponse getDigitalData(long faultId, List<String> variables) {
		FaultDataResponse response = new FaultDataResponse();
		response.setId(faultId);
		response.setKeys(variables);
		List<FaultData> datas = new ArrayList<>();
		Date date = new Date();
		for (int i = 0; i < 1200; i++) {
			FaultData data = new FaultData();
			data.setTimestamp(sdf.format(cal(date, i)));
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
	 * @see com.hirain.phm.bd.ground.fault.service.FaultDataService#getFaultData(java.lang.Long, java.util.List)
	 */
	@Override
	public FaultDataResponse getFaultData(Long id, List<String> variables) throws Exception {
		return getFaultData(id);
	}

}
