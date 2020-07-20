/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting.support.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirain.phm.synapsis.board.domain.Board;
import com.hirain.phm.synapsis.constant.BoardType;
import com.hirain.phm.synapsis.setting.AlgorithmSetting;
import com.hirain.phm.synapsis.setting.BoardSetting;
import com.hirain.phm.synapsis.setting.ECNVariable;
import com.hirain.phm.synapsis.setting.MVBVariable;
import com.hirain.phm.synapsis.setting.Setting;
import com.hirain.phm.synapsis.setting.StoreSetting;
import com.hirain.phm.synapsis.setting.TimeSetting;
import com.hirain.phm.synapsis.setting.Variable;
import com.hirain.phm.synapsis.setting.VariableGroup;
import com.hirain.phm.synapsis.setting.property.PHMBoardProperty;
import com.hirain.phm.synapsis.setting.support.BoardPropertyHandler;
import com.hirain.phm.synapsis.setting.support.ProtocolService;
import com.hirain.phm.synapsis.setting.support.SettingValidator;
import com.hirain.phm.synapsis.setting.support.domain.ValidateResult;
import com.hirain.phm.synapsis.setting.support.utils.StringUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 10, 2019 4:16:25 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 10, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Service
@Slf4j
public class SettingValidatorImpl implements SettingValidator {

	@Autowired
	private ProtocolService protocolService;

	@Autowired
	private BoardPropertyHandler propertyHandler;

	private Map<Integer, List<String>> mvbSlotNameMap = new HashMap<>();

	private Map<Integer, List<String>> ecnSlotNameMap = new HashMap<>();

	/**
	 * @see com.hirain.phm.synapsis.setting.support.SettingValidator#validateWithBoard(com.hirain.phm.synapsis.setting.Setting, java.util.List)
	 */
	@Override
	public ValidateResult validateWithBoard(Setting current, List<Board> boards) {
		// 校验板卡和配置的一致性
		ValidateResult result = new ValidateResult();
		List<String> errors = new ArrayList<>();
		List<String> warnings = new ArrayList<>();
		if (current == null) {
			errors.add("没有当前配置");
		}
		validateSlotBoardType(current, boards, errors, warnings);
		if (errors.size() > 0) {
			result.setError(true);
			result.setOutline(current == null ? "没有配置" : "配置槽位-板卡与实际机箱不一致");
			result.setErrors(errors);
		}
		if (warnings.size() > 0) {
			result.setWarnings(warnings);
		}
		return result;
	}

	/**
	 * @see com.hirain.phm.synapsis.setting.support.SettingValidator#validate(com.hirain.phm.synapsis.setting.Setting)
	 */
	@Override
	public ValidateResult validate(Setting setting) {
		// 校验配置的正确性
		ValidateResult result = new ValidateResult();
		List<String> errors = new ArrayList<>();
		initMVB(setting);
		initECN(setting);
		validTimeSetting(setting, errors);
		validStoreSetting(setting, errors);
		validAlgoVariable(setting, errors);
		validPHMVideoIP(setting, errors);
		return result;
	}

	/**
	 * @see com.hirain.phm.synapsis.setting.support.SettingValidator#preBoardValidate(java.util.List)
	 */
	@Override
	public ValidateResult preBoardValidate(List<BoardSetting> boards) {
		ValidateResult result = new ValidateResult();
		List<String> errors = new ArrayList<>();
		for (BoardSetting boardSetting : boards) {
			List<String> boardErrors = propertyHandler.validate(boardSetting);
			if (!boardErrors.isEmpty()) {
				errors.addAll(boardErrors);
			}
		}
		if (errors.size() > 0) {
			result.setError(true);
			result.setOutline("板卡配置信息异常");
			result.setErrors(errors);
		}
		return result;
	}

