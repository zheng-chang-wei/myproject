/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.hirain.phm.synapsis.setting.AlgorithmSetting;
import com.hirain.phm.synapsis.setting.common.CommonMapper;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 3, 2019 3:45:28 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 3, 2019 jianwen.xin@hirain.com 1.0 create file
 */
public interface AlgorithmSettingMapper extends CommonMapper<AlgorithmSetting> {

	/**
	 * @param settingId
	 * @param code
	 */
	@Select("select tas.*, ts.name subsystem from t_algorithm_setting tas left join t_subsystem ts on ts.id=tas.subsystem_id where setting_id=#{settingId} and code=#{code}")
	AlgorithmSetting selectByCode(int settingId, int code);

	/**
	 * {@link com.hirain.phm.synapsis.setting.dao.SettingMapper#selectDetails(int)}
	 * 
	 * @param settingId
	 * @return
	 */
	@Select("select tas.*, ts.name subsystem from t_algorithm_setting tas left join t_subsystem ts on ts.id=tas.subsystem_id where setting_id=#{settingId}")
	@Results({

			@Result(property = "id", column = "id", id = true),
			@Result(property = "variableGroups", column = "id", many = @Many(select = "com.hirain.phm.synapsis.setting.dao.VariableGroupMapper.selectByAlgorithm"))

	})
	List<AlgorithmSetting> selectSetting(int settingId);

	/**
	 * @param settingId
	 */
	@Delete("delete from t_algorithm_setting where setting_id=#{settingId}")
	void deleteBySettingId(int settingId);

}
