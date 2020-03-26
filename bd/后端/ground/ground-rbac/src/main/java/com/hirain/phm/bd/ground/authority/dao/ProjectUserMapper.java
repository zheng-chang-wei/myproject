package com.hirain.phm.bd.ground.authority.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import com.hirain.phm.bd.ground.authority.domain.ProjectUser;
import com.hirain.phm.bd.ground.authority.domain.User;
import com.hirain.phm.bd.ground.common.config.CommonMapper;
import com.hirain.phm.bd.ground.train.domain.Project;

public interface ProjectUserMapper extends CommonMapper<ProjectUser> {

	@Select("select project_id from t_project_user where user_id=#{userId}")
	List<Long> getProjectUserRolesByUserID(Long userId);

	@SelectProvider(type = ProjectUserSelectProvider.class, method = "getUsersOfLine")
	List<User> getUsersOfProject(String project);

	/**
	 * UserMapper 注解中依赖
	 * 
	 * @param userId
	 * @return
	 */
	@Select("select t_project.* from t_project left join t_project_user on t_project.id = t_project_user.project_id where t_project_user.user_id=#{userId}")
	List<Project> findProjectByUserId(Integer userId);

	class ProjectUserSelectProvider {

		public String getUsersOfLine(String project) {
			String sql = "select tu.user_name,tu.password from t_project_user tlu";
			sql += " left join t_project tp on tp.id=tlu.project_id";
			sql += " left join t_user tu on tu.user_id=tlu.user_id";
			sql += " where tp.name=#{project}";
			return sql;
		}
	}

	@Select("select t_user.* from t_project_user left join t_user on t_user.user_id=t_project_user.user_id where t_project_user.project_id=#{projectId}")
	List<User> getUsersByProjectID(Integer lineId);
}
