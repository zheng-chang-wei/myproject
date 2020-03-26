/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.result.ecn.dao;

import java.util.List;

import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.hirain.phm.synapsis.result.CommonMapper;
import com.hirain.phm.synapsis.result.ecn.domain.CommonSegment;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 21, 2020 3:29:34 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 21, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public interface CommonSegmentMapper extends CommonMapper<CommonSegment> {

	@Select("select tcs.* from t_common_segment tcs where tcs.segment_setting_id=#{segmentSettingId}")
	@Results({

			@Result(property = "id", column = "id", id = true), @Result(property = "variableId", column = "variable_id"),
			@Result(property = "source", column = "variable_id", one = @One(select = "com.hirain.phm.synapsis.setting.dao.ECNVariableMapper.selectByPrimaryKey"))

	})
	List<CommonSegment> selectBySegmentSettingId(long segmentSettingId);
}
