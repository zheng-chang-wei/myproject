package com.hirain.phm.bd.ground.authority.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.UpdateProvider;

import com.hirain.phm.bd.ground.authority.domain.Dept;
import com.hirain.phm.bd.ground.common.config.CommonMapper;

public interface DeptMapper extends CommonMapper<Dept> {
	
	@Select("select dept_name from t_dept where dept_id=#{deptId}")
	String findDeptNameById(Long deptId);

	// 删除父节点，子节点变成顶级节点（根据实际业务调整）
	@UpdateProvider(type = DeptMapperProvider.class, method = "changeToTop")
	void changeToTop(@Param("deptIds") List<String> deptIds);
	
	class DeptMapperProvider {
		public String changeToTop(@Param("deptIds") List<String> deptIds) {
			String sql = "UPDATE t_dept set parent_id = 0 where dept_id in ( select a.dept_id from ( (select dept_id from t_dept where parent_id in ( ";
			for(int i = 0; i < deptIds.size(); i++) {
				if(i != 0) {
					sql += ",";
				}
				String id = deptIds.get(i);
				sql += " '" + id +  "' ";
			}
			sql += ") ) )  a )";
			return sql;
		}
	}
}