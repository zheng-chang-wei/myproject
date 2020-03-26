/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.digital.service;

import com.hirain.phm.bd.ground.digital.controller.ResultQueryRequest;
import com.hirain.phm.bd.ground.digital.domain.DigitalTwinPacket;
import com.hirain.phm.bd.ground.digital.response.ResultQueryResponse;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Sep 25, 2019 10:45:35 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Sep 25, 2019 jianwen.xin@hirain.com 1.0 create file
 */
public interface DigitalTwinService {

	/**
	 * @param packet
	 */
	void update(DigitalTwinPacket packet);

	/**
	 * @param request
	 * @return
	 */
	ResultQueryResponse getAllResults(ResultQueryRequest request);

	/**
	 * @param request
	 * @param type
	 * @return
	 */
	ResultQueryResponse getResultsByType(ResultQueryRequest request, String type);

	/**
	 * @param request
	 * @param param
	 * @return
	 */
	ResultQueryResponse getResultsByParamName(ResultQueryRequest request, String param);

}