	/**
	 * @see com.hirain.phm.synapsis.setting.support.SettingValidator#preAlgoValidate(java.util.List)
	 */
	@Override
	public ValidateResult preAlgoValidate(List<AlgorithmSetting> algorithms) {
		ValidateResult result = new ValidateResult();
		List<String> errors = new ArrayList<>();
		List<Integer> algoCodes = new ArrayList<>();
		List<String> algoNames = new ArrayList<>();
		List<String> algoFileNames = new ArrayList<>();
		for (AlgorithmSetting algorithmSetting : algorithms) {
			String name = algorithmSetting.getName();
			if (StringUtils.isEmpty(name)) {
				errors.add("算法名为空");
			} else {
				algoNames.add(name);
			}
			Integer code = algorithmSetting.getCode();
			if (code == null) {
				errors.add("算法Code为空");
			} else {
				algoCodes.add(code);
			}
			String filename = algorithmSetting.getFilename();
			if (StringUtils.isEmpty(filename)) {
				errors.add("算法文件名为空");
			} else {
				algoFileNames.add(filename);
			}
		}
		HashSet<String> nameSet = new HashSet<>(algoNames);
		if (nameSet.size() != algoNames.size()) {
			errors.add("算法名存在冲突");
		}
		HashSet<Integer> codeSet = new HashSet<>(algoCodes);
		if (codeSet.size() != algoCodes.size()) {
			errors.add("算法Code存在冲突");
		}
		HashSet<String> filenameSet = new HashSet<>(algoFileNames);
		if (filenameSet.size() != algoFileNames.size()) {
			errors.add("算法文件名存在冲突");
		}
		if (errors.size() > 0) {
			result.setError(true);
			result.setOutline("算法配置信息异常");
			result.setErrors(errors);
		}
		return result;
	}

	/**
	 * @see com.hirain.phm.synapsis.setting.support.SettingValidator#preStoreValidate(com.hirain.phm.synapsis.setting.StoreSetting)
	 */
	@Override
	public ValidateResult preStoreValidate(StoreSetting storeSetting) {
		ValidateResult result = new ValidateResult();
		List<String> errors = new ArrayList<>();
		Integer rawStrategy = storeSetting.getRawStrategy();
		if (rawStrategy == null) {
			errors.add("原始数据存储策略未设置");
		}
		Integer rawSpace = storeSetting.getRawSpace();
		if (rawSpace == null) {
			errors.add("原始数据存储空间未设置");
		}
		Integer resultStrategy = storeSetting.getResultStrategy();
		if (resultStrategy == null) {
			errors.add("分析数据存储策略未设置");
		}
		Integer resultSpace = storeSetting.getResultSpace();
		if (resultSpace == null) {
			errors.add("分析数据存储策略未设置");
		}
		if (errors.size() > 0) {
			result.setError(true);
			result.setOutline("存储配置信息异常");
			result.setErrors(errors);
		}
		return result;
	}

	/**
	 * @see com.hirain.phm.synapsis.setting.support.SettingValidator#preTimeValidate(com.hirain.phm.synapsis.setting.TimeSetting)
	 */
	@Override
	public ValidateResult preTimeValidate(TimeSetting timeSetting) {
		ValidateResult result = new ValidateResult();
		List<String> errors = new ArrayList<>();
		String type = timeSetting.getType();
		if (StringUtils.isEmpty(type)) {
			errors.add("时钟配置类型未设置");
		}
		if (errors.size() > 0) {
			result.setError(true);
			result.setOutline("时钟配置信息异常");
			result.setErrors(errors);
		}
		return result;
	}

	/**
	 * 校验Board中槽位-类型和Setting中槽位-类型是否一致
	 */
	private void validateSlotBoardType(Setting setting, List<Board> boards, List<String> errors, List<String> warnings) {
		Map<Integer, BoardType> boardMap = new HashMap<>();
		List<Integer> settingSlotIds = new ArrayList<>();
		for (Board board : boards) {
			boardMap.put(board.getSlotID(), board.getBoardType());
		}
		List<BoardSetting> boardSettings = setting.getBoardSettings();
		for (BoardSetting boardSetting : boardSettings) {
			Integer slotId = boardSetting.getSlotId();
			settingSlotIds.add(slotId);
			String type = boardSetting.getType().trim().toUpperCase();
			if (boardMap.get(slotId) == null) {// 配置文件中某个槽位号在实际设备中不存在板卡
				errors.add("槽位号 " + slotId + " ，缺失板卡");
			} else if (!boardMap.get(boardSetting.getSlotId()).name().equals(type)) {// 配置文件中某个槽位号对应的板卡类型与实际设备中该槽位号对应的板卡类型不一致
				errors.add("槽位号 " + slotId + " 中板卡类型与实际板卡类型不一致");
			}
		}
		for (Board board : boards) {
			int slotID = board.getSlotID();
			if (!settingSlotIds.contains(slotID)) {// 实际存在板卡的槽位号在配置文件中未配置板卡
				warnings.add("槽位号 " + slotID + " 的板卡在配置文件中不存在");
			}
		}
	}

