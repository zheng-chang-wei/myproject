/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.data.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.hirain.phm.synapsis.data.param.FileTreeNode;
import com.hirain.phm.synapsis.data.param.NodeLevel;
import com.hirain.phm.synapsis.data.service.DataFileLoader;
import com.hirain.phm.synapsis.data.service.DataFileService;
import com.hirain.phm.synapsis.exception.SynapsisException;
import com.hirain.phm.synapsis.parse.domain.FileHeader;
import com.hirain.phm.synapsis.parse.domain.VariableData;
import com.hirain.phm.synapsis.response.ResultBean;
import com.hirain.phm.synapsis.setting.ECNVariable;
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
 * 				Feb 21, 2020 zhaobin.su@hirain.com getmapping->postmapping
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
	public ResultBean<List<VariableData>> getDatas(@RequestBody JSONObject request) throws SynapsisException {
		JSONArray arr = request.getJSONArray("FileTreeNodes");
		List<FileTreeNode> nodes = JSONObject.parseArray(arr.toJSONString(), FileTreeNode.class);

		Integer offset = request.getIntValue("offset");

		JSONArray arr2 = request.getJSONArray("selectedVariables");
		List<VariableGroup> selectedVariables = JSONObject.parseArray(arr2.toJSONString(), VariableGroup.class);
		for (int i = 0; i < arr2.size(); i++) {
			if (selectedVariables.get(i).getType().equals("ECN")) {
				List<ECNVariable> variables = JSON.parseObject(arr2.getJSONObject(i).getJSONArray("variables").toJSONString(),
						new TypeReference<List<ECNVariable>>() {
						});
				selectedVariables.get(i).setVariables(variables);
			}
		}
		return new ResultBean<>(loader.getVariableDatas(nodes, offset, selectedVariables));
	}

	@PostMapping("/delete")
	public ResultBean<String> delete(@RequestBody JSONObject request) {
		boolean result = false;
		JSONArray arr = request.getJSONArray("filePath");
		List<FileTreeNode> nodes = JSONObject.parseArray(arr.toJSONString(), FileTreeNode.class);
		for (FileTreeNode node : nodes) {
			result = service.deleteFile(node.getPath());
			NodeLevel level = node.getLevel();
			if (level.equals(NodeLevel.Month) || level.equals(NodeLevel.Date)) {
				break;
			}
		}
		if (result) {
			return new ResultBean<>("删除成功！");
		}
		return new ResultBean<>("删除失败，请联系网站管理员！");
	}

	/**
	 * 下载原始数据文件
	 */
	@GetMapping(value = "/download")
	public ResultBean<String> download(HttpServletResponse response, String[] filePath) {
		try {
			service.downloadFile(response, filePath);
			return new ResultBean<>("下载成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultBean<>("下载失败，请联系网站管理员！");
		}
	}

}
