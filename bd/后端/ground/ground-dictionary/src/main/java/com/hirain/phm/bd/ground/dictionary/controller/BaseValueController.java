/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.dictionary.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirain.phm.bd.ground.common.model.ResultBean;
import com.hirain.phm.bd.ground.dictionary.domain.BaseValue;
import com.hirain.phm.bd.ground.dictionary.service.BaseValueService;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Apr 15, 2020 10:26:37 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Apr 15, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@RestController
@RequestMapping("/dictionary/basevalue")
public class BaseValueController {

	@Autowired
	private BaseValueService service;

	@GetMapping("/list")
	public ResultBean<List<BaseValue>> list(Integer projectId) {
		return new ResultBean<>(service.list(projectId));
	}

	@PostMapping("/update")
	public ResultBean<String> update(@RequestBody BaseValue value) {
		service.updateAll(value);
		return new ResultBean<>("更新成功");
	}
}
