package com.hirain.phm.bd.ground.authority.dao;

import java.util.List;

import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import com.github.pagehelper.util.StringUtil;
import com.hirain.phm.bd.ground.authority.domain.GroundRole;
import com.hirain.phm.bd.ground.common.config.CommonMapper;

public interface GroundRoleMapper extends CommonMapper<GroundRole> {

	@Select("select r.* from t_ground_role r left join t_ground_user_role ur on(r.ground_role_id = ur.ground_role_id) left join t_user u on(u.user_id = ur.user_id) where u.user_name = #{userName}")
	List<GroundRole> findUserRoleByUserName(String userName);

	@Select("select r.* from t_ground_role r left join t_ground_user_role ur on(r.ground_role_id = ur.ground_role_id) left join t_user u on(u.user_id = ur.user_id) where u.user_id = #{userId}")
	List<GroundRole> findUserRoleById(Long userId);

	@Select("SELECT r.ground_role_id,r.role_name roleName,r.remark " + "FROM" + " t_ground_role r " + "WHERE "
			+ " r.ground_role_id = #{groundRoleId}")
	@Results({
			@Result(property = "menuIds", column = "ground_role_id", id = true, many = @Many(select = "com.hirain.phm.bd.ground.authority.dao.GroundRoleMenuMapper.getGroundRoleMenusByRoleID")) })
	GroundRole findRoleWithMenuById(Long groundRoleId);

	@SelectProvider(type = GroundRoleMapperProvider.class, method = "listRolesByRoleName")
	List<GroundRole> listRolesByRoleName(String roleName);

	class GroundRoleMapperProvider {

		public String listRolesByRoleName(String roleName) {
			String sql = "SELECT * FROM t_ground_role WHERE TRUE";
			if (StringUtil.isNotEmpty(roleName)) {
				sql += " AND role_name like #{roleName}";
			}
			return sql;
		}
	}
}