	/**
	 * 校验PHM板卡的视频源IP地址是否一致
	 */
	private void validPHMVideoIP(Setting setting, List<String> errors) {
		List<BoardSetting> boardSettings = setting.getBoardSettings();
		for (BoardSetting boardSetting : boardSettings) {
			String boardType = boardSetting.getType().trim().toUpperCase();
			switch (boardType) {
			case "PHM_AGX":
			case "PHM_TX2":
				PHMBoardProperty phm_property = (PHMBoardProperty) boardSetting.getProperty();
				List<String> videoIPs = Arrays.asList(phm_property.getTargetIp1(), phm_property.getTargetIp2());
				List<AlgorithmSetting> algorithmSettings = setting.getAlgorithmSettings();
				for (AlgorithmSetting algoSetting : algorithmSettings) {
					if (!videoIPs.contains(algoSetting.getVideoIp())) {
						errors.add("槽位 " + boardSetting.getSlotId() + " 对应板卡视频源IP地址有误");
					}
				}
				break;
			}
		}
	}

	/**
	 * 初始化MVB板卡对应数据流文件解析出来的变量Map
	 */
	private void initMVB(Setting setting) {
		mvbSlotNameMap.clear();
		try {
			for (BoardSetting boardSetting : setting.getBoardSettings()) {
				String boardType = boardSetting.getType().trim().toUpperCase();
				switch (boardType) {
				case "MVB":
					List<String> variableNames = new ArrayList<>();
					for (VariableGroup variableGroup : protocolService.getVariables(setting.getId(), BoardType.MVB.getType())) {
						List<? extends Variable> mvbVariables = variableGroup.getVariables();
						for (Variable variable : mvbVariables) {
							variableNames.add(variable.getName());
						}
					}
					mvbSlotNameMap.put(boardSetting.getSlotId(), variableNames);
					break;
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * 初始化ECN板卡对应数据流文件解析出来的变量Map
	 */
	private void initECN(Setting setting) {
		ecnSlotNameMap.clear();
		try {
			for (BoardSetting boardSetting : setting.getBoardSettings()) {
				String boardType = boardSetting.getType().trim().toUpperCase();
				switch (boardType) {
				case "ECN":
					List<String> variableNames = new ArrayList<>();
					for (VariableGroup variableGroup : protocolService.getVariables(setting.getId(), BoardType.ECN.getType())) {
						List<? extends Variable> ecnVariables = variableGroup.getVariables();
						for (Variable variable : ecnVariables) {
							variableNames.add(variable.getName());
						}
					}
					ecnSlotNameMap.put(boardSetting.getSlotId(), variableNames);
					break;
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * 校验时钟配置的变量是否为当前数据流文件中的变量所包含
	 */
	private void validTimeSetting(Setting setting, List<String> errors) {
		TimeSetting timeSetting = setting.getTimeSetting();
		if (StringUtils.isEquals(timeSetting.getType(), "bus")) {// 时钟配置源来自总线，才有变量组
			VariableGroup variableGroup = timeSetting.getTimeVariables();
			validVariableConsistency(variableGroup, errors);
		}
	}

	/**
	 * 校验存储配置的变量是否为当前数据流文件中的变量所包含
	 */
	private void validStoreSetting(Setting setting, List<String> errors) {
		StoreSetting storeSetting = setting.getStoreSetting();
		List<VariableGroup> variableGroups = storeSetting.getStoreVariables();
		for (VariableGroup variableGroup : variableGroups) {
			validVariableConsistency(variableGroup, errors);
		}
	}

	/**
	 * 校验算法配置中的变量是否为当前数据流文件中的变量所包含
	 */
	private void validAlgoVariable(Setting setting, List<String> errors) {
		for (AlgorithmSetting algorithmSetting : setting.getAlgorithmSettings()) {
			for (VariableGroup variableGroup : algorithmSetting.getVariableGroups()) {
				validVariableConsistency(variableGroup, errors);
			}
		}
	}

	/**
	 * 校验变量的一致性
	 */
	@SuppressWarnings("unchecked")
	private void validVariableConsistency(VariableGroup variableGroup, List<String> errors) {
		List<? extends Variable> variables = variableGroup.getVariables();
		List<String> variableNames;
		switch (variableGroup.getType()) {
		case "MVB":
			variableNames = mvbSlotNameMap.get(variableGroup.getSlotId());
			for (MVBVariable variable : (List<MVBVariable>) variables) {
				if (!variableNames.contains(variable.getName())) {
					errors.add("MVB数据流文件中不包含变量：" + variable.getName());
				}
			}
			break;
		case "ECN":
			variableNames = ecnSlotNameMap.get(variableGroup.getSlotId());
			for (ECNVariable variable : (List<ECNVariable>) variables) {
				if (!variableNames.contains(variable.getName())) {
					errors.add("ECN数据流文件中不包含变量：" + variable.getName());
				}
			}
			break;
		}
	}

}
