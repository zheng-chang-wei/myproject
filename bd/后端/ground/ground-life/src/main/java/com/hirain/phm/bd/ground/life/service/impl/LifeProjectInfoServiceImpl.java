/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.life.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirain.phm.bd.ground.common.service.BaseService;
import com.hirain.phm.bd.ground.life.dao.LifeProjectInfoMapper;
import com.hirain.phm.bd.ground.life.domain.LifeProjectInfo;
import com.hirain.phm.bd.ground.life.service.LifeProjectInfoService;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created May 24, 2019 10:52:05 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               May 24, 2019 zepei.tao@hirain.com 1.0 create file
 */
@Service
public class LifeProjectInfoServiceImpl extends BaseService<LifeProjectInfo> implements LifeProjectInfoService {

	@Autowired
	private LifeProjectInfoMapper mapper;

	/**
	 * @see com.hirain.phm.bd.ground.life.service.LifeProjectInfoService#findLifeInfos(java.lang.Integer)
	 */
	@Override
	public List<LifeProjectInfo> findLifeInfos(Integer projectId) {
		return mapper.findProjectLifeInfo(projectId);
	}

}
