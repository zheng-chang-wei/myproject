/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.common.csv;

import java.util.HashMap;
import java.util.Map;

import com.hirain.common.csv.converter.DefaultValueConverter;
import com.hirain.common.csv.converter.ValueConverter;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Feb 27, 2020 7:50:19 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Feb 27, 2020 jianwen.xin@hirain.com 1.0 create file
 */
class ConverterManager {

	private Map<Class<?>, ValueConverter> converters;

	public ConverterManager() {
		converters = new HashMap<>();
		converters.put(DefaultValueConverter.class, new DefaultValueConverter());
	}

	public ValueConverter getConverter(Class<? extends ValueConverter> clz) {
		ValueConverter converter = converters.get(clz);
		if (converter == null) {
			try {
				converter = clz.newInstance();
				converters.put(clz, converter);
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return converter;
	}
}
