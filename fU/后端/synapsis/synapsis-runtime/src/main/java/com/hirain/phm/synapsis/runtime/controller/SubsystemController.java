/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.runtime.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirain.phm.synapsis.page.PageService;
import com.hirain.phm.synapsis.page.QueryRequest;
import com.hirain.phm.synapsis.response.PageResultBean;
import com.hirain.phm.synapsis.response.ResultBean;
import com.hirain.phm.synapsis.runtime.param.SubsystemGroupItem;
import com.hirain.phm.synapsis.setting.AlgorithmSetting;
import com.hirain.phm.synapsis.setting.Subsystem;
import com.hirain.phm.synapsis.setting.db.SettingDbService;
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

	@Autowired
	private SettingDbService dbService;

	@Autowired
	private PageService pageService;

	// @GetMapping("/list")
	// public PageResultBean<List<SubsystemGroupItem>> listSubsystemByParms(QueryRequest request, String name) {
	// return subsystemService.listSubsystemByParms(request, name);
	// }

	@GetMapping("/list")
	public PageResultBean<List<SubsystemGroupItem>> list(QueryRequest request, String name) {
		PageResultBean<List<Subsystem>> pageBean = pageService.selectByPage(request, () -> subsystemService.selectByLikeName(name));
		List<SubsystemGroupItem> list = getSubsystemUsed(pageBean.getData());
		return new PageResultBean<>(list, pageBean.getTotal());
	}

	/**
	 * @param data
	 * @return
	 */
	private List<SubsystemGroupItem> getSubsystemUsed(List<Subsystem> subsystems) {
		List<SubsystemGroupItem> list = new ArrayList<>();
		for (Subsystem subsystem : subsystems) {
			SubsystemGroupItem subsystemGroupItem = new SubsystemGroupItem();
			subsystemGroupItem.setId(subsystem.getId());
			subsystemGroupItem.setName(subsystem.getName());
			subsystemGroupItem.setDescription(subsystem.getDescription());
			List<AlgorithmSetting> algorithmSettings = dbService.selectAlgorithmBySubsystemId(subsystem.getId());
			if (algorithmSettings.size() == 0) {
				subsystemGroupItem.setIsUsed(false);
			}
			subsystemGroupItem.setCount(algorithmSettings.size());
			list.add(subsystemGroupItem);
		}
		return list;
	}

	// @PostMapping("/save")
	// public ResultBean<String> save(Subsystem subsystem) {
	// if (subsystemService.isRepeat(subsystem)) {
	// return new ResultBean<>("分组名称重复", null);
	// } else {
	// subsystemService.save(subsystem);
	// return new ResultBean<>("添加成功");
	// }
	// }
	@PostMapping("/save")
	public ResultBean<String> save(@RequestBody List<Subsystem> subsystems) {
		subsystemService.save(subsystems);
		return new ResultBean<>("添加成功");
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
