/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.result.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import com.hirain.phm.synapsis.result.CommonMapper;
import com.hirain.phm.synapsis.result.domain.AlgorithmResult;
import com.hirain.phm.synapsis.result.param.ResultQueryParam;

import tk.mybatis.mapper.util.StringUtil;

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
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               May 9, 2020 zhaobin.su@hirain.com 1.0 create file
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

	@Select("select tar.* from t_algorithm_result tar order by tar.timestamp desc")
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
	// @Select("select tar.* from t_algorithm_result tar where tar.timestamp>=#{start} and tar.timestamp<=#{end}")
	@SelectProvider(type = ResultMapperProvider.class, method = "getSelectResultsSql")
	@Results({

			@Result(property = "id", column = "id", id = true), @Result(property = "headerId", column = "header_id"),
			@Result(property = "algorithmHeaderId", column = "algorithm_header_id"),
			@Result(property = "header", column = "header_id", one = @One(select = "com.hirain.phm.synapsis.result.dao.CommonHeaderMapper.selectByPrimaryKey")),
			@Result(property = "algorithmHeader", column = "algorithm_header_id", one = @One(select = "com.hirain.phm.synapsis.result.dao.AlgorithmHeaderMapper.selectByPrimaryKey"))

	})
	List<AlgorithmResult> selectResultsWithParam(ResultQueryParam param);

	@Delete("delete from t_algorithm_result where id=#{id}")
	void deleteByResultId(long id);

	class ResultMapperProvider {

		public String getSelectResultsSql(ResultQueryParam param) {
			String sql = "select tar.* from t_algorithm_result tar where true";
			if (param.getStart() != null) {
				sql += " and tar.timestamp>=#{start}";
			}
			if (param.getEnd() != null) {
				sql += " and tar.timestamp<=#{end}";
			}
			if (StringUtil.isNotEmpty(param.getName())) {
				sql += " and tar.name like '%" + param.getName() + "%'";
			}
			if (StringUtil.isNotEmpty(param.getAlgorithm())) {
				sql += " and tar.algorithm like '%" + param.getAlgorithm() + "%'";
			}
			if (StringUtil.isNotEmpty(param.getSubsystem())) {
				sql += " and tar.subsystem like '%" + param.getSubsystem() + "%'";
			}
			sql += " order by tar.timestamp desc";
			return sql;
		}
	}
}
