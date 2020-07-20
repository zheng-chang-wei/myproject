/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.dictionary.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirain.phm.bd.ground.common.model.ResultBean;
import com.hirain.phm.bd.ground.dictionary.DictionaryCopyService;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Apr 16, 2020 11:31:44 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Apr 16, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@RestController
@RequestMapping("/dictionary")
public class DictioaryController {

	@Autowired(required = false)
	private List<DictionaryCopyService> serviceList;

	@PostMapping("/copy")
	public ResultBean<String> copy(String srcProject, String destProject) {
		if (serviceList != null) {
			serviceList.forEach(s -> s.copy(srcProject, destProject));
		}
		return new ResultBean<>("复制成功");
	}
}
