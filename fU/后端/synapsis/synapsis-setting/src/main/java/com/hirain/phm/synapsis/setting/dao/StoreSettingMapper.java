/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting.dao;

import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.hirain.phm.synapsis.setting.StoreSetting;
import com.hirain.phm.synapsis.setting.common.CommonMapper;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Apr 21, 2020 5:12:09 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Apr 21, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public interface StoreSettingMapper extends CommonMapper<StoreSetting> {

	@Select("select tss.* from t_store_setting tss where tss.setting_id=#{settingId}")
	@Results({

			@Result(property = "settingId", column = "setting_id", id = true),
			@Result(property = "storeVariables", column = "setting_id", many = @Many(select = "com.hirain.phm.synapsis.setting.dao.VariableGroupMapper.selectStoreVariables"))

	})
	StoreSetting selectDetail(int settingId);
}
