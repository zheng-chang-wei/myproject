/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/

package com.hirain.phm.bd.ground.subhealth.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirain.phm.bd.ground.common.service.BaseService;
import com.hirain.phm.bd.ground.subhealth.dao.SubhealthDetailMapper;
import com.hirain.phm.bd.ground.subhealth.domain.SubhealthDetail;
import com.hirain.phm.bd.ground.subhealth.service.SubhealthDetailService;
import com.hirain.phm.bd.ground.util.RedisUtil;

/**
 * @Version 1.0
 * @Author weijia.kong@hirain.com
 * @Created May 24, 2019 3:58:32 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               May 24, 2019 weijia.kong@hirain.com 1.0 create file
 */
@Service
public class SubhealthDetailServiceImpl extends BaseService<SubhealthDetail> implements SubhealthDetailService {

	@Autowired
	private SubhealthDetailMapper mapper;

	@Autowired
	private RedisUtil redis;

	@Override
	public void insertList(List<SubhealthDetail> details) {
		mapper.insertList(details);
		for (SubhealthDetail detail : details) {
			addSubhealthToMonthRecord(detail.getStartTime());
		}
	}

	private void addSubhealthToMonthRecord(Date time) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		redis.hmincrement("subhealth-" + year, String.valueOf(month));
	}
}
