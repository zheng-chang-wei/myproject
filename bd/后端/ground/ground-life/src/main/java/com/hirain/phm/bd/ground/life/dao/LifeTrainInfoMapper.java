package com.hirain.phm.bd.ground.life.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.UpdateProvider;

import com.hirain.phm.bd.ground.common.config.CommonMapper;
import com.hirain.phm.bd.ground.life.domain.LifeTrainInfo;

public interface LifeTrainInfoMapper extends CommonMapper<LifeTrainInfo> {

	/**
	 * 根据trainID 从t_trainlife_info表中找到相应的数据集合
	 */
	@Select("select * from t_life_traininfo where train_id=#{trainID}")
	List<LifeTrainInfo> findTrainLifeInfosByTrainID(Integer trainID);

	/**
	 * 根据trainID 和 lifeitemID从t_trainlife_info表中找到相应的数据
	 */
	@Select("select * from t_life_traininfo where train_id=#{trainID} and lifeitem_id=#{itemID} ")
	LifeTrainInfo findTrainLifeInfoByTrainIDItemID(Integer trainID, Integer itemID);

	/**
	 * 更新指定数据
	 */
	@UpdateProvider(type = TrainLifeInfoMapperProvider.class, method = "updateTrainLifeInfoByParams")
	void updateTrainLifeInfo(LifeTrainInfo trainLifeInfo);

	class TrainLifeInfoMapperProvider {

		public String updateTrainLifeInfoByParams(LifeTrainInfo trainLifeInfo) {
			String sql = "update  t_life_traininfo set " +
			// 
					"	t_life_traininfo.reference_value=#{referenceValue}" +
					//
					"	t_life_traininfo.failures_nums=#{failuresNums}" +
					//
					"	t_life_traininfo.all_value=#{allValue}" + "  where true";

			sql += " and t_life_traininfo.train_id = #{trainId}";
			sql += " and t_life_traininfo.lifeitem_id = #{lifeitemId}";
			return sql;
		}

	}
}