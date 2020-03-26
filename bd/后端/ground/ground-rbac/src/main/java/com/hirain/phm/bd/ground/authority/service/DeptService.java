package com.hirain.phm.bd.ground.authority.service;

import java.util.List;

import com.hirain.phm.bd.ground.authority.domain.Dept;
import com.hirain.phm.bd.ground.common.model.Tree;
import com.hirain.phm.bd.ground.common.service.IService;


public interface DeptService extends IService<Dept> {

	Tree<Dept> getDeptTree();

	List<Dept> findAllDepts(Dept dept);

	Dept findByName(String deptName);

	Dept findById(Long deptId);

	void addDept(Dept dept) throws Exception;

	void updateDept(Dept dept) throws Exception;

	void deleteDepts(String deptIds);
}
