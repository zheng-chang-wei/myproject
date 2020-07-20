/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.subhealth.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.hirain.phm.bd.ground.dictionary.service.BaseValueService;
import com.hirain.phm.bd.ground.subhealth.dao.SubhealthDataMapper;
import com.hirain.phm.bd.ground.subhealth.domain.SubhealthData;
import com.hirain.phm.bd.ground.subhealth.domain.SubhealthDataContainer;
import com.hirain.phm.bd.ground.subhealth.param.SubhealthDataResponse;
import com.hirain.phm.bd.ground.subhealth.service.SubhealthDataService;
import com.hirain.phm.bd.message.train.DoorState;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 6, 2020 2:06:42 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 6, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Service
@Profile({ "dev", "local" })
public class SubhealthDataServiceImpl implements SubhealthDataService {

	@Autowired
	private SubhealthDataMapper mapper;

	@Autowired
	private BaseValueService baseValueService;

	/**
	 * @see com.hirain.phm.bd.ground.subhealth.service.SubhealthDataService#saveData(java.lang.Integer, int, java.lang.String, java.lang.String)
	 */
	@Override
	public void saveData(Integer id, int state, String keys, String datas) {
		SubhealthDataContainer container = new SubhealthDataContainer();
		container.setId(id);
		container.setKeys(keys);
		container.setState(state);
		container.setDatas(datas);
		mapper.insert(container);
	}

	/**
	 * @see com.hirain.phm.bd.ground.subhealth.service.SubhealthDataService#getSubhealthData(int)
	 */
	@Override
	public SubhealthDataResponse getSubhealthData(int detailId) {
		try {
			return getSubhealthData(detailId, null);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public SubhealthDataResponse getDigitalData(int subhealthId, List<String> variables) throws Exception {
		return getSubhealthData(subhealthId, variables);
	}

	/**
	 * @param subhealthId
	 * @param variables
	 * @return
	 * @throws Exception
	 */
	@Override
	public SubhealthDataResponse getSubhealthData(int subhealthId, List<String> variables) throws Exception {
		SubhealthDataContainer container = mapper.selectByPrimaryKey(subhealthId);
		SubhealthDataResponse response = new SubhealthDataResponse();
		if (container != null) {
			List<String> keys = JSONObject.parseArray(container.getKeys(), String.class);
			response.setId(subhealthId);
			response.setState(DoorState.values()[container.getState()].getMessage());
			response.setKeys(variables);
			List<List<String>> baseValues = new ArrayList<>();
			for (String key : variables) {
				baseValues.add(baseValueService.getBaseValue(0, key));
			}
			response.setBaseValues(baseValues);

			List<SubhealthData> datas = JSONObject.parseArray(container.getDatas(), SubhealthData.class);
			if (variables != null) {
				response.setDatas(filterDatas(variables, keys, datas));
			} else {
				response.setDatas(datas);
			}
		}
		return response;
	}

	/**
	 * @param variables
	 * @param keys
	 * @param datas
	 * @return
	 * @throws Exception
	 */
	private List<SubhealthData> filterDatas(List<String> variables, List<String> keys, List<SubhealthData> datas) throws Exception {
		List<SubhealthData> results = new ArrayList<>();
		int[] index = filter(keys, variables);
		for (SubhealthData data : datas) {
			SubhealthData result = new SubhealthData();
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
}
