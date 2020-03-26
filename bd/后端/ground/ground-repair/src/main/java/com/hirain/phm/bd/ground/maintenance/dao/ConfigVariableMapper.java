/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.maintenance.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.hirain.phm.bd.ground.common.config.CommonMapper;
import com.hirain.phm.bd.ground.maintenance.domain.ConfigVariable;

/**
 * @Version 1.0
 * @Author changwei.zheng@hirain.com
 * @Created 2020年2月17日 上午10:43:20
 * @Description
 *              <p>
 *              门类型
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2020年2月17日 changwei.zheng@hirain.com 1.0 create file
 */
public interface ConfigVariableMapper extends CommonMapper<ConfigVariable> {

	@Select("select * from t_config_variable where user_id = #{id}")
	List<ConfigVariable> listVariable(Long id);

}
