/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.runtime.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirain.phm.synapsis.exception.SynapsisException;
import com.hirain.phm.synapsis.response.ResultBean;
import com.hirain.phm.synapsis.setting.VariableGroup;
import com.hirain.phm.synapsis.setting.support.ProtocolService;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Apr 24, 2020 9:06:33 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Apr 24, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@RestController
@RequestMapping("/protocol")
public class ProtocolController {

	@Autowired
	private ProtocolService service;

	@GetMapping("/variables")
	public ResultBean<List<VariableGroup>> getVariables(Integer settingId, String type) throws SynapsisException {
		return new ResultBean<>(service.getVariables(settingId, type));
	}
}
