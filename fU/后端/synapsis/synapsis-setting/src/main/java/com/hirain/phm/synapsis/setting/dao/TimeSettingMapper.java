/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting.dao;

import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.hirain.phm.synapsis.setting.TimeSetting;
import com.hirain.phm.synapsis.setting.common.CommonMapper;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Apr 21, 2020 5:39:48 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Apr 21, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public interface TimeSettingMapper extends CommonMapper<TimeSetting> {

	@Select("select tts.* from t_time_setting tts where tts.setting_id=#{settingId}")
	@Results({

			@Result(property = "timeVariables", column = "group_id", one = @One(select = "com.hirain.phm.synapsis.setting.dao.VariableGroupMapper.selectTimeVariables"))

	})
	TimeSetting selectDetail(int settingId);
}
