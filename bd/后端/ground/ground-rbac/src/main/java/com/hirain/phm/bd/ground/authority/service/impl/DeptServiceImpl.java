package com.hirain.phm.bd.ground.authority.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hirain.phm.bd.ground.authority.dao.DeptMapper;
import com.hirain.phm.bd.ground.authority.domain.Dept;
import com.hirain.phm.bd.ground.authority.service.DeptService;
import com.hirain.phm.bd.ground.authority.util.TreeUtils;
import com.hirain.phm.bd.ground.common.model.Tree;
import com.hirain.phm.bd.ground.common.service.BaseService;

import tk.mybatis.mapper.entity.Example;

@Service("deptService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DeptServiceImpl extends BaseService<Dept> implements DeptService {

	@Autowired
	private DeptMapper deptMapper;

	@Override
	public Tree<Dept> getDeptTree() {
		List<Tree<Dept>> trees = new ArrayList<>();
		List<Dept> depts = findAllDepts(new Dept());
		depts.forEach(dept -> {
			Tree<Dept> tree = new Tree<>();
			tree.setId(dept.getDeptId().toString());
			tree.setParentId(dept.getParentId().toString());
			tree.setText(dept.getDeptName());
			trees.add(tree);
		});
		return TreeUtils.build(trees);
	}

	@Override
	public List<Dept> findAllDepts(Dept dept) {
		Example example = new Example(Dept.class);
		if (StringUtils.isNotBlank(dept.getDeptName())) {
			example.createCriteria().andCondition("dept_name=", dept.getDeptName());
		}
		example.setOrderByClause("dept_id");
		return selectByExample(example);
	}

	@Override
	public Dept findByName(String deptName) {
		Example example = new Example(Dept.class);
		example.createCriteria().andCondition("lower(dept_name) =", deptName.toLowerCase());
		List<Dept> list = selectByExample(example);
		return list.isEmpty() ? null : list.get(0);
	}

	@Override
	@Transactional
	public void addDept(Dept dept) throws Exception {
		assert dept.getDeptName() != null;
		Dept dept2 = findByName(dept.getDeptName());
		if (dept2 != null) {
			throw new Exception("部门名称重复");
		}
		try {
			Long parentId = dept.getParentId();
			if (parentId == null) {
				dept.setParentId(0L);
			}
			dept.setCreateTime(new Date());
			save(dept);
		} catch (Exception e) {
			throw new Exception("新增部门失败，请联系网站管理员！", e);
		}
	}

	@Override
	@Transactional
	public void deleteDepts(String deptIds) {
		// TODO 部门有用户，如何处理
		List<String> list = Arrays.asList(deptIds.split(","));
		batchDelete(list, "deptId", Dept.class);
		deptMapper.changeToTop(list);
	}

	@Override
	public Dept findById(Long deptId) {
		return selectByKey(deptId);
	}

	@Override
	@Transactional
	public void updateDept(Dept dept) throws Exception {
		Dept dept2 = findByName(dept.getDeptName());
		if (dept2 != null) {
			if (!dept.getDeptId().equals(dept2.getDeptId())) {
				throw new Exception("部门名称重复");
			}
		}
		try {
			updateNotNull(dept);
		} catch (Exception e) {
			throw new Exception("修改部门失败，请联系网站管理员！", e);
		}
	}

}
