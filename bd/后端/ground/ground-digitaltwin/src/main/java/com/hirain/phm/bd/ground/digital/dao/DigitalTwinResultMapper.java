/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.digital.dao;

import java.util.Date;
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
	List<DigitalTwinResult> selectByCondition(DigitalTwinResult condition, Date start, Date end);

	class ResultMapperProvider {

		public String selectByRequest(DigitalTwinResult condition, Date start, Date end) {
			String sql = "select timestamp,param_value from t_digital_twins";
			sql += " where train_id=#{condition.trainId}";
			sql += " and car_id=#{condition.carId}";
			sql += " and door_id=#{condition.doorId}";
			if (start != null) {
				sql += " and timestamp>=#{start}";
			}
			if (end != null) {
				sql += " and timestamp<=#{end}";
			}
			sql += " and param_id=#{condition.paramId}";
			sql += " order by timestamp";
			return sql;
		}
	}
}
