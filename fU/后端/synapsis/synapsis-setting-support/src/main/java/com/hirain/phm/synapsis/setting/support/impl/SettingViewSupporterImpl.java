/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting.support.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirain.phm.synapsis.constant.BoardType;
import com.hirain.phm.synapsis.exception.SynapsisException;
import com.hirain.phm.synapsis.protocol.ParseResult;
import com.hirain.phm.synapsis.protocol.ProtocolService;
import com.hirain.phm.synapsis.setting.AlgorithmSetting;
import com.hirain.phm.synapsis.setting.BoardSetting;
import com.hirain.phm.synapsis.setting.Setting;
import com.hirain.phm.synapsis.setting.Variable;
import com.hirain.phm.synapsis.setting.VariableGroup;
import com.hirain.phm.synapsis.setting.support.SettingViewSupporter;
import com.hirain.phm.synapsis.setting.support.param.ADGroupVO;
import com.hirain.phm.synapsis.setting.support.param.AlgorithmSettingVO;
import com.hirain.phm.synapsis.setting.support.param.BoardSettingVO;
import com.hirain.phm.synapsis.setting.support.param.ECNGroupVO;
import com.hirain.phm.synapsis.setting.support.param.MVBGroupVO;
import com.hirain.phm.synapsis.setting.support.param.SettingVO;
import com.hirain.phm.synapsis.setting.support.param.StoreVariablesVO;
import com.hirain.phm.synapsis.setting.support.param.TimeVariablesVO;
import com.hirain.phm.synapsis.setting.support.param.VariableGroupVO;
import com.hirain.phm.synapsis.setting.support.variable.VariableConvertManager;
import com.hirain.phm.synapsis.setting.support.variable.VariableGroupConverter;

import lombok.extern.slf4j.Slf4j;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 12, 2019 10:48:36 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 12, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Service
@Slf4j
public class SettingViewSupporterImpl implements SettingViewSupporter {

	@Autowired
	private VariableGroupConverter converter;

	@Autowired
	private SettingProperties properties;

	@Autowired
	private VariableConvertManager manger;

	@Autowired
	private ProtocolService parseService;

	/**
	 * @see com.hirain.phm.synapsis.setting.support.SettingViewSupporter#settingToVO(com.hirain.phm.synapsis.setting.Setting)
	 */
	@Override
	public SettingVO settingToVO(Setting setting) {
		SettingVO frontSetting = new SettingVO();
		frontSetting.setName(setting.getName());
		frontSetting.setLine(setting.getLine());
		frontSetting.setTrain(setting.getTrain());
		frontSetting.setRawSpace(setting.getRawSpace());
		frontSetting.setRawStrategy(setting.getRawStrategy());
		frontSetting.setResultSpace(setting.getResultSpace());
		frontSetting.setResultStrategy(setting.getResultStrategy());
		frontSetting.setTimeOn(setting.getTimeOn());
		frontSetting.setTimeVariables(getTimeVariables(setting.getTimeVariables()));
		frontSetting.setStoreVariables(getStoreVariables(setting.getStoreVariables()));
		frontSetting.setBoardSettings(getBoardSettings(setting.getBoardSettings()));
		frontSetting.setAlgorithmSettings(getAlgorithmSettings(setting.getAlgorithmSettings()));
		return frontSetting;
	}

	/**
	 * @see com.hirain.phm.synapsis.setting.support.SettingViewSupporter#getVariableGroupsFromProtocol(com.hirain.phm.synapsis.setting.Setting)
	 */
	@Override
	public List<VariableGroup> getVariableGroupsFromProtocol(Setting setting) {
		List<VariableGroup> variableGroup = new ArrayList<>();
		String folerPath = properties.getResourceDirectory() + File.separator + setting.getName();
		for (BoardSetting boardSetting : setting.getBoardSettings()) {
			if (boardSetting.getFilename() != null) {
				BoardType boardType = BoardType.valueOf(boardSetting.getType());
				VariableGroup group = new VariableGroup();
				group.setSlotId(boardSetting.getSlotId());
				group.setType(boardType.getType());
				try {
					ParseResult result = parseService.parse(boardType.getType(), folerPath + File.separator + boardSetting.getFilename());
					if (result.getCode() == ParseResult.SUCCESS) {
						List<? extends Variable> variables = manger.convert(boardType.getType(), result.getData());
						if (variables != null) {
							group.setVariables(variables);
							variableGroup.add(group);
						}
					}
				} catch (SynapsisException e) {
					log.error(e.getMessage(), e);
				}
			}
		}
		return variableGroup;
	}

	/**
	 * @param timeGroup
	 * @return
	 */
	private TimeVariablesVO getTimeVariables(VariableGroup timeGroup) {
		if (timeGroup != null) {
			TimeVariablesVO timeVariables = new TimeVariablesVO();
			VariableGroupVO frontGroup = converter.convertToVO(timeGroup);
			if (frontGroup instanceof MVBGroupVO) {
				timeVariables.setMvbGroup((MVBGroupVO) frontGroup);
			} else if (frontGroup instanceof ECNGroupVO) {
				timeVariables.setEcnGroup((ECNGroupVO) frontGroup);
			}
			return timeVariables;
		}
		return null;
	}

