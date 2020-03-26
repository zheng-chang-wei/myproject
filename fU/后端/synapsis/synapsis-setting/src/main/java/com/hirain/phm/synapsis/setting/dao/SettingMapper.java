/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting.dao;

import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.hirain.phm.synapsis.setting.CommonMapper;
import com.hirain.phm.synapsis.setting.Setting;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 3, 2019 2:09:53 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 3, 2019 jianwen.xin@hirain.com 1.0 create file
 */
public interface SettingMapper extends CommonMapper<Setting>{

	/**
	 * @return
	 */
	@Select("select * from t_setting where selected=true")
	Setting selectCurrent();

	@Select("select ts.* from t_setting ts where ts.id=#{id}")
	@Results({

			@Result(property = "id", column = "id", id = true),
			@Result(property = "timeVariables", column = "id", one = @One(select = "com.hirain.phm.synapsis.setting.dao.VariableGroupMapper.selectTimeVariables")),
			@Result(property = "storeVariables", column = "id", many = @Many(select = "com.hirain.phm.synapsis.setting.dao.VariableGroupMapper.selectStoreVariables")),
			@Result(property = "boardSettings", column = "id", many = @Many(select = "com.hirain.phm.synapsis.setting.dao.BoardSettingMapper.selectSetting")),
			@Result(property = "algorithmSettings", column = "id", many = @Many(select = "com.hirain.phm.synapsis.setting.dao.AlgorithmSettingMapper.selectSetting"))

	})
	Setting selectDetails(int id);

	/**
	 * 
	 */
	@Select("select id from t_setting order by last_modify offset 0 rows fetch next 1 rows only")
	int selectOldest();

}
