package com.hirain.phm.bd.ground.authority.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.hirain.phm.bd.ground.authority.domain.RepairRole;
import com.hirain.phm.bd.ground.common.config.CommonMapper;


public interface RepairRoleMapper extends CommonMapper<RepairRole> {

	@Select("SELECT ar.role_name FROM t_repair_role ar LEFT JOIN t_repair_user_role aru ON ar.repair_role_id = aru.repair_role_id WHERE aru.user_id = #{userId}")
	List<String> findApprovalRoleNameByUserId(Long userId);
}
