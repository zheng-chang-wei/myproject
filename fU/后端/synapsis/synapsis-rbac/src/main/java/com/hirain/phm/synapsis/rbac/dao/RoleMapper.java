/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.rbac.dao;

import org.apache.ibatis.annotations.Select;

import com.hirain.phm.synapsis.rbac.CommonMapper;
import com.hirain.phm.synapsis.rbac.domain.Role;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Mar 13, 2020 6:18:32 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Mar 13, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public interface RoleMapper extends CommonMapper<Role> {

	@Select("select tr.id,tr.cname,tr.rolename from t_role tr left join t_user_role tur on tr.id=tur.role_id where tur.user_id=#{userId}")
	Role getRoleAll(int userId);
}
