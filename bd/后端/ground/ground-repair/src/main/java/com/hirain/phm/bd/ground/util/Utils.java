/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.util;

import com.hirain.phm.bd.ground.common.event.UnifiedFaultRecord;

/**
 * @Version 1.0   
 * @Author jianwen.xin@hirain.com  
 * @Created Dec 10, 2019 6:06:32 PM  
 * @Description
 * <p>
 * @Modification
 * <p>Date         Author      Version     Description  
 * <p>Dec 10, 2019     jianwen.xin@hirain.com             1.0        create file 
 */
public class Utils {

	public static String getDoor(UnifiedFaultRecord fault) {
		String door = "";
		if (fault.getCarriageId() != null) {
			door += fault.getCarriageId() + "车";
		}
		if (fault.getDoorId() != null) {
			door += fault.getDoorId() + "门";
		}
		return door.isEmpty() ? "--" : door;
	}

}
