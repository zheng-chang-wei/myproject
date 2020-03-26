/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.result.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.hirain.phm.synapsis.page.PageService;
import com.hirain.phm.synapsis.page.QueryRequest;
import com.hirain.phm.synapsis.parse.domain.FileHeader;
import com.hirain.phm.synapsis.parse.domain.VariableData;
import com.hirain.phm.synapsis.response.PageResultBean;
import com.hirain.phm.synapsis.response.ResultBean;
import com.hirain.phm.synapsis.result.domain.AlgorithmResult;
import com.hirain.phm.synapsis.result.param.ResultQueryParam;
import com.hirain.phm.synapsis.result.service.ResultService;
import com.hirain.phm.synapsis.setting.VariableGroup;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Feb 11, 2020 3:39:45 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Feb 11, 2020 jianwen.xin@hirain.com 1.0 create file
 * @Modification
 *               <p>
 *               Feb 21, 2020 zhaobin.su@hirain.com getmapping->postmapping
 */
@RestController
@RequestMapping("/result")
public class ResultController {

	@Autowired
	private ResultService resultService;

	@Autowired
	private PageService pageService;

	@PostMapping("/all")
	public PageResultBean<List<AlgorithmResult>> listAll(@RequestBody JSONObject request) {
		QueryRequest req = JSONObject.toJavaObject(request.getJSONObject("QueryRequest"), QueryRequest.class);
		return pageService.selectByPage(req, () -> resultService.listResults());
	}

	@PostMapping("/list")
	public ResultBean<List<AlgorithmResult>> listResults(@RequestBody JSONObject request) {
		QueryRequest req = JSONObject.toJavaObject(request.getJSONObject("QueryRequest"), QueryRequest.class);
		ResultQueryParam param = JSONObject.toJavaObject(request.getJSONObject("ResultQueryParam"), ResultQueryParam.class);
		return pageService.selectByPage(req, () -> resultService.listResults(param));
	}

	@PostMapping("/header")
	public ResultBean<FileHeader> getFileHeader(@RequestBody JSONObject request) throws Exception {
		long resultId = request.getLongValue("resultId");
		return new ResultBean<>(resultService.getFileHeader(resultId));
	}

	@PostMapping("/datas")
	public ResultBean<List<VariableData>> getVariableDatas(Long resultId, List<VariableGroup> selected) throws Exception {
		return new ResultBean<>(resultService.getVariableDatas(resultId, selected));
	}
}
