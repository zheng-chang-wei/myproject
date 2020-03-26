/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.common.csv.converter;

import java.text.SimpleDateFormat;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Feb 27, 2020 4:24:44 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Feb 27, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public class DateValueConverter implements ValueConverter {

	private SimpleDateFormat sdf;

	/**
	 * 
	 */
	public DateValueConverter(String pattern) {
		sdf = new SimpleDateFormat(pattern);
	}

	/**
	 * @see com.hirain.phm.bd.ground.maintenance.export.csv.ValueConverter#converter(java.lang.Object)
	 */
	@Override
	public String converter(Object object) {
		if (object == null) {
			return "";
		}
		return sdf.format(object);
	}

}
