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
import com.hirain.phm.bd.ground.dictionary.domain.SecondComponent;
import com.hirain.phm.bd.ground.dictionary.service.SecondComponentService;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Mar 31, 2020 2:20:14 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Mar 31, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@RestController
@RequestMapping("/dictionary/component2")
public class SecondComponentController {

	@Autowired
	private SecondComponentService service;

	@GetMapping("/list")
	public ResultBean<List<SecondComponent>> list(Integer parentId) {
		return new ResultBean<>(service.getAllActive(parentId));
	}

	@PostMapping("/insert")
	@Transactional
	public ResultBean<String> insert(@RequestBody SecondComponent component) {
		if (StringUtils.isEmpty(component.getName())) {
			return ResultBean.error("名称不能为空");
		}
		boolean duplicate = service.checkDuplicate(component);
		if (duplicate) {
			return ResultBean.error("二级部件重复");
		}
		component.setActive(true);
		service.save(component);
		return new ResultBean<>("添加成功");
	}

	@PostMapping("/update")
	@Transactional
	public ResultBean<String> update(@RequestBody SecondComponent component) {
		if (StringUtils.isEmpty(component.getName())) {
			return ResultBean.error("名称不能为空");
		}
		boolean duplicate = service.checkDuplicate(component);
		if (duplicate) {
			return ResultBean.error("二级部件重复");
		}
		service.updateAll(component);
		return new ResultBean<>("修改成功");
	}

	@PostMapping("/delete")
	@Transactional
	public ResultBean<String> delete(@RequestBody SecondComponent component) {
		service.deactive(component);
		return new ResultBean<>("删除成功");
	}
}
