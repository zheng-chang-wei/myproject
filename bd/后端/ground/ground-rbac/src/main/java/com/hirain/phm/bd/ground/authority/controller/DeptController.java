package com.hirain.phm.bd.ground.authority.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirain.phm.bd.ground.authority.domain.Dept;
import com.hirain.phm.bd.ground.authority.service.DeptService;
import com.hirain.phm.bd.ground.common.annotation.Log;
import com.hirain.phm.bd.ground.common.model.ResponseBo;
import com.hirain.phm.bd.ground.common.model.Tree;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/dept")
@Slf4j
public class DeptController {

	@Autowired
	private DeptService deptService;

	@GetMapping("/tree")
	public ResponseBo getDeptTree() {
		try {
			Tree<Dept> tree = deptService.getDeptTree();
			return ResponseBo.ok(tree.getChildren());
		} catch (Exception e) {
			log.error("获取部门树失败", e);
			return ResponseBo.error("获取部门树失败！");
		}
	}

	/**
	 * @unused
	 * @param deptId
	 * @return
	 */
	@GetMapping("/getDept")
	public ResponseBo getDept(Long deptId) {
		try {
			Dept dept = deptService.findById(deptId);
			return ResponseBo.ok(dept);
		} catch (Exception e) {
			log.error("获取部门信息失败", e);
			return ResponseBo.error("获取部门信息失败，请联系网站管理员！");
		}
	}

	@GetMapping("/list")
	public ResponseBo deptList() {
		try {
			return ResponseBo.ok(deptService.selectAll());
		} catch (Exception e) {
			log.error("获取部门列表失败", e);
			return ResponseBo.error("获取部门信息失败，请联系网站管理员！");
		}
	}

	@PostMapping("/add")
	public ResponseBo addDepts(Dept dept) {
		try {
			deptService.addDept(dept);
			return ResponseBo.ok("新增部门成功！");
		} catch (Exception e) {
			log.error("新增部门失败", e);
			return ResponseBo.error(e.getMessage());
		}
	}

	@PostMapping("/delete")
	@Log("删除部门")
	public ResponseBo deleteDepts(String ids) {
		try {
			deptService.deleteDepts(ids);
			return ResponseBo.ok("删除部门成功！");
		} catch (Exception e) {
			log.error("删除部门失败", e);
			return ResponseBo.error("删除部门失败，请联系网站管理员！");
		}
	}

	@PostMapping("/update")
	@Log("编辑部门")
	public ResponseBo updateDept(Dept dept) {
		try {
			deptService.updateDept(dept);
			return ResponseBo.ok("修改部门成功！");
		} catch (Exception e) {
			log.error("修改部门失败", e);
			return ResponseBo.error(e.getMessage());
		}
	}
}
