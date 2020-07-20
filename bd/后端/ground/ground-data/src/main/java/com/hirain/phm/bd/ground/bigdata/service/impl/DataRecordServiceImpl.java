/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.bigdata.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirain.phm.bd.common.serialize.JsonUtil;
import com.hirain.phm.bd.data.DataQueryService;
import com.hirain.phm.bd.data.bean.DataParam;
import com.hirain.phm.bd.data.bean.DataRecord;
import com.hirain.phm.bd.ground.bigdata.param.DataRecordResponse;
import com.hirain.phm.bd.ground.bigdata.param.DataRecordVO;
import com.hirain.phm.bd.ground.bigdata.param.DataRequest;
import com.hirain.phm.bd.ground.bigdata.param.DataResponse;
import com.hirain.phm.bd.ground.bigdata.param.RawData;
import com.hirain.phm.bd.ground.bigdata.service.DataRecordService;
import com.hirain.phm.bd.ground.bigdata.service.DataRedisService;
import com.hirain.phm.bd.ground.dictionary.service.BaseValueService;
import com.hirain.phm.bd.ground.train.controller.ProjectGateWay;
import com.hirain.phm.bd.ground.train.domain.Project;
import com.hirain.phm.bd.message.data.RecordData;
import com.hirain.phm.bd.message.decode.RunDataFrame;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Mar 5, 2020 2:46:09 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Mar 5, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Service
public class DataRecordServiceImpl implements DataRecordService {

	@Autowired
	private DataQueryService service;

	@Autowired
	private DataRedisService redis;

	@Autowired
	private BaseValueService baseValueService;

	@Autowired
	private ProjectGateWay projectGW;

	private static String TIME_INDEX = "时间";

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

	/**
	 * @see com.hirain.phm.bd.ground.bigdata.service.DataRecordService#getDataRecords(com.hirain.phm.bd.data.bean.DataParam)
	 */
	@Override
	public DataRecordResponse getDataRecords(DataRequest request) {
		DataRecordResponse response = new DataRecordResponse();
		List<DataRecord> records = service.select(getParam(request));
		List<DataRecordVO> results = new ArrayList<>();
		for (DataRecord record : records) {
			DataRecordVO vo = new DataRecordVO();
			vo.setCarriageId(record.getCarriageId());
			vo.setDoorId(record.getDoorId());
			vo.setProject(record.getProject());
			vo.setTrain(record.getTrain());
			vo.setTimestamp(record.getTimestamp());
			vo.setState(DataRecordVO.STATE_ARRAY[record.getState()]);
			results.add(vo);
			String key = getKey(vo);
			redis.set(key, record.getData(), 2, TimeUnit.HOURS);
		}
		response.setRecords(results);
		// response.setTotal(count(request));
		return response;
	}

	/**
	 * @param record
	 * @return
	 */
	private String getKey(DataRecordVO record) {
		StringBuilder sb = new StringBuilder();
		sb.append(record.getProject()).append("-");
		sb.append(record.getTrain()).append("-");
		sb.append(record.getCarriageId()).append("-");
		sb.append(record.getDoorId()).append("-");
		sb.append(sdf.format(record.getTimestamp()));
		return sb.toString();
	}

	/**
	 * @see com.hirain.phm.bd.ground.bigdata.service.DataRecordService#count(com.hirain.phm.bd.data.bean.DataParam)
	 */
	@Override
	public int count(DataRequest request) {
		return service.count(getParam(request));
	}

	/**
	 * @param request
	 * @return
	 */
	private DataParam getParam(DataRequest request) {
		DataParam param = new DataParam();
		param.setDate(request.getTime());
		param.setProject(request.getProject());
		param.setTrain(request.getTrain());
		param.setLimit(request.getPageSize());
		param.setOffset(request.getPageSize() * (request.getPageNum() - 1));
		return param;
	}

	@Override
	public DataResponse getData(DataRecordVO record, List<String> variables) throws Exception {
		String redisKey = getKey(record);
		String json = redis.get(redisKey);
		RecordData recordData = JsonUtil.fromString(json, RecordData.class);
		DataResponse response = new DataResponse();
		List<String> keys = recordData.getKeys();
		response.setKeys(variables);
		Project project = projectGW.selectProjectByName(record.getProject());
		List<List<String>> baseValues = new ArrayList<>();
		for (String key : variables) {
			baseValues.add(baseValueService.getBaseValue(project.getId(), key));
		}
		response.setBaseValues(baseValues);

		int index = keys.indexOf(TIME_INDEX);
		List<RawData> datas = convertFrom(recordData.getFrames(), index);
		if (variables != null) {
			List<RawData> results = filterDatas(variables, keys, datas);
			response.setDatas(results);
		} else {
			response.setDatas(datas);
		}
		System.out.println(response);
		return response;
	}

	private List<RawData> filterDatas(List<String> variables, List<String> keys, List<RawData> datas) throws Exception {
		List<RawData> results = new ArrayList<>();
		int[] index = filter(keys, variables);
		for (RawData data : datas) {
			RawData result = new RawData();
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
	 * @param frames
	 * @return
	 */
	private List<RawData> convertFrom(List<RunDataFrame> frames, int index) {
		List<RawData> datas = new ArrayList<>();
		for (RunDataFrame frame : frames) {
			RawData data = new RawData();
			String timestamp = frame.getValues().get(index);
			data.setTimestamp(timestamp);
			data.setValues(frame.getValues());
			datas.add(data);
		}
		return datas;
	}

}
