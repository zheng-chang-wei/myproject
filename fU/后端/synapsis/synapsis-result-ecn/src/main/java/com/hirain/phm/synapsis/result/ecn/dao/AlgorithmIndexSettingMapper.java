/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.result.ecn.dao;

import java.util.List;

import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.hirain.phm.synapsis.result.CommonMapper;
import com.hirain.phm.synapsis.result.ecn.domain.AlgorithmIndexSetting;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 21, 2020 4:07:13 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 21, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public interface AlgorithmIndexSettingMapper extends CommonMapper<AlgorithmIndexSetting> {

	/**
	 * @param settingId
	 * @return
	 */
	@Select("select trs.* from t_result_setting trs where trs.setting_id=#{settingId}")
	@Results({

			@Result(property = "id", column = "id", id = true),
			@Result(property = "algorithms", column = "id", many = @Many(select = "com.hirain.phm.synapsis.result.ecn.dao.AlgorithmIndexMapper.selectByIndexSettingId"))

	})
	List<AlgorithmIndexSetting> selectBySettingId(Integer settingId);

}
