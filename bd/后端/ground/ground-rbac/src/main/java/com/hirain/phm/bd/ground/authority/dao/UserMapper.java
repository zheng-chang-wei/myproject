package com.hirain.phm.bd.ground.authority.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import com.hirain.phm.bd.ground.authority.controller.UserRequest;
import com.hirain.phm.bd.ground.authority.domain.User;
import com.hirain.phm.bd.ground.common.config.CommonMapper;

import tk.mybatis.mapper.util.StringUtil;

public interface UserMapper extends CommonMapper<User> {

	@SelectProvider(type = UserMapperProvider.class, method = "finduserWithCondition")
	@Results({ @Result(property = "userId", column = "userId", id = true),
			@Result(property = "deptName", column = "dept_id", one = @One(select = "com.hirain.phm.bd.ground.authority.dao.DeptMapper.findDeptNameById")),
			@Result(property = "groundRoles", column = "userId", id = true, many = @Many(select = "com.hirain.phm.bd.ground.authority.dao.GroundRoleMapper.findUserRoleById")),
			@Result(property = "projects", column = "userId", id = true, many = @Many(select = "com.hirain.phm.bd.ground.authority.dao.ProjectUserMapper.findProjectByUserId")) })
	List<User> findUserWithCondition(@Param("user") UserRequest user);

	@Select("SELECT" + "	u.user_id as userId, " + "	u.user_name, " + "	u.name, " + "   u.password," + "	u.dept_id, " + "	u.email, "
			+ "	u.mobile, " + "	u.wechat, " + "	u.email" + " FROM" + " t_user u " + "WHERE " + " u.user_id = #{userId}")
	@Results({ @Result(property = "userId", column = "userId", id = true),
			@Result(property = "approvalRoleNames", column = "userId", id = true, many = @Many(select = "com.hirain.phm.bd.ground.authority.dao.RepairRoleMapper.findApprovalRoleNameByUserId")),
			@Result(property = "groundRoles", column = "userId", id = true, many = @Many(select = "com.hirain.phm.bd.ground.authority.dao.GroundRoleMapper.findUserRoleById")),
			@Result(property = "projects", column = "userId", id = true, many = @Many(select = "com.hirain.phm.bd.ground.authority.dao.ProjectUserMapper.findProjectByUserId")) })
	User findUserWithRole(Long userId);

	class UserMapperProvider {

		SimpleDateFormat formatter = (SimpleDateFormat) SimpleDateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);

		public String finduserWithCondition(@Param("user") UserRequest user) {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT u.user_id AS userId," +
			//
					"	u.user_name," +
					//
					"	u.PASSWORD," +
					//
					"	u.NAME," +
					//
					"	u.dept_id," +
					//
					"	u.email," +
					//
					"	u.mobile," +
					//
					"	u.wechat," +
					//
					"	u.create_time" +
					//
					"  FROM" +
					//
					"	t_user u " +
					//
					" LEFT JOIN t_ground_user_role ON u.USER_ID = t_ground_user_role.USER_ID " +
					//
					" LEFT JOIN t_ground_role ON t_ground_role.GROUND_ROLE_ID = t_ground_user_role.GROUND_ROLE_ID  ");
			sql.append("where true ");
			if (StringUtil.isNotEmpty(user.getUserName())) {
				sql.append(" and u.user_name like '%").append(user.getUserName()).append("%'");
			}
			if (StringUtil.isNotEmpty(user.getRoleName())) {
				sql.append(" and t_ground_role.ROLE_NAME like '%").append(user.getRoleName()).append("%'");
			}
			if (StringUtil.isNotEmpty(user.getName())) {
				sql.append(" and u.name like '%").append(user.getName()).append("%'");
			}
			if (StringUtil.isNotEmpty(user.getEmail())) {
				sql.append(" and u.email like '%").append(user.getEmail()).append("%'");
			}
			if (StringUtil.isNotEmpty(user.getMobile())) {
				sql.append(" and u.mobile like '%").append(user.getMobile()).append("%'");
			}
			if (StringUtil.isNotEmpty(user.getDeptName())) {
				sql.append(" and u.dept_id in (");
				sql.append(" select d.dept_id from t_dept d where d.dept_name like '%").append(user.getDeptName()).append("%')");
			}
			if (user.getStarttime() != null) {
				sql.append(" and u.create_time >= '").append(formatter.format(user.getStarttime())).append("'");
			}
			if (user.getEndtime() != null) {
				sql.append(" and u.create_time <= '").append(formatter.format(user.getEndtime())).append("'");
			}
			sql.append(" ORDER BY u.CREATE_TIME DESC");
			return sql.toString();
		}
	}
}