/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.digital.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirain.phm.bd.ground.digital.dao.DigitalTwinParamMapper;
import com.hirain.phm.bd.ground.digital.dao.DigitalTwinResultMapper;
import com.hirain.phm.bd.ground.digital.domain.DigitalTwinPacket;
import com.hirain.phm.bd.ground.digital.domain.DigitalTwinParam;
import com.hirain.phm.bd.ground.digital.domain.DigitalTwinResult;
import com.hirain.phm.bd.ground.digital.param.DayValue;
import com.hirain.phm.bd.ground.digital.param.ParamResult;
import com.hirain.phm.bd.ground.digital.param.ResultQueryRequest;
import com.hirain.phm.bd.ground.digital.param.ResultQueryResponse;
import com.hirain.phm.bd.ground.digital.service.DigitalTwinService;
import com.hirain.phm.bd.ground.train.controller.TrainGateWay;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Sep 25, 2019 10:52:24 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Sep 25, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Service
public class DigitalTwinServiceImpl implements DigitalTwinService {

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	@Autowired
	private DigitalTwinParamMapper paramMapper;

	@Autowired
	private DigitalTwinResultMapper resultMapper;

	@Autowired
	private TrainGateWay trainGW;

	/**
	 * @see com.hirain.phm.bd.ground.digital.service.DigitalTwinService#update(com.hirain.phm.bd.ground.digital.domain.DigitalTwinPacket)
	 */
	@Override
	public void update(DigitalTwinPacket packet) {
		List<DigitalTwinResult> results = transform(packet);
		resultMapper.insertList(results);
	}

	/**
	 * @param packet
	 * @return
	 */
	private List<DigitalTwinResult> transform(DigitalTwinPacket packet) {
		List<DigitalTwinResult> results = new ArrayList<>();
		Map<String, DigitalTwinParam> paramMap = getParamMap();
		DigitalTwinResult defaultResult = getDefault(packet);
		results.addAll(transformPara(packet.getMotorPara(), paramMap, defaultResult));
		results.addAll(transformPara(packet.getMechPara(), paramMap, defaultResult));
		return results;
	}

	/**
	 * @param packet
	 * @return
	 */
	private DigitalTwinResult getDefault(DigitalTwinPacket packet) {
		int trainId = getTrainId(packet.getProject(), packet.getTrainID());
		DigitalTwinResult result = new DigitalTwinResult();
		result.setCarId(packet.getCarID());
		result.setDoorId(packet.getDoorID());
		result.setTrainId(trainId);
		result.setTimestamp(packet.getDate());
		return result;
	}

	/**
	 * @param paramMap
	 * @param defaultResult
	 * @param motorPara
	 */
	private List<DigitalTwinResult> transformPara(Map<String, Double> paras, Map<String, DigitalTwinParam> paramMap,
			DigitalTwinResult defaultResult) {
		List<DigitalTwinResult> results = new ArrayList<>();
		paras.keySet().forEach(key -> {
			DigitalTwinResult result = new DigitalTwinResult();
			result.setCarId(defaultResult.getCarId());
			result.setDoorId(defaultResult.getDoorId());
			result.setTrainId(defaultResult.getTrainId());
			result.setTimestamp(defaultResult.getTimestamp());
			result.setParamId(paramMap.get(key).getId());
			result.setParamValue(paras.get(key));
			results.add(result);
		});
		return results;
	}

	private int getTrainId(String project, String train) {
		Integer trainId = trainGW.selectTrain(project, train).getId();
		return trainId;
	}

	private Map<String, DigitalTwinParam> getParamMap() {
		List<DigitalTwinParam> params = paramMapper.selectAll();
		Map<String, DigitalTwinParam> map = params.stream().collect(Collectors.toMap(DigitalTwinParam::getKey, param -> param));
		return map;
	}

	/**
	 * @see com.hirain.phm.bd.ground.digital.service.DigitalTwinService#getAllResults(ResultQueryResult)
	 */
	@Override
	public ResultQueryResponse getAllResults(ResultQueryRequest request) {
		List<DigitalTwinParam> params = paramMapper.selectAll();
		return getResultsByParams(request, params);
	}

	@Override
	public ResultQueryResponse getResultsByType(ResultQueryRequest request, String type) {
		List<DigitalTwinParam> params = paramMapper.selectByType(type);
		return getResultsByParams(request, params);
	}

	@Override
	public ResultQueryResponse getResultsByParamName(ResultQueryRequest request, String param) {
		List<DigitalTwinParam> params = paramMapper.selectbyParamName(param);
		return getResultsByParams(request, params);
	}

	private ResultQueryResponse getResultsByParams(ResultQueryRequest request, List<DigitalTwinParam> params) {
		Map<DigitalTwinParam, List<DigitalTwinResult>> resultMap = getResultMap(request, params);
		return generateResponse(resultMap);
	}

	private Map<DigitalTwinParam, List<DigitalTwinResult>> getResultMap(ResultQueryRequest request, List<DigitalTwinParam> params) {
		DigitalTwinResult condition = getQueryCondition(request);
		Map<DigitalTwinParam, List<DigitalTwinResult>> resultMap = new HashMap<>();
		params.forEach(param -> {
			condition.setParamId(param.getId());
			List<DigitalTwinResult> results = resultMapper.selectByCondition(condition, request.getStart(), request.getEnd());
			resultMap.put(param, results);
		});
		return resultMap;
	}

	/**
	 * @param request
	 * @return
	 */
	private DigitalTwinResult getQueryCondition(ResultQueryRequest request) {
		DigitalTwinResult condition = new DigitalTwinResult();
		condition.setCarId(request.getCarId());
		condition.setDoorId(request.getDoorId());
		condition.setTrainId(getTrainId(request.getProject(), request.getTrain()));
		return condition;
	}

	/**
	 * @param resultMap
	 */
	private ResultQueryResponse generateResponse(Map<DigitalTwinParam, List<DigitalTwinResult>> resultMap) {
		List<ParamResult> results = new ArrayList<>();
		resultMap.keySet().forEach(param -> {
			List<DayValue> values = getDayValues(resultMap.get(param));
			ParamResult paramResult = new ParamResult();
			paramResult.setParam(param);
			paramResult.setValues(values);
			results.add(paramResult);
		});
		ResultQueryResponse response = new ResultQueryResponse();
		response.setResults(results);
		return response;
	}

	/**
	 * @param list
	 * @return
	 */
	private List<DayValue> getDayValues(List<DigitalTwinResult> list) {
		List<DayValue> values = new ArrayList<>();
		list.forEach(l -> {
			DayValue value = new DayValue();
			value.setDay(sdf.format(l.getTimestamp()));
			value.setValue(l.getParamValue());
			values.add(value);
		});
		return values;
	}

}
