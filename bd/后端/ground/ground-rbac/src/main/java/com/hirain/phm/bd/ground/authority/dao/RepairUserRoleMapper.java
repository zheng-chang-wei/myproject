package com.hirain.phm.bd.ground.authority.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.hirain.phm.bd.ground.authority.domain.RepairUserRole;
import com.hirain.phm.bd.ground.common.config.CommonMapper;

public interface RepairUserRoleMapper extends CommonMapper<RepairUserRole> {

	@Select("select repair_role_id from t_repair_user_role where user_id=#{userId}")
	List<Integer> getRepairRolesByUserId(Long userId);
}
