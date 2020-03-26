package com.hirain.phm.bd.ground.authority.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.hirain.phm.bd.ground.authority.domain.GroundRoleMenu;
import com.hirain.phm.bd.ground.authority.domain.Menu;
import com.hirain.phm.bd.ground.common.config.CommonMapper;

public interface GroundRoleMenuMapper extends CommonMapper<GroundRoleMenu> {

	@Select("select menu_id from t_ground_role_menu where ground_role_id=#{groundRoleId}")
	List<Long> getGroundRoleMenusByRoleID(Long groundRoleId);

	@Select("select * from t_menu where menu_id in (select menu_id from t_ground_role_menu where ground_role_id=#{roleId})")
	List<Menu> findMenusByRoleId(Long roleId);

}