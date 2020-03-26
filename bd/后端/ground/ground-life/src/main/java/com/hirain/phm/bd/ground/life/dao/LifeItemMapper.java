package com.hirain.phm.bd.ground.life.dao;

import org.apache.ibatis.annotations.Select;

import com.hirain.phm.bd.ground.common.config.CommonMapper;
import com.hirain.phm.bd.ground.life.domain.LifeItem;

public interface LifeItemMapper extends CommonMapper<LifeItem> {

	/**
	 * 通过id号查找寿命项点
	 */
	@Select("select * from t_life_item where id=#{id}")
	LifeItem findLifeItemByID(Integer id);
}