/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.result.dao;

import java.util.List;

import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.hirain.phm.synapsis.result.CommonMapper;
import com.hirain.phm.synapsis.result.domain.AlgorithmResult;
import com.hirain.phm.synapsis.result.param.ResultQueryParam;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 21, 2020 11:51:26 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 21, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public interface AlgorithmResultMapper extends CommonMapper<AlgorithmResult> {

	/**
	 * @param id
	 */
	@Select("select tar.* from t_algorithm_result tar where tar.id=#{id}")
	@Results({

			@Result(property = "id", column = "id", id = true), @Result(property = "headerId", column = "header_id"),
			@Result(property = "algorithmHeaderId", column = "algorithm_header_id"),
			@Result(property = "header", column = "header_id", one = @One(select = "com.hirain.phm.synapsis.result.dao.CommonHeaderMapper.selectByPrimaryKey")),
			@Result(property = "algorithmHeader", column = "algorithm_header_id", one = @One(select = "com.hirain.phm.synapsis.result.dao.AlgorithmHeaderMapper.selectByPrimaryKey"))

	})
	AlgorithmResult selectResult(Long id);

	@Select("select tar.* from t_algorithm_result tar")
	@Results({

			@Result(property = "id", column = "id", id = true), @Result(property = "headerId", column = "header_id"),
			@Result(property = "algorithmHeaderId", column = "algorithm_header_id"),
			@Result(property = "header", column = "header_id", one = @One(select = "com.hirain.phm.synapsis.result.dao.CommonHeaderMapper.selectByPrimaryKey")),
			@Result(property = "algorithmHeader", column = "algorithm_header_id", one = @One(select = "com.hirain.phm.synapsis.result.dao.AlgorithmHeaderMapper.selectByPrimaryKey"))

	})
	List<AlgorithmResult> selectResults();

	/**
	 * @param param
	 */
	@Select("select tar.* from t_algorithm_result tar where tar.timestamp>=#{start} and tar.timestamp<=#{end}")
	@Results({

			@Result(property = "id", column = "id", id = true), @Result(property = "headerId", column = "header_id"),
			@Result(property = "algorithmHeaderId", column = "algorithm_header_id"),
			@Result(property = "header", column = "header_id", one = @One(select = "com.hirain.phm.synapsis.result.dao.CommonHeaderMapper.selectByPrimaryKey")),
			@Result(property = "algorithmHeader", column = "algorithm_header_id", one = @One(select = "com.hirain.phm.synapsis.result.dao.AlgorithmHeaderMapper.selectByPrimaryKey"))

	})
	List<AlgorithmResult> selectResultsWithParam(ResultQueryParam param);

}
