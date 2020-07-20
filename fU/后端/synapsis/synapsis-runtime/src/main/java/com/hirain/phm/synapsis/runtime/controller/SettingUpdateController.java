/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.runtime.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.hirain.phm.synapsis.response.ResultBean;
import com.hirain.phm.synapsis.setting.AlgorithmSetting;
import com.hirain.phm.synapsis.setting.BoardSetting;
import com.hirain.phm.synapsis.setting.Setting;
import com.hirain.phm.synapsis.setting.StoreSetting;
import com.hirain.phm.synapsis.setting.TimeSetting;
import com.hirain.phm.synapsis.setting.db.SettingDbService;
import com.hirain.phm.synapsis.setting.db.SettingUpdateService;
import com.hirain.phm.synapsis.setting.support.SettingUpdateSupporter;
import com.hirain.phm.synapsis.setting.support.SettingValidator;
import com.hirain.phm.synapsis.setting.support.SettingViewSupporter;
import com.hirain.phm.synapsis.setting.support.domain.ValidateResult;
import com.hirain.phm.synapsis.setting.support.param.AlgorithmSettingsVO;
import com.hirain.phm.synapsis.setting.support.param.BoardSettingsVO;
import com.hirain.phm.synapsis.setting.support.param.StoreSettingVO;
import com.hirain.phm.synapsis.setting.support.param.TimeSettingVO;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Apr 21, 2020 10:09:51 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Apr 21, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@RestController
@RequestMapping("/setting/update")
public class SettingUpdateController {

	@Autowired
	private SettingUpdateService service;

	@Autowired
	private SettingDbService dbService;

	@Autowired
	private SettingUpdateSupporter supporter;

	@Autowired
	private SettingViewSupporter viewSupporter;

	@Autowired
	private SettingValidator settingValidator;

	@PostMapping("/basic")
	public ResultBean<Integer> createOrUpdate(@RequestBody Setting setting) {
		Setting result = dbService.selectByName(setting.getName());
		if (result != null) {
			if (!result.getId().equals(setting.getId())) {
				return new ResultBean<>("配置名称重复，请修改", null);
			}
		}
		String oldname = null;
		if (setting.getId() != null) {
			Setting oldSetting = dbService.selectById(setting.getId());
			oldname = oldSetting.getName();
		}
		setLastModifyDate(setting);
		supporter.createOrRename(oldname, setting.getName());
		return new ResultBean<>(setting.getId());
	}

	@PostMapping("/board")
	public ResultBean<ValidateResult> updateBoards(@RequestBody BoardSettingsVO boardSettingVOAndSettingId) throws Exception {
		List<BoardSetting> boards = viewSupporter.parseBoardSettingVO(boardSettingVOAndSettingId.getBoardVOList());
		// 校验boards是否合法
		ValidateResult result = settingValidator.preBoardValidate(boards);
		if (result.isError()) {
			return new ResultBean<>(result);
		}
		service.updateBoardSetting(boardSettingVOAndSettingId.getSettingId(), boards);
		Setting setting = dbService.selectById(boardSettingVOAndSettingId.getSettingId());
		setLastModifyDate(setting);
		supporter.handleBoardResources(setting.getName(), boards);
		return new ResultBean<>();
	}

	@PostMapping("/algorithm")
	public ResultBean<ValidateResult> updateAlgorithms(@RequestBody AlgorithmSettingsVO algorithmSettingVOAndSettingId) throws IOException {
		List<AlgorithmSetting> algorithms = viewSupporter.parseAlgorithmSettingVO(algorithmSettingVOAndSettingId.getAlgorithmVOList());
		// 校验algorithms是否合法
		ValidateResult result = settingValidator.preAlgoValidate(algorithms);
		if (result.isError()) {
			return new ResultBean<>(result);
		}
		service.updateAlgorithmSetting(algorithmSettingVOAndSettingId.getSettingId(), algorithms);
		Setting setting = dbService.selectById(algorithmSettingVOAndSettingId.getSettingId());
		setLastModifyDate(setting);
		supporter.handleAlgorithmResources(setting.getName(), algorithms);
		return new ResultBean<>();
	}

	@PostMapping("/store")
	public ResultBean<ValidateResult> updateStoreSetting(@RequestBody StoreSettingVO settingVO) {
		StoreSetting storeSetting = viewSupporter.parseStoreSettingVO(settingVO);
		ValidateResult result = settingValidator.preStoreValidate(storeSetting);
		if (result.isError()) {
			return new ResultBean<>(result);
		}
		Setting setting = dbService.selectById(settingVO.getSettingId());
		setLastModifyDate(setting);
		service.updateStoreSetting(settingVO.getSettingId(), storeSetting);
		return new ResultBean<>();
	}

	@PostMapping("/time")
	public ResultBean<ValidateResult> updateTimeSetting(@RequestBody TimeSettingVO timeVO) {
		TimeSetting timeSetting = viewSupporter.parseTimeSettingVO(timeVO);
		ValidateResult result = settingValidator.preTimeValidate(timeSetting);
		if (result.isError()) {
			return new ResultBean<>(result);
		}
		Setting setting = dbService.selectById(timeVO.getSettingId());
		setLastModifyDate(setting);
		service.updateTimeSetting(timeVO.getSettingId(), timeSetting);
		return new ResultBean<>();
	}

	private void setLastModifyDate(Setting setting) {
		HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
		setting.setLastModify(new Date());
		setting.setUserName(request.getSession().getAttribute("userName").toString());
		service.createOrUpdate(setting);
	}
}
