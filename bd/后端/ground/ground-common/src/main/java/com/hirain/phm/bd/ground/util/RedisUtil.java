/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年4月8日 上午9:52:14
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年4月8日 jianwen.xin@hirain.com 1.0 create file
 */
@Component
public class RedisUtil {

	@Autowired
	private RedisTemplate<String, String> template;

	public boolean hasKey(String key) {
		return template.hasKey(key);
	}

	public boolean hasKey(String key, Object hashKey) {
		return template.opsForHash().hasKey(key, hashKey);
	}

	public void del(String... key) {
		if (key != null && key.length > 0) {
			template.delete(Arrays.asList(key));
		}
	}

	public Object hmget(String key, String hashKey) {
		return template.opsForHash().get(key, hashKey);
	}

	public Map<Object, Object> hget(String key) {
		try {
			return template.opsForHash().entries(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new HashMap<>();
	}

	public Set<Object> hkeys(String key) {
		return template.opsForHash().keys(key);
	}

	public List<Object> hvalues(String key) {
		return template.opsForHash().values(key);
	}

	public long hsize(String key) {
		return template.opsForHash().size(key);
	}

	public void hset(String key, Map<Object, Object> values) {
		try {
			template.opsForHash().putAll(key, values);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void hmset(String key, Object hashKey, Object value) {
		template.opsForHash().put(key, hashKey, value);
	}

	public void set(String key, String value) {
		template.opsForValue().set(key, value);
	}

	public void set(String key, String value, long timeout, TimeUnit unit) {
		template.opsForValue().set(key, value, timeout, unit);
	}

	public String get(String key) {
		return template.opsForValue().get(key);
	}

	public Long increment(String key) {
		return template.opsForValue().increment(key);
	}

	/**
	 * @param newSheetKey
	 * @param hkey
	 * @return
	 */
	public Long hmincrement(String key, String hkey) {
		return template.opsForHash().increment(key, hkey, 1);
	}

	/**
	 * @param unhandledMonthAgo
	 * @return
	 */
	public Long decrement(String key) {
		return template.opsForValue().decrement(key);
	}

	/**
	 * @param unhandledMonthAgo
	 * @param i
	 * @return
	 */
	public Long increment(String key, int delta) {
		return template.opsForValue().increment(key, delta);
	}

	/**
	 * @param newSheetKey
	 * @param string
	 */
	public void hmdel(String key, String hkey) {
		template.opsForHash().delete(key, hkey);
	}
}
