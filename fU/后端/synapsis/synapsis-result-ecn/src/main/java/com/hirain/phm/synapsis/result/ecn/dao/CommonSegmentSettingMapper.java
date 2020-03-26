/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.result.ecn.dao;

import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.hirain.phm.synapsis.result.CommonMapper;
import com.hirain.phm.synapsis.result.ecn.domain.CommonSegmentSetting;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 21, 2020 3:28:51 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 21, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public interface CommonSegmentSettingMapper extends CommonMapper<CommonSegmentSetting> {

	/**
	 * @param settingId
	 * @return
	 */
	@Select("select ths.* from t_header_setting ths where ths.setting_id=#{settingId}")
	@Results({

			@Result(property = "id", column = "id", id = true),
			@Result(property = "segments", column = "id", many = @Many(select = "com.hirain.phm.synapsis.result.ecn.dao.CommonSegmentMapper.selectBySegmentSettingId"))

	})
	CommonSegmentSetting selectBySettingId(Integer settingId);

}
