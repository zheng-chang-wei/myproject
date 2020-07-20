/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.dictionary.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hirain.phm.bd.ground.common.model.ResultBean;
import com.hirain.phm.bd.ground.dictionary.domain.DoorType;
import com.hirain.phm.bd.ground.dictionary.service.DoorTypeService;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Apr 9, 2020 5:51:47 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Apr 9, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@RestController
@RequestMapping("/dictionary/doortype")
public class DoorTypeController {

	@Autowired
	private DoorTypeService service;

	@GetMapping("/list")
	public ResultBean<List<DoorType>> list() {
		return new ResultBean<>(service.selectAllActive());
	}

	@PostMapping("/import")
	@Transactional
	public ResultBean<String> importDoortype(MultipartFile file, DoorType doorType) throws IOException {
		service.batchImport(doorType, file);
		return new ResultBean<>("导入成功");
	}

	@PostMapping("/insert")
	@Transactional
	public ResultBean<String> insert(@RequestBody DoorType doorType) {
		if (StringUtils.isEmpty(doorType.getName())) {
			return ResultBean.error("名称不能为空");
		}
		boolean duplicate = service.checkDuplicate(doorType);
		if (duplicate) {
			return ResultBean.error("车门类型重复");
		}
		doorType.setActive(true);
		service.save(doorType);
		return new ResultBean<>("添加成功");
	}

	@PostMapping("/update")
	@Transactional
	public ResultBean<String> update(@RequestBody DoorType doorType) {
		if (StringUtils.isEmpty(doorType.getName())) {
			return ResultBean.error("名称不能为空");
		}
		boolean duplicate = service.checkDuplicate(doorType);
		if (duplicate) {
			return ResultBean.error("车门类型重复");
		}
		service.updateAll(doorType);
		return new ResultBean<>("修改成功");
	}

	@PostMapping("/delete")
	@Transactional
	public ResultBean<String> delete(@RequestBody DoorType doorType) {
		service.deactive(doorType);
		return new ResultBean<>("删除成功");
	}
}
