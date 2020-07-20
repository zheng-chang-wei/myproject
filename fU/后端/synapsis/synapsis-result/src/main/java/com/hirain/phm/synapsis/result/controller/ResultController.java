/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.result.controller;

import java.io.File;
import java.text.SimpleDateFormat;
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
import com.hirain.phm.synapsis.page.PageService;
import com.hirain.phm.synapsis.page.QueryRequest;
import com.hirain.phm.synapsis.parse.domain.FileHeader;
import com.hirain.phm.synapsis.parse.domain.VariableData;
import com.hirain.phm.synapsis.response.PageResultBean;
import com.hirain.phm.synapsis.response.ResultBean;
import com.hirain.phm.synapsis.result.ResultConfig;
import com.hirain.phm.synapsis.result.domain.AlgorithmResult;
import com.hirain.phm.synapsis.result.param.ResultQueryParam;
import com.hirain.phm.synapsis.result.service.ResultService;
import com.hirain.phm.synapsis.setting.ECNVariable;
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
	private ResultConfig config;

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
	public ResultBean<List<VariableData>> getVariableDatas(@RequestBody JSONObject request) throws Exception {

		long resultId = request.getLongValue("resultId");

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
		return new ResultBean<>(resultService.getVariableDatas(resultId, selectedVariables));
	}

	@PostMapping("/video")
	public ResultBean<String> getVideoPath(@RequestBody JSONObject request) throws Exception {
		String code = request.getString("code");
		Date timestep = request.getTimestamp("timestamp");
		SimpleDateFormat ymrhms = new SimpleDateFormat("yyyyMMddHHmmss");
		SimpleDateFormat ymd_hms = new SimpleDateFormat("yyyyMMdd_HHmmss");

		String videoUrl = config.getVideoUrl() + code + "_" + ymrhms.format(timestep) + "/" + ymd_hms.format(timestep) + ".mp4";
		return new ResultBean<>(videoUrl);
	}

	@PostMapping("/delete")
	public ResultBean<String> delete(@RequestBody JSONObject request) {
		JSONArray arr = request.getJSONArray("deleteNodes");
		SimpleDateFormat ymrhms = new SimpleDateFormat("yyyyMMddHHmmss");
		List<AlgorithmResult> results = JSONObject.parseArray(arr.toJSONString(), AlgorithmResult.class);
		String filePath;
		for (AlgorithmResult algorithmResult : results) {
			resultService.deleteById(algorithmResult.getId());
			filePath = config.getRoot() + File.separator + algorithmResult.getCode() + "_" + ymrhms.format(algorithmResult.getTimestamp());
			resultService.deleteFile(filePath);
		}
		return new ResultBean<>("删除成功！");
	}

	/**
	 * 下载分析数据文件
	 */
	@GetMapping(value = "/download")
	public ResultBean<String> download(HttpServletResponse response, String[] filePath) {
		try {
			resultService.downloadFile(response, filePath);
			return new ResultBean<>("下载成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultBean<>("下载失败，请联系网站管理员！");
		}
	}
}
