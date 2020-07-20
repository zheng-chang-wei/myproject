/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.result.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirain.phm.synapsis.result.dao.AlgorithmHeaderMapper;
import com.hirain.phm.synapsis.result.dao.AlgorithmResultMapper;
import com.hirain.phm.synapsis.result.dao.CommonHeaderMapper;
import com.hirain.phm.synapsis.result.domain.AlgorithmResult;
import com.hirain.phm.synapsis.result.param.ResultQueryParam;
import com.hirain.phm.synapsis.result.service.AlgorithmResultService;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 21, 2020 11:52:58 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 21, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Service
public class AlgorithmResultServiceImpl implements AlgorithmResultService {

	@Autowired
	private AlgorithmResultMapper resultMapper;

	@Autowired
	private CommonHeaderMapper headerMapper;

	@Autowired
	private AlgorithmHeaderMapper algorithmMapper;

	/**
	 * @see com.hirain.phm.synapsis.result.service.AlgorithmResultService#save(com.hirain.phm.synapsis.result.domain.AlgorithmResult)
	 */
	@Override
	public void save(AlgorithmResult result) {
		if (result.getHeader() != null) {
			headerMapper.insertGenerateKey(result.getHeader());
			result.setHeaderId(result.getHeader().getId());
		}
		algorithmMapper.insertGenerateKey(result.getAlgorithmHeader());
		result.setAlgorithmHeaderId(result.getAlgorithmHeader().getId());
		resultMapper.insertGenerateKey(result);
	}

	/**
	 * @see com.hirain.phm.synapsis.result.service.AlgorithmResultService#listResults()
	 */
	@Override
	public List<AlgorithmResult> listResults() {
		return resultMapper.selectResults();
	}

	/**
	 * @see com.hirain.phm.synapsis.result.service.AlgorithmResultService#listResults(com.hirain.phm.synapsis.result.param.ResultQueryParam)
	 */
	@Override
	public List<AlgorithmResult> listResults(ResultQueryParam param) {
		return resultMapper.selectResultsWithParam(param);
	}

	/**
	 * @see com.hirain.phm.synapsis.result.service.AlgorithmResultService#selectById(long)
	 */
	@Override
	public AlgorithmResult selectById(long resultId) {
		return resultMapper.selectByPrimaryKey(resultId);
	}

	/**
	 * @see com.hirain.phm.synapsis.result.service.AlgorithmResultService#deleteById(long)
	 */
	@Override
	public void deleteById(long resultId) {
		resultMapper.deleteByResultId(resultId);
	}

}
