/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.subhealth.service;

import java.util.List;

import com.hirain.phm.bd.ground.subhealth.param.SubhealthDataResponse;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 6, 2020 2:04:48 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 6, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public interface SubhealthDataService {

	/**
	 * @param id
	 * @param state
	 * @param keys
	 * @param datas
	 */
	void saveData(Integer id, int state, String keys, String datas);

	/**
	 * @param i
	 */
	SubhealthDataResponse getSubhealthData(int detailId);

	/**
	 * @param subhealthId
	 * @param variables
	 * @return
	 * @throws Exception
	 */
	SubhealthDataResponse getDigitalData(int subhealthId, List<String> variables) throws Exception;

	/**
	 * @param subhealthId
	 * @param variables
	 * @return
	 * @throws Exception
	 */
	SubhealthDataResponse getSubhealthData(int subhealthId, List<String> variables) throws Exception;

}
