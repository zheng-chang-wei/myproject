/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.bigdata.service.impl;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirain.phm.bd.ground.bigdata.service.DataRedisService;
import com.hirain.phm.bd.ground.util.RedisUtil;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Mar 6, 2020 4:58:50 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Mar 6, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Service
public class DataRedisServiceImpl implements DataRedisService {

	@Autowired
	private RedisUtil util;

	/**
	 * @see com.hirain.phm.bd.ground.bigdata.service.DataRedisService#get(java.lang.String)
	 */
	@Override
	public String get(String key) {
		return util.get(key);
	}

	@Override
	public void set(String key, String value) {
		util.set(key, value);
	}

	/**
	 * @see com.hirain.phm.bd.ground.bigdata.service.DataRedisService#set(java.lang.String, java.lang.String, long, java.util.concurrent.TimeUnit)
	 */
	@Override
	public void set(String key, String value, long timeout, TimeUnit unit) {
		util.set(key, value, timeout, unit);
	}
}
