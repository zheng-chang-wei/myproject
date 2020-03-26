/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.bigdata.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirain.phm.bd.ground.bigdata.dao.BigTrainMapper;
import com.hirain.phm.bd.ground.bigdata.domain.BigTrainEntity;
import com.hirain.phm.bd.ground.bigdata.param.GroundData;
import com.hirain.phm.bd.ground.bigdata.param.GroundDataParam;
import com.hirain.phm.bd.ground.bigdata.service.BigTrainService;
import com.hirain.phm.bd.ground.common.service.BaseService;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年6月5日 下午6:13:12
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年6月5日 jianwen.xin@hirain.com 1.0 create file
 */
@Service
public class BigTrainServiceImpl extends BaseService<BigTrainEntity> implements BigTrainService {

	@Autowired
	private BigTrainMapper mapper;

	@Override
	public List<GroundData> findBigTrainByParam(GroundDataParam param) {
		return mapper.findBigTrainByParam(param);
	}

	@Override
	public BigTrainEntity selectByTrainId(Integer trainId) {
		return mapper.selectByTrainId(trainId);
	}
}
