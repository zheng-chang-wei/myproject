/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.maintenance.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import com.hirain.phm.bd.ground.common.config.CommonMapper;
import com.hirain.phm.bd.ground.maintenance.domain.StepType;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年7月17日 上午10:58:35
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年7月17日 jianwen.xin@hirain.com 1.0 create file
 */
public interface StepTypeMapper extends CommonMapper<StepType> {

	@SelectProvider(type = SelectInProvider.class, method = "selectTypeIn")
	List<StepType> selectIn(@Param("typeList") List<String> typeList);

	class SelectInProvider {

		public String selectTypeIn(List<String> typeList) {
			String sql = "select * from t_step_type where type in( ";
			StringBuilder sb = new StringBuilder();
			typeList.forEach(t -> {
				sb.append("'").append(t).append("'").append(",");
			});
			String in = sb.substring(0, sb.length() - 1);
			return sql + in + ")";
		}
	}
}
