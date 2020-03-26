/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.rbac.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.hirain.phm.synapsis.rbac.CommonMapper;
import com.hirain.phm.synapsis.rbac.domain.User;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Mar 13, 2020 6:12:32 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Mar 13, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public interface UserMapper extends CommonMapper<User> {

	/**
	 * @param username
	 * @return
	 */
	@Select("select id,username,password from t_user where username=#{username}")
	User selectByName(String username);

	/**
	 * @return
	 */
	@Select("select tu.id,tu.username,tu.password from t_user tu")
	@Results({

			@Result(property = "id", column = "id", id = true),
			@Result(property = "role", column = "id", one = @One(select = "com.hirain.phm.synapsis.rbac.dao.RoleMapper.getRoleAll"))

	})
	List<User> listAll();

	/**
	 * @param username
	 * @return
	 */
	@Select("select tu.id,tu.username,tu.password from t_user tu where tu.username=#{username}")
	@Results({

			@Result(property = "id", column = "id", id = true),
			@Result(property = "role", column = "id", one = @One(select = "com.hirain.phm.synapsis.rbac.dao.RoleMapper.getRoleAll"))

	})
	User selectUserInfo(String username);

	/**
	 * @param id
	 * @param id2
	 */
	@Update("update t_user_role set role_id=#{roleId} where user_id=#{userId}")
	int updateUserRole(Integer userId, Integer roleId);

	/**
	 * @param id
	 * @param id2
	 */
	@Insert("insert into t_user_role values(#{userId},#{roleId})")
	int insertUserRole(Integer userId, Integer roleId);

	/**
	 * @param id
	 */
	@Delete("delete from t_user_role where user_id=#{id}")
	int deleteUserRole(int id);

}
