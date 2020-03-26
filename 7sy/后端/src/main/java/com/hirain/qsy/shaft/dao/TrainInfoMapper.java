package com.hirain.qsy.shaft.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import com.hirain.qsy.shaft.common.config.MyMapper;
import com.hirain.qsy.shaft.model.TrainInfo;

import tk.mybatis.mapper.util.StringUtil;

public interface TrainInfoMapper extends MyMapper<TrainInfo> {

	/**
	 * 查询所有的车型
	 * 
	 * @return
	 */
	@Select("select distinct train_type from t_train_info")
	List<String> queryAllTrainType();

	@SelectProvider(type = TrainInfoMapperProvider.class, method = "queryTrainNumByType")
	List<String> queryTrainNumByType(@Param("traintype") String traintype);

	@Select("select distinct train_num from t_train_info order by train_num")
	List<String> queryTrainNums();

	@Select("select  id from t_train_info where train_num=#{trainNum}")
	List<Integer> findIdByTrainNum(String trainNum);

	@Select("select id from t_train_info where train_num=#{trainNum} and train_type=#{trainType}")
	Integer findTrainInfoByTypeAndNum(String trainNum, String trainType);

	class TrainInfoMapperProvider {

		public String queryTrainNumByType(String traintype) {
			StringBuilder sql = new StringBuilder();
			sql.append(" select distinct train_num from t_train_info  ");
			sql.append("where true ");
			if (StringUtil.isNotEmpty(traintype)) {
				sql.append(" and train_type='").append(traintype).append("'");
			}
			sql.append(" ORDER BY train_num");
			return sql.toString();
		}
	}
}