/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.fault.service;

import java.io.OutputStream;
import java.util.List;

import com.hirain.phm.bd.ground.fault.param.FaultCount;
import com.hirain.phm.bd.ground.fault.param.FaultRequest;
import com.hirain.phm.bd.ground.fault.param.SubhealthResponse;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jun 28, 2020 5:37:54 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jun 28, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public interface OwnerSubhealthService {

	List<SubhealthResponse> selectTop20(String project, String train);

	/**
	 * @param request
	 * @param outputStream
	 */
	void exportSubhealth(FaultRequest request, OutputStream outputStream);

	/**
	 * @param project
	 * @param train
	 * @param subhealths
	 */
	List<FaultCount> countLastMonth(String project, String train, List<String> subhealths);
}
