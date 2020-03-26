/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.data.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hirain.phm.synapsis.data.param.FileTreeNode;
import com.hirain.phm.synapsis.data.service.DataFileLoader;
import com.hirain.phm.synapsis.data.service.DataFileService;
import com.hirain.phm.synapsis.exception.SynapsisException;
import com.hirain.phm.synapsis.parse.domain.FileHeader;
import com.hirain.phm.synapsis.parse.domain.VariableData;
import com.hirain.phm.synapsis.response.ResultBean;
import com.hirain.phm.synapsis.setting.VariableGroup;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 18, 2019 3:39:21 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 18, 2019 jianwen.xin@hirain.com 1.0 create file
 ** @Modification
 *               Feb 21, 2020 zhaobin.su@hirain.com getmapping->postmapping
 */
@RestController
@RequestMapping("/data")
public class DataController {

	@Autowired
	private DataFileService service;

	@Autowired
	private DataFileLoader loader;

	@PostMapping("/root")
	public ResultBean<List<FileTreeNode>> listRoot() throws SynapsisException {
		return new ResultBean<>(service.listRoot());
	}

	@PostMapping("/list")
	public ResultBean<List<FileTreeNode>> listNodes(@RequestBody JSONObject request) throws SynapsisException {
		FileTreeNode node = JSONObject.toJavaObject(request.getJSONObject("FileTreeNode"), FileTreeNode.class);
		return new ResultBean<>(service.listNodes(node));
	}

	@PostMapping("/query")
	public ResultBean<List<FileTreeNode>> list(@RequestBody JSONObject request) throws SynapsisException {
		FileTreeNode node = JSONObject.toJavaObject(request.getJSONObject("FileTreeNode"), FileTreeNode.class);
		Date startDate = request.getDate("startDate");
		Date endDate = request.getDate("endDate");
		if (node == null) {
			return new ResultBean<>(service.listRoot(startDate, endDate));
		} else {
			return new ResultBean<>(service.listNodes(node, startDate, endDate));
		}
	}

	@PostMapping("/header")
	public ResultBean<FileHeader> getFileHeader(@RequestBody JSONObject request) throws SynapsisException {
		JSONArray arr = request.getJSONArray("FileTreeNodes");
		List<FileTreeNode> nodes = JSONObject.parseArray(arr.toJSONString(), FileTreeNode.class);
		return new ResultBean<>(loader.getVariables(nodes));
	}

	@PostMapping("/datas")
	public ResultBean<List<VariableData>> getDatas(List<FileTreeNode> nodes, int offset, List<VariableGroup> selectedVariables)
			throws SynapsisException {
		return new ResultBean<>(loader.getVariableDatas(nodes, offset, selectedVariables));
	}
}
