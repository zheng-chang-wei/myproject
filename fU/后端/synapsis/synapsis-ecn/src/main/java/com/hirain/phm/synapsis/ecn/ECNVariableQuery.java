/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.ecn;

import java.util.List;
import java.util.Map;

import com.hirain.phm.synapsis.ecn.model.Device;
import com.hirain.phm.synapsis.setting.ECNVariable;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 19, 2020 1:37:45 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 19, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public interface ECNVariableQuery {

	List<ECNVariable> getAllVariables(Device device);

	/**
	 * @param comId
	 * @param length
	 * @param device
	 * @return
	 */
	List<ECNVariable> getVariables(long comId, int length, Device device);

	/**
	 * @param comId
	 * @param device
	 */
	int getDataSetLength(long comId, Device device);

	Map<Long, String> getComIdCycle(Device device);

}
