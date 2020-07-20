/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting.dao;

import org.apache.ibatis.annotations.Delete;

import com.hirain.phm.synapsis.setting.Subsystem;
import com.hirain.phm.synapsis.setting.common.CommonMapper;

/**
 * @Version 1.0
 * @Author changwei.zheng@hirain.com
 * @Created 2019年12月24日 上午9:41:09
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年12月24日 changwei.zheng@hirain.com 1.0 create file
 */
public interface SubsystemMapper extends CommonMapper<Subsystem> {

	@Delete("delete from t_subsystem")
	void deleteALl();

	// @SelectProvider(type = SubsystemMapperProvider.class, method = "listSubsystemByParms")
	// List<SubsystemGroupItem> listSubsystemByParms(String name);
	//
	// class SubsystemMapperProvider {
	//
	// public String listSubsystemByParms(String name) {
	// String sql = "SELECT ts.*,tas.id algorithm_setting_id from t_subsystem ts "
	// //
	// + " left join t_algorithm_setting tas on ts.id=tas.subsystem_id "
	// //
	// + " WHERE true ";
	//
	// if (StringUtil.isNotEmpty(name)) {
	// sql += " and ts.`name`= #{name} ";
	// }
	// sql += " GROUP BY ts.ID,ts.name,ts.description";
	// return sql;
	// }
	//
	// }
}
