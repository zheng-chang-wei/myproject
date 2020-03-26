package com.hirain.phm.bd.ground.life.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.hirain.phm.bd.ground.common.config.CommonMapper;
import com.hirain.phm.bd.ground.life.domain.LifeProjectInfo;

public interface LifeProjectInfoMapper extends CommonMapper<LifeProjectInfo> {

	@Select("select * from t_life_project_info where project_id=#{projectId}")
	List<LifeProjectInfo> findProjectLifeInfo(Integer projectId);
}