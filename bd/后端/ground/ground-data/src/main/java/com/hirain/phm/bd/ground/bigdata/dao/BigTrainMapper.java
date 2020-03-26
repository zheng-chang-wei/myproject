/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.bigdata.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import com.hirain.phm.bd.ground.bigdata.domain.BigTrainEntity;
import com.hirain.phm.bd.ground.bigdata.param.GroundData;
import com.hirain.phm.bd.ground.bigdata.param.GroundDataParam;
import com.hirain.phm.bd.ground.common.config.CommonMapper;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年6月5日 下午5:40:39
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年6月5日 jianwen.xin@hirain.com 1.0 create file
 */
public interface BigTrainMapper extends CommonMapper<BigTrainEntity> {

	@SelectProvider(type = BigTrainProvider.class, method = "findBigTrainByParam")
	List<GroundData> findBigTrainByParam(GroundDataParam param);

	class BigTrainProvider {

		public String findBigTrainByParam(GroundDataParam param) {
			String sql = "select tb.id,tp.name project,tt.train_no train, tb.start_day,tb.end_day\r\n"

					+ "from t_big_train tb \r\n"

					+ "left join t_train tt on tt.id=tb.train_id\r\n"

					+ "left join t_project tp on tp.id=tt.project_id\r\n"

					+ "where true";
			if (param.getProject() != null) {
				sql += " and tp.name=#{project}";
			}
			if (param.getTrain() != null) {
				sql += " and tt.train_no=#{train}";
			}
			return sql;
		}
	}

	@Select("select * from t_big_train where train_id=#{trainId}")
	BigTrainEntity selectByTrainId(Integer trainId);
}
