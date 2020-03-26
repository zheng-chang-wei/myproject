/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.result.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.hirain.phm.synapsis.result.domain.AlgorithmResult;
import com.hirain.phm.synapsis.result.param.ResultQueryParam;
import com.hirain.phm.synapsis.result.service.AlgorithmResultService;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 21, 2020 10:42:08 AM
 * @Description
 *              <p>
 *              内存数据存储，测试使用
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 21, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public class InMemoryAlgorithmResultService implements AlgorithmResultService {

	private List<AlgorithmResult> results = new ArrayList<>();

	private static long count = 1;

	/**
	 * @see com.hirain.phm.synapsis.result.service.AlgorithmResultService#save(com.hirain.phm.synapsis.result.domain.AlgorithmResult)
	 */
	@Override
	public void save(AlgorithmResult result) {
		result.setId(count++);
		System.out.println(result);
		results.add(result);
	}

	/**
	 * @see com.hirain.phm.synapsis.result.service.AlgorithmResultService#listResults()
	 */
	@Override
	public List<AlgorithmResult> listResults() {
		return new ArrayList<>(results);
	}

	/**
	 * @see com.hirain.phm.synapsis.result.service.AlgorithmResultService#listResults(com.hirain.phm.synapsis.result.param.ResultQueryParam)
	 */
	@Override
	public List<AlgorithmResult> listResults(ResultQueryParam param) {
		List<AlgorithmResult> results = new ArrayList<>();
		for (AlgorithmResult result : this.results) {
			if (isInTimeRange(param, result)) {
				results.add(result);
			}
		}
		return results;
	}

	/**
	 * @param param
	 * @param result
	 * @return
	 */
	public boolean isInTimeRange(ResultQueryParam param, AlgorithmResult result) {
		return result.getTimestamp().compareTo(param.getStart()) >= 0 && result.getTimestamp().compareTo(param.getEnd()) <= 0;
	}

	/**
	 * @see com.hirain.phm.synapsis.result.service.AlgorithmResultService#selectById(long)
	 */
	@Override
	public AlgorithmResult selectById(long resultId) {
		for (AlgorithmResult result : results) {
			if (resultId == result.getId()) {
				return result;
			}
		}
		return null;
	}

}
