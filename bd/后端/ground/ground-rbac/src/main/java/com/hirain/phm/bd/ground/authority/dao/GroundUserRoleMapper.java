package com.hirain.phm.bd.ground.authority.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.hirain.phm.bd.ground.authority.domain.GroundUserRole;
import com.hirain.phm.bd.ground.common.config.CommonMapper;

public interface GroundUserRoleMapper extends CommonMapper<GroundUserRole> {
	
	@Select("select ground_role_id from t_ground_user_role where user_id=#{userId}")
	List<Long> getGroundRolesByUserID(Long userId);
}