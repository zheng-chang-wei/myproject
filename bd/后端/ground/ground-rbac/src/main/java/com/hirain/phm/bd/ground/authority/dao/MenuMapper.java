package com.hirain.phm.bd.ground.authority.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import com.hirain.phm.bd.ground.authority.domain.Menu;
import com.hirain.phm.bd.ground.common.config.CommonMapper;

public interface MenuMapper extends CommonMapper<Menu> {

	@SelectProvider(type = MenuMapperProvider.class, method = "findUserPermissions")
	List<Menu> findUserPermissions(String userName);

	@SelectProvider(type = MenuMapperProvider.class, method = "findUserMenus")
	List<Menu> findUserMenus(String userName);

	@Select("select * from t_menu")
	List<Menu> findAllMenus();

	// 删除父节点，子节点变成顶级节点（根据实际业务调整
	@UpdateProvider(type = MenuMapperProvider.class, method = "changeToTop")
	void changeToTop(@Param("menuIds") List<String> menuIds);

	@Select("select m.* from t_menu m left join t_ground_role_menu rm on(rm.menu_id = m.menu_id) where rm.ground_role_id=#{groundRoleId}")
	List<Menu> findMenuListByRoleId(Long groundRoleId);
	
	class MenuMapperProvider {
		
		public String findUserMenus(String userName) {
			return "select m.* from t_menu m" + 
					" where  m.MENU_ID in " + 
					" (select distinct rm.menu_id from t_ground_role_menu rm " + 
					" left join t_ground_role r on(rm.ground_role_id = r.ground_role_id)" + 
					" left join t_ground_user_role ur on(ur.ground_role_id = r.ground_role_id)" + 
					" left join t_user u on (u.user_id = ur.user_id)" + 
					" where u.user_name = #{userName}) and m.type=0 order by m.create_time";
		}
		
		public String changeToTop(@Param("menuIds") List<String> menuIds) {
			String sql = "UPDATE t_menu set parent_id = 0 where menu_id in ( select a.menu_id from ( (select menu_id from t_menu where parent_id in ( ";
			for(int i = 0; i < menuIds.size(); i++) {
				if(i != 0) {
					sql += ",";
				}
				String id = menuIds.get(i);
				sql += " '" + id +  "' ";
			}
			sql += ") ) )  a )";
			return sql;
		}
		
		public String findUserPermissions(String userName) {
		
			return "select m.perms from t_ground_role r" + 
				" left join t_ground_user_role ur on(r.ground_role_id = ur.ground_role_id) " + 
				" left join t_user u on(u.user_id = ur.user_id)" + 
				" left join t_ground_role_menu rm on(rm.ground_role_id = r.ground_role_id) " + 
				" left join t_menu m on(m.menu_id = rm.menu_id ) " + 
				" where u.user_name = #{userName} and m.perms is not null and m.perms <> ''";
		}
	}
}