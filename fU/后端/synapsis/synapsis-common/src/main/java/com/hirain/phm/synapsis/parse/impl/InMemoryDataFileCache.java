/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.parse.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.hirain.phm.synapsis.parse.DataFileCache;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 17, 2020 1:48:27 PM
 * @Description
 *              <p>
 *              基于内存的缓存
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 17, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Component
public class InMemoryDataFileCache implements DataFileCache {

	Map<String, Object> cache = new ConcurrentHashMap<>();

	/**
	 * @see com.hirain.phm.synapsis.parse.DataFileCache#cache(java.lang.String, java.lang.Object)
	 */
	@Override
	public void cache(String key, Object value) {
		cache.put(key, value);
	}

	/**
	 * @see com.hirain.phm.synapsis.parse.DataFileCache#get(java.lang.String)
	 */
	@Override
	public Object get(String key) {
		return cache.get(key);
	}

	/**
	 * @see com.hirain.phm.synapsis.parse.DataFileCache#clear()
	 */
	@Override
	public void clear() {
		cache.clear();
	}
}
