/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.digital.dao;

import java.util.List;

import org.apache.ibatis.annotations.SelectProvider;

import com.hirain.phm.bd.ground.common.config.CommonMapper;
import com.hirain.phm.bd.ground.digital.domain.DigitalTwinResult;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Sep 25, 2019 10:51:43 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Sep 25, 2019 jianwen.xin@hirain.com 1.0 create file
 */
public interface DigitalTwinResultMapper extends CommonMapper<DigitalTwinResult> {

	@SelectProvider(type = ResultMapperProvider.class, method = "selectByRequest")
	List<DigitalTwinResult> selectByCondition(DigitalTwinResult condition);

	class ResultMapperProvider {

		public String selectByRequest(DigitalTwinResult condition) {
			String sql = "select timestamp,param_value from t_digital_twins";
			sql += " where train_id=#{trainId}";
			sql += " and car_id=#{carId}";
			sql += " and door_id=#{doorId}";
			sql += " and timestamp>=#{timestamp}";
			sql += " and param_id=#{paramId}";
			sql += " order by timestamp";
			return sql;
		}
	}
}
