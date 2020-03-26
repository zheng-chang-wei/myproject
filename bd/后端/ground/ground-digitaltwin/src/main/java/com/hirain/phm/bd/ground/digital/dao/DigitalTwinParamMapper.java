/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.digital.dao;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.hirain.phm.bd.ground.common.config.CommonMapper;
import com.hirain.phm.bd.ground.digital.domain.DigitalTwinParam;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Sep 25, 2019 10:50:54 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Sep 25, 2019 jianwen.xin@hirain.com 1.0 create file
 */
public interface DigitalTwinParamMapper extends CommonMapper<DigitalTwinParam> {

	/**
	 * @param type
	 * @return
	 */
	@Select("SELECT id,param_key,name,unit,param_type FROM t_digital_param WHERE param_type=#{type}")
	@Results({ @Result(column = "param_key", property = "key"), @Result(property = "type", column = "param_type") })
	List<DigitalTwinParam> selectByType(String type);

	/**
	 * @param param
	 */
	@Select("select id,param_key,name,unit,param_type from t_digital_param where name=#{param}")
	@Results({ @Result(column = "param_key", property = "key"), @Result(property = "type", column = "param_type") })
	List<DigitalTwinParam> selectbyParamName(String param);

}
