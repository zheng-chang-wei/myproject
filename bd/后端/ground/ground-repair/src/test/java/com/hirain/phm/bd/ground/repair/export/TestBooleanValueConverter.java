/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.repair.export;

import com.hirain.common.csv.converter.ValueConverter;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Feb 27, 2020 7:45:49 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Feb 27, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public class TestBooleanValueConverter implements ValueConverter {

	/**
	 * @see com.hirain.phm.bd.ground.maintenance.export.csv.ValueConverter#converter(java.lang.Object)
	 */
	@Override
	public String converter(Object object) {
		boolean b = (boolean) object;
		return b ? "yes" : "no";
	}

}
