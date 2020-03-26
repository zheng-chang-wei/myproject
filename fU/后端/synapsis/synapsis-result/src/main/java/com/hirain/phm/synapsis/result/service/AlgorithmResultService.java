/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.result.service;

import java.util.List;

import com.hirain.phm.synapsis.result.domain.AlgorithmResult;
import com.hirain.phm.synapsis.result.param.ResultQueryParam;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 20, 2020 6:39:36 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 20, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public interface AlgorithmResultService {

	/**
	 * @param result
	 */
	void save(AlgorithmResult result);

	/**
	 * 
	 */
	List<AlgorithmResult> listResults();

	/**
	 * @param param
	 * @return
	 */
	List<AlgorithmResult> listResults(ResultQueryParam param);

	/**
	 * @param resultId
	 */
	AlgorithmResult selectById(long resultId);

}
