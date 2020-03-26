/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.runtime.controller;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hirain.phm.synapsis.protocol.ParseResult;
import com.hirain.phm.synapsis.protocol.ProtocolService;
import com.hirain.phm.synapsis.response.ResultBean;
import com.hirain.phm.synapsis.runtime.param.ActivateResponse;
import com.hirain.phm.synapsis.runtime.param.ProtocolResponse;
import com.hirain.phm.synapsis.runtime.param.SettingResponse;
import com.hirain.phm.synapsis.runtime.param.UpdateResponse;
import com.hirain.phm.synapsis.runtime.service.RuntimeService;
import com.hirain.phm.synapsis.setting.Setting;
import com.hirain.phm.synapsis.setting.Subsystem;
import com.hirain.phm.synapsis.setting.Variable;
import com.hirain.phm.synapsis.setting.db.SettingDbService;
import com.hirain.phm.synapsis.setting.support.param.SettingVO;
import com.hirain.phm.synapsis.setting.support.variable.VariableConvertManager;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 10, 2019 9:44:08 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 10, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@RestController
@RequestMapping("/setting")
public class SettingController {

	@Autowired
	private SettingDbService settingService;

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private ProtocolService parseService;

	@Autowired
	private VariableConvertManager variableConvert;

	@Value("${setting.file.temporary}")
	private String temporaryDirectory;// 存放资源文件的临时目录

	@GetMapping("/all")
	public ResultBean<List<Setting>> listAll() {
		return new ResultBean<>(settingService.selectAll());
	}

	/**
	 * 根据ID查询配置详情
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/select")
	public ResultBean<SettingResponse> select(Integer id) {
		SettingResponse response = runtimeService.selectWithDetail(id);
		return new ResultBean<>(response);
	}

	/**
	 * 上传数据流文件
	 * 
	 * @param type
	 * @param file
	 * @return
	 */
	@PostMapping("/upload/protocol")
	public ResultBean<ProtocolResponse> uploadProtocol(String type, MultipartFile file) throws Exception {
		ProtocolResponse response = new ProtocolResponse();
		try {
			String fileName = runtimeService.upload(file);// 上传文件到临时目录
			response.setPath(fileName);
			String filePath = temporaryDirectory + File.separator + fileName;
			// Excel转对象
			ParseResult parseResult = parseService.parse(type, filePath);
			List<? extends Variable> variables = variableConvert.convert(type, parseResult.getData());
			response.setVariables(variables);
			response.setErrors(parseResult.getErrors());
		} catch (Exception e) {
			throw e;
		}
		return new ResultBean<>(response);
	}

	/**
	 * 上传算法文件
	 * 
	 * @param file
	 * @return
	 */
	@PostMapping("/upload/script")
	public ResultBean<String> uploadScript(MultipartFile file) throws Exception {
		String fileName = runtimeService.upload(file);// 上传文件到临时目录
		return new ResultBean<>(fileName);
	}

	/**
	 * 更新配置
	 * 
	 * @param frontSetting
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/update")
	public ResultBean<UpdateResponse> saveSetting(@RequestBody SettingVO frontSetting) throws Exception {
		return new ResultBean<>(runtimeService.saveSetting(frontSetting));
	}

	/*
	 * 激活配置
	 */
	@PostMapping("/activate")
	public ResultBean<ActivateResponse> activateSetting(int settingId) throws Exception {
		return new ResultBean<>(runtimeService.validateAndActivate(settingId));
	}

	@PostMapping("/delete")
	public ResultBean<String> delete(int settingId) throws Exception {
		return new ResultBean<>(runtimeService.deleteSetting(settingId));
	}

	/**
	 * 查询所有子系统
	 */
	@GetMapping("/selectAllSubsystems")
	public ResultBean<List<Subsystem>> selectAllSubsystems() {
		return new ResultBean<>(runtimeService.selectAllSubsystems());
	}

	/**
	 * 导出配置文件
	 * 
	 * @param response
	 * @param path
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/export")
	public ResultBean<String> export(HttpServletResponse response, Integer settingId) throws Exception {
		runtimeService.export(response, settingId);
		return new ResultBean<>("导出成功");
	}

	/**
	 * 导入配置文件
	 * 
	 * @param response
	 * @param path
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "/import")
	public ResultBean<SettingResponse> importFile(MultipartFile file) throws Exception {
		SettingResponse response = runtimeService.importFile(file);
		return new ResultBean<>(response);
	}

}
