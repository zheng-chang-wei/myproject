/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.result.ecn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirain.phm.synapsis.response.ResultBean;
import com.hirain.phm.synapsis.result.ecn.service.ResultSettingService;
import com.hirain.phm.synapsis.result.ecn.vo.AlgorithmOutputVO;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 22, 2020 3:49:03 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 22, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@RestController
@RequestMapping("/result/ecn")
public class ECNResultController {

	@Autowired
	private ResultSettingService service;

	@PostMapping("/save")
	public ResultBean<String> save(Integer settingId, @RequestBody AlgorithmOutputVO vo) {
		service.save(settingId, vo);
		return new ResultBean<>("保存成功");
	}

}