	/**
	 * @param storeVariables
	 * @return
	 */
	private StoreVariablesVO getStoreVariables(List<VariableGroup> groups) {
		StoreVariablesVO storeVariables = new StoreVariablesVO();
		ArrayList<MVBGroupVO> mvbGroups = new ArrayList<>();
		ArrayList<ADGroupVO> adGroups = new ArrayList<>();
		ArrayList<ECNGroupVO> ecnGroups = new ArrayList<>();
		convert(groups, mvbGroups, adGroups, ecnGroups);
		storeVariables.setAdGroups(adGroups);
		storeVariables.setMvbGroups(mvbGroups);
		storeVariables.setEcnGroups(ecnGroups);
		return storeVariables;
	}

	/**
	 * @param boardSettings
	 * @return
	 */
	private List<BoardSettingVO> getBoardSettings(List<BoardSetting> boardSettings) {
		List<BoardSettingVO> settings = new ArrayList<>();
		for (BoardSetting board : boardSettings) {
			BoardSettingVO boardSetting = new BoardSettingVO();
			boardSetting.setSlotId(board.getSlotId());
			boardSetting.setEnable(board.getEnable());
			boardSetting.setFilename(board.getFilename());
			boardSetting.setFileOriginalName(board.getFileOriginalName());
			boardSetting.setIp(board.getIp());
			boardSetting.setType(board.getType());
			settings.add(boardSetting);
			BoardType boardType = BoardType.valueOf(board.getType());
			if (boardType.getType().equals("AD")) {
				List<VariableGroup> groups = board.getVariableGroups();
				ADGroupVO adGroup = new ADGroupVO();
				adGroup.setSlotId(boardSetting.getSlotId());
				adGroup.setVariables(new ArrayList<>());
				for (VariableGroup group : groups) {
					VariableGroupVO frontGroup = converter.convertToVO(group);
					adGroup.getVariables().addAll(((ADGroupVO) frontGroup).getVariables());
				}
				boardSetting.setVariables(adGroup.getVariables());
			}
		}
		return settings;
	}

	/**
	 * @param algorithmSettings
	 * @return
	 */
	private List<AlgorithmSettingVO> getAlgorithmSettings(List<AlgorithmSetting> algorithmSettings) {
		ArrayList<AlgorithmSettingVO> settings = new ArrayList<>();
		for (AlgorithmSetting algorithmSetting : algorithmSettings) {
			AlgorithmSettingVO frontSetting = new AlgorithmSettingVO();
			frontSetting.setCode(algorithmSetting.getCode());
			frontSetting.setEnable(algorithmSetting.getEnable());
			frontSetting.setFilename(algorithmSetting.getFilename());
			frontSetting.setFileOriginalName(algorithmSetting.getFileOriginalName());
			frontSetting.setName(algorithmSetting.getName());
			frontSetting.setSlotId(algorithmSetting.getSlotId());
			frontSetting.setSubsystemId(algorithmSetting.getSubsystemId());
			frontSetting.setType(algorithmSetting.getType());
			settings.add(frontSetting);
			ArrayList<MVBGroupVO> mvbGroups = new ArrayList<>();
			ArrayList<ADGroupVO> adGroups = new ArrayList<>();
			ArrayList<ECNGroupVO> ecnGroups = new ArrayList<>();
			convert(algorithmSetting.getVariableGroups(), mvbGroups, adGroups, ecnGroups);
			frontSetting.setAdGroups(adGroups);
			frontSetting.setMvbGroups(mvbGroups);
			frontSetting.setEcnGroups(ecnGroups);
		}
		return settings;
	}

	/**
	 * @param groups
	 * @param mvbGroups
	 * @param adGroups
	 * @param ecnGroups
	 */
	private void convert(List<VariableGroup> groups, ArrayList<MVBGroupVO> mvbGroups, ArrayList<ADGroupVO> adGroups,
			ArrayList<ECNGroupVO> ecnGroups) {
		Map<Integer, ADGroupVO> map = new HashMap<>();
		for (VariableGroup group : groups) {
			VariableGroupVO frontGroup = converter.convertToVO(group);
			if (frontGroup instanceof MVBGroupVO) {
				mvbGroups.add((MVBGroupVO) frontGroup);
			} else if (frontGroup instanceof ECNGroupVO) {
				ecnGroups.add((ECNGroupVO) frontGroup);
			} else if (frontGroup instanceof ADGroupVO) {
				ADGroupVO adGroup = map.get(frontGroup.getSlotId());
				if (adGroup == null) {
					adGroup = new ADGroupVO();
					adGroup.setSlotId(frontGroup.getSlotId());
					adGroup.setVariables(new ArrayList<>());
					map.put(adGroup.getSlotId(), adGroup);
				}
				adGroup.getVariables().addAll(((ADGroupVO) frontGroup).getVariables());
			}
		}
		adGroups.addAll(map.values());
	}

}
