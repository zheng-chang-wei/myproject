/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.dictionary.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirain.phm.bd.ground.common.model.ResultBean;
import com.hirain.phm.bd.ground.dictionary.domain.Repair;
import com.hirain.phm.bd.ground.dictionary.service.FaultRepairService;
import com.hirain.phm.bd.ground.dictionary.service.RepairService;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Apr 9, 2020 10:11:13 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Apr 9, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@RestController
@RequestMapping("/dictionary/repair")
public class RepairController {

	@Autowired
	private RepairService service;

	@Autowired
	private FaultRepairService frService;

	@GetMapping("/all")
	public ResultBean<List<Repair>> list() {
		return new ResultBean<>(service.selectAll());
	}

	@PostMapping("/insert")
	@Transactional
	public ResultBean<String> insert(@RequestBody Repair repair) {
		service.save(repair);
		return new ResultBean<>("添加成功");
	}

	@PostMapping("/update")
	@Transactional
	public ResultBean<String> update(@RequestBody Repair repair) {
		service.updateAll(repair);
		return new ResultBean<>("修改成功");
	}

	@PostMapping("/delete")
	@Transactional
	public ResultBean<String> delete(Integer id) {
		frService.deleteByRepairId(id);
		Repair repair = new Repair();
		repair.setId(id);
		service.delete(id);
		return new ResultBean<>("删除成功");
	}
}
