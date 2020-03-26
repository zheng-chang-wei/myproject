/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.common.csv;

import com.hirain.common.csv.converter.ValueConverter;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Feb 28, 2020 9:44:20 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Feb 28, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public class CustomValueConverter implements ValueConverter {

	/**
	 * @see com.hirain.common.csv.converter.ValueConverter#converter(java.lang.Object)
	 */
	@Override
	public String converter(Object object) {
		if (object instanceof Boolean) {
			boolean b = (boolean) object;
			return b ? "yes" : "no";
		}
		return object == null ? "" : object.toString();
	}

}
