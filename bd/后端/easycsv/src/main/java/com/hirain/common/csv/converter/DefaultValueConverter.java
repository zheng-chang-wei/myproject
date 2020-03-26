/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.common.csv.converter;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Feb 27, 2020 4:24:15 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Feb 27, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public class DefaultValueConverter implements ValueConverter {

	/**
	 * @see com.hirain.phm.bd.ground.maintenance.export.csv.ValueConverter#converter(java.lang.Object)
	 */
	@Override
	public String converter(Object object) {
		return object == null ? "" : object.toString();
	}

}
