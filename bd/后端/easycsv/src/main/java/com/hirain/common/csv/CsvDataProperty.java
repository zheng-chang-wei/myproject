/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.common.csv;

import java.lang.reflect.Field;

import com.hirain.common.csv.converter.ValueConverter;

import lombok.Setter;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Feb 27, 2020 4:19:27 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Feb 27, 2020 jianwen.xin@hirain.com 1.0 create file
 */
class CsvDataProperty {

	@Setter
	private Field field;

	@Setter
	private String name;

	@Setter
	private ValueConverter converter;

	public String name() {
		return name;
	}

	public String valueOf(Object object) {
		try {
			Object value = field.get(object);
			return converter.converter(value);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return "";
	}
}
