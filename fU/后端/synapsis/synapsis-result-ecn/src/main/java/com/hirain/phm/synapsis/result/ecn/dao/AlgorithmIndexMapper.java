/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.result.ecn.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.hirain.phm.synapsis.result.CommonMapper;
import com.hirain.phm.synapsis.result.ecn.domain.AlgorithmIndex;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 21, 2020 4:07:36 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 21, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public interface AlgorithmIndexMapper extends CommonMapper<AlgorithmIndex> {

	@Select("select tai.* from t_algorithm_index tai where tai.index_setting_id=#{indexSettingId}")
	List<AlgorithmIndex> selectByIndexSettingId(int indexSettingId);
}
