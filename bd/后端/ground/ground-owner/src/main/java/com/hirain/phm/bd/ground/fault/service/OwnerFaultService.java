/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.fault.service;

import java.io.OutputStream;
import java.util.List;

import com.hirain.phm.bd.ground.fault.param.FaultCount;
import com.hirain.phm.bd.ground.fault.param.FaultRequest;
import com.hirain.phm.bd.ground.fault.param.FaultResponse;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jun 24, 2020 5:06:57 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jun 24, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public interface OwnerFaultService {

	void exportFault(FaultRequest request, OutputStream stream);

	/**
	 * @param project
	 * @param train
	 * @return
	 */
	List<FaultResponse> selectTop20(String project, String train);

	/**
	 * @param request
	 * @return
	 */
	List<FaultResponse> selectByParam(FaultRequest request);

	/**
	 * @param project
	 * @param train
	 * @param faults
	 * @return
	 */
	List<FaultCount> countLastMonth(String project, String train, List<String> faults);

}
