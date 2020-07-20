/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.maintenance.controller;

import java.util.Arrays;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirain.phm.bd.ground.authority.domain.User;
import com.hirain.phm.bd.ground.common.model.ResultBean;
import com.hirain.phm.bd.ground.maintenance.domain.ConfigVariable;
import com.hirain.phm.bd.ground.maintenance.service.ConfigVariableService;

/**
 * @Version 1.0
 * @Author changwei.zheng@hirain.com
 * @Created 2020年3月4日 下午5:55:14
 * @Description
 *              <p>
 *              自定义工单变量controller
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2020年3月4日 changwei.zheng@hirain.com 1.0 create file
 */
@RestController
@RequestMapping("/config")
public class ConfigVariableController {

	@Autowired
	private ConfigVariableService configVariableService;

	@GetMapping("/listVariable")
	public ResultBean<List<ConfigVariable>> listVariable() {
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		return new ResultBean<>(configVariableService.listVariable(user.getId()));
	}

	@PostMapping("/save")
	public ResultBean<String> save(ConfigVariable configVariable) {
		if (configVariableService.isRepeat(configVariable)) {
			return new ResultBean<>("分组名称重复", null);
		} else {
			User user = (User) SecurityUtils.getSubject().getPrincipal();
			configVariable.setUserId(user.getId());
			configVariableService.save(configVariable);
			return new ResultBean<>("添加成功");
		}
	}

	@PostMapping("/update")
	public ResultBean<String> update(ConfigVariable configVariable) {
		if (configVariableService.isRepeat(configVariable)) {
			return new ResultBean<>("分组名称重复", null);
		} else {
			configVariableService.updateNotNull(configVariable);
			return new ResultBean<>("修改成功");
		}
	}

	@PostMapping("/delete")
	public ResultBean<String> delete(String[] ids) {
		configVariableService.batchDelete(Arrays.asList(ids), "id", ConfigVariable.class);
		return new ResultBean<>("删除成功");
	}

}
