/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.fault.service;

import java.util.List;

import com.hirain.phm.bd.ground.fault.param.FaultDataResponse;
import com.hirain.phm.bd.message.train.FaultMessage;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 27, 2019 9:52:08 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 27, 2019 jianwen.xin@hirain.com 1.0 create file
 */
public interface FaultDataService {

	/**
	 * @param id
	 * @return
	 */
	FaultDataResponse getFaultData(Long id);

	/**
	 * @param faultId
	 * @param faultMessage
	 */
	void saveFaultData(Long faultId, FaultMessage faultMessage);

	/**
	 * @param l
	 * @param asList
	 * @return
	 * @throws Exception
	 */
	FaultDataResponse getDigitalData(long faultId, List<String> variables) throws Exception;

	/**
	 * @param id
	 * @param variables
	 * @return
	 * @throws Exception
	 */
	FaultDataResponse getFaultData(Long id, List<String> variables) throws Exception;

}
