/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.runtime.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirain.phm.synapsis.page.QueryRequest;
import com.hirain.phm.synapsis.response.PageResultBean;
import com.hirain.phm.synapsis.response.ResultBean;
import com.hirain.phm.synapsis.setting.Subsystem;
import com.hirain.phm.synapsis.setting.SubsystemGroupItem;
import com.hirain.phm.synapsis.setting.db.SubsystemService;

/**
 * @Version 1.0
 * @Author changwei.zheng@hirain.com
 * @Created 2020年2月24日 下午8:27:58
 * @Description
 *              <p>
 *              算法分组（子系统）controller
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2020年2月24日 changwei.zheng@hirain.com 1.0 create file
 */
@RestController
@RequestMapping("/subsystem")
public class SubsystemController {

	@Autowired
	private SubsystemService subsystemService;

	@GetMapping("/listSubsystemByParms")
	public PageResultBean<List<SubsystemGroupItem>> listSubsystemByParms(QueryRequest request, String name) {
		return subsystemService.listSubsystemByParms(request, name);
	}

	@PostMapping("/save")
	public ResultBean<String> save(Subsystem subsystem) {
		if (subsystemService.isRepeat(subsystem)) {
			return new ResultBean<>("分组名称重复", null);
		} else {
			subsystemService.save(subsystem);
			return new ResultBean<>("添加成功");
		}
	}

	@PostMapping("/update")
	public ResultBean<String> update(Subsystem subsystem) {
		if (subsystemService.isRepeat(subsystem)) {
			return new ResultBean<>("分组名称重复", null);
		} else {
			subsystemService.update(subsystem);
			return new ResultBean<>("编辑成功");
		}
	}

	@PostMapping("/deleteById")
	public ResultBean<String> deleteById(Integer[] ids) {
		subsystemService.deleteById(ids);
		return new ResultBean<>("删除成功");
	}

}
