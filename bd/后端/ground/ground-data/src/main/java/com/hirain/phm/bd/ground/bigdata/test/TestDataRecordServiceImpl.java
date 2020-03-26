/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.bigdata.test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hirain.phm.bd.data.DataQueryService;
import com.hirain.phm.bd.data.bean.DataParam;
import com.hirain.phm.bd.data.bean.DataRecord;
import com.hirain.phm.bd.ground.bigdata.param.DataRecordResponse;
import com.hirain.phm.bd.ground.bigdata.param.DataRecordVO;
import com.hirain.phm.bd.ground.bigdata.param.DataRequest;
import com.hirain.phm.bd.ground.bigdata.param.DataResponse;
import com.hirain.phm.bd.ground.bigdata.param.RawData;
import com.hirain.phm.bd.ground.bigdata.service.DataRecordService;
import com.hirain.phm.bd.ground.common.data.BaseValueService;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Mar 9, 2020 9:47:19 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Mar 9, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public class TestDataRecordServiceImpl implements DataRecordService {

	@Autowired
	private DataQueryService service;

	@Autowired
	private BaseValueService baseValueService;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");

	/**
	 * @see com.hirain.phm.bd.ground.bigdata.service.DataRecordService#getDataRecords(com.hirain.phm.bd.ground.bigdata.param.DataRequest)
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
		}
		response.setRecords(results);
		response.setTotal(count(request));
		return response;
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

	/**
	 * @see com.hirain.phm.bd.ground.bigdata.service.DataRecordService#count(com.hirain.phm.bd.ground.bigdata.param.DataRequest)
	 */
	@Override
	public int count(DataRequest request) {
		return service.count(getParam(request));
	}

	/**
	 * @see com.hirain.phm.bd.ground.bigdata.service.DataRecordService#getData(com.hirain.phm.bd.ground.bigdata.param.DataRecordVO, java.util.List)
	 */
	@Override
	public DataResponse getData(DataRecordVO record, List<String> variables) throws Exception {
		List<String> keys = new ArrayList<>();
		keys.addAll(variables);
		DataResponse response = new DataResponse();
		response.setKeys(keys);
		List<List<String>> baseValues = new ArrayList<>();
		for (String key : keys) {
			baseValues.add(baseValueService.getBaseValue(0, key));
		}
		response.setBaseValues(baseValues);
		List<RawData> datas = new ArrayList<>();
		Date date = new Date();
		for (int i = 0; i < 1200; i++) {
			RawData data = new RawData();
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
}
