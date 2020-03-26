/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.fault.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.hirain.phm.bd.ground.common.data.BaseValueService;
import com.hirain.phm.bd.ground.fault.dao.FaultDataMapper;
import com.hirain.phm.bd.ground.fault.domain.FaultData;
import com.hirain.phm.bd.ground.fault.domain.FaultDataContainer;
import com.hirain.phm.bd.ground.fault.param.FaultDataResponse;
import com.hirain.phm.bd.ground.fault.service.FaultDataService;
import com.hirain.phm.bd.message.CommonMessage;
import com.hirain.phm.bd.message.decode.KeyValueDecoder;
import com.hirain.phm.bd.message.decode.RunDataFrame;
import com.hirain.phm.bd.message.train.FaultMessage;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 27, 2019 1:52:15 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 27, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Service
@Profile({ "dev", "local" })
public class FaultDataServiceImpl implements FaultDataService {

	@Autowired
	private FaultDataMapper mapper;

	@Autowired
	private BaseValueService baseValueService;

	private KeyValueDecoder decoder = new KeyValueDecoder();

	/**
	 * @see com.hirain.phm.bd.ground.fault.service.FaultDataService#getFaultData(java.lang.Long)
	 */
	@Override
	public FaultDataResponse getFaultData(Long id) {
		try {
			return getFaultData(id, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public FaultDataResponse getFaultData(Long id, List<String> variables) throws Exception {
		FaultDataContainer container = mapper.selectByPrimaryKey(id);
		FaultDataResponse response = new FaultDataResponse();
		List<String> keys = JSONObject.parseArray(container.getKeys(), String.class);
		response.setKeys(keys);
		List<List<String>> baseValues = new ArrayList<>();
		for (String key : keys) {
			baseValues.add(baseValueService.getBaseValue(0, key));
		}
		response.setBaseValues(baseValues);

		List<FaultData> datas = JSONObject.parseArray(container.getDatas(), FaultData.class);
		if (variables != null) {
			List<FaultData> results = filterDatas(variables, keys, datas);
			response.setDatas(results);
		} else {
			response.setDatas(datas);
		}
		return response;
	}

	/**
	 * @throws Exception
	 * @see com.hirain.phm.bd.ground.fault.service.FaultDataService#getDigitalData(long, java.util.List)
	 */
	@Override
	public FaultDataResponse getDigitalData(long faultId, List<String> variables) throws Exception {
		return getFaultData(faultId, variables);
	}

	private List<FaultData> filterDatas(List<String> variables, List<String> keys, List<FaultData> datas) throws Exception {
		List<FaultData> results = new ArrayList<>();
		int[] index = filter(keys, variables);
		for (FaultData data : datas) {
			FaultData result = new FaultData();
			result.setTimestamp(data.getTimestamp());
			List<String> values = new ArrayList<>();
			for (int i = 0; i < index.length; i++) {
				values.add(data.getValues().get(index[i]));
			}
			result.setValues(values);
			results.add(result);
		}
		return results;
	}

	private int[] filter(List<String> keys, List<String> variables) throws Exception {
		int[] index = new int[variables.size()];
		for (int i = 0; i < variables.size(); i++) {
			int j = keys.indexOf(variables.get(i));
			if (j < 0) {
				throw new Exception("不存在变量" + variables.get(i));
			}
			index[i] = j;
		}
		return index;
	}

	/**
	 * @see com.hirain.phm.bd.ground.fault.service.FaultDataService#saveFaultData(java.lang.Long, com.hirain.phm.bd.message.train.FaultMessage)
	 */
	@Override
	public void saveFaultData(Long faultId, FaultMessage faultMessage) {
		FaultDataContainer container = new FaultDataContainer();
		container.setId(faultId);
		container.setState(faultMessage.getState());
		String keys = JSONObject.toJSONString(KeyValueDecoder.keys);
		container.setKeys(keys);
		List<FaultData> datas = new ArrayList<>();
		for (CommonMessage message : faultMessage.getMessages()) {
			RunDataFrame frame = decoder.decode(message);
			FaultData data = new FaultData();
			data.setValues(frame.getValues());
			int index = KeyValueDecoder.keys.indexOf("时间");
			data.setTimestamp(data.getValues().get(index));
			datas.add(data);
		}
		container.setDatas(JSONObject.toJSONString(datas));
		mapper.insert(container);
	}
}
