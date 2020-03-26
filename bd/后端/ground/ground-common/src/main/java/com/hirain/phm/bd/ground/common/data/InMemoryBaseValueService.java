/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.common.data;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Feb 20, 2020 3:07:18 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Feb 20, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Service
public class InMemoryBaseValueService implements BaseValueService {

	private Map<String, List<String>> map;

	/**
	 * @see com.hirain.phm.bd.ground.common.data.BaseValueService#getBaseValue(int, java.lang.String)
	 */
	@Override
	public List<String> getBaseValue(int projectId, String variable) {
		if (map == null) {
			map = new HashMap<>();
			map.put("电机电压", Arrays.asList("1.5", "0.5"));
			map.put("电机电流", Arrays.asList("2"));
			map.put("编码器", Arrays.asList("2000"));
			map.put("开门时间", Arrays.asList("2000"));
			map.put("关门时间", Arrays.asList("2000"));
		}
		return map.get(variable);
	}

}
