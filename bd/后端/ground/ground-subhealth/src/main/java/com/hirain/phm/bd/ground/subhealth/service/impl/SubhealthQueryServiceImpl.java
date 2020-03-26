/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
/**
 * @Version 1.0   
 * @Author weijia.kong@hirain.com
 * @Created Jun 3, 2019 11:31:38 AM
 * @Description
 * <p>
 * @Modification
 * <p>Date         Author      Version     Description  
 * <p>Jun 3, 2019     weijia.kong@hirain.com            1.0        create file 
 */
package com.hirain.phm.bd.ground.subhealth.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirain.phm.bd.ground.subhealth.dao.SubhealthQueryMapper;
import com.hirain.phm.bd.ground.subhealth.param.AnnualCountResponse;
import com.hirain.phm.bd.ground.subhealth.param.SubhealthDetailParams;
import com.hirain.phm.bd.ground.subhealth.param.SubhealthWithSuggestionParams;
import com.hirain.phm.bd.ground.subhealth.service.SubhealthQueryService;
import com.hirain.phm.bd.ground.util.RedisUtil;

@Service
public class SubhealthQueryServiceImpl implements SubhealthQueryService {

	@Autowired
	private SubhealthQueryMapper queryMapper;

	@Autowired
	private RedisUtil redis;

	/*
	 * (non-Javadoc)
	 * @see com.hirain.phm.bd.ground.subhealth.service.SubhealthQueryService#selectByParams(com.hirain.phm.bd.ground.subhealth.domain.
	 * SubhealthDetailParams)
	 */
	@Override
	public List<SubhealthWithSuggestionParams> selectByParams(SubhealthDetailParams subdeDetailParams) {
		return queryMapper.selectByExample(subdeDetailParams);
	}

	@Override
	public List<SubhealthWithSuggestionParams> selectToday(String project, String trainNo) {
		return queryMapper.selectToday(project, trainNo);
	}

	/** 
	 * @see com.hirain.phm.bd.ground.subhealth.service.SubhealthQueryService#selectToday()
	 */
	@Override
	public List<SubhealthWithSuggestionParams> selectToday() {
		String todayDate = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now());
		String startTime = todayDate + " 00:00:00";
		String endTime = todayDate + " 23:59:59";
		return queryMapper.selectToday4Bode(startTime, endTime);
	}

	@Override
	public AnnualCountResponse selectYearCounts(int year) {
		AnnualCountResponse response = new AnnualCountResponse();
		response.setYear(year);
		Map<Object, Object> map = redis.hget("subhealth-" + year);
		List<Integer> values = new ArrayList<>();
		for (int i = 0; i < 12; i++) {
			Object value = map.get(String.valueOf(i + 1));
			values.add(value == null ? 0 : Integer.parseInt(value.toString()));
		}
		response.setCounts(values);
		return response;
	}

	/**
	 * @see com.hirain.phm.bd.ground.fault.service.FaultDetailService#selectMonthCounts(int, int)
	 */
	@Override
	public Integer selectMonthCounts(int year, int month) {
		Object value = redis.hmget("subhealth-" + year, String.valueOf(month));
		return value == null ? 0 : Integer.parseInt(value.toString());
	}

	/**
	 * @see com.hirain.phm.bd.ground.fault.service.FaultDetailService#selectAnnualCount(int)
	 */
	@Override
	public int selectAnnualCount(int year) {
		Map<Object, Object> counts = redis.hget("subhealth-" + year);
		int sum = 0;
		for (Object count : counts.values()) {
			sum += Integer.parseInt(count.toString());
		}
		System.err.println(year + ":" + sum);
		return sum;
	}
}
