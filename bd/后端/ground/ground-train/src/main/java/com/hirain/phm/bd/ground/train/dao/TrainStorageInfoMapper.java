/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
/**
 * @Version 1.0   
 * @Author weijia.kong@hirain.com
 * @Created Jun 6, 2019 2:57:59 PM
 * @Description
 * <p>
 * @Modification
 * <p>Date         Author      Version     Description  
 * <p>Jun 6, 2019     weijia.kong@hirain.com            1.0        create file 
 */
package com.hirain.phm.bd.ground.train.dao;

import java.util.List;

import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

import com.github.pagehelper.util.StringUtil;
import com.hirain.phm.bd.ground.common.config.CommonMapper;
import com.hirain.phm.bd.ground.train.domain.TrainStorageInfo;
import com.hirain.phm.bd.ground.train.param.TrainDMResponse;
import com.hirain.phm.bd.ground.train.param.TrainParamHeader;

@Repository
public interface TrainStorageInfoMapper extends CommonMapper<TrainStorageInfo> {

	@SelectProvider(type = TrainStorageInfoMapperProvider.class, method = "findByTrainParam")
	List<TrainDMResponse> findByTrainParam(TrainParamHeader trainParam);

	class TrainStorageInfoMapperProvider {

		public String findByTrainParam(TrainParamHeader trainParam) {
			String sql = "SELECT tp.name project, tt.train_no train," +

					"	td.train_id trainId," +

					"	td.storage_ratio," +

					"	td.fault_storage_ratio faultRatio," +

					"	td.start_date, td.end_date, tt.train_online online " +

					"FROM	t_train_dm td " +

					"LEFT JOIN t_train tt ON td.train_id = tt.id " +

					"LEFT JOIN t_project tp ON tp.id = tt.project_id where true";
			if (StringUtil.isNotEmpty(trainParam.getProject())) {
				sql += " and tp.name=#{project}";
			}
			if (StringUtil.isNotEmpty(trainParam.getTrainNo())) {
				sql += " and tt.train_no = #{trainNo}";
			}
			sql += " order by online desc";
			return sql;
		}
	}
}
