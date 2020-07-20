/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting.support.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirain.phm.synapsis.setting.AlgorithmSetting;
import com.hirain.phm.synapsis.setting.BoardSetting;
import com.hirain.phm.synapsis.setting.Setting;
import com.hirain.phm.synapsis.setting.StoreSetting;
import com.hirain.phm.synapsis.setting.TimeSetting;
import com.hirain.phm.synapsis.setting.Variable.VariableType;
import com.hirain.phm.synapsis.setting.VariableGroup;
import com.hirain.phm.synapsis.setting.property.BoardProperty;
import com.hirain.phm.synapsis.setting.support.BoardPropertyHandler;
import com.hirain.phm.synapsis.setting.support.SettingViewSupporter;
import com.hirain.phm.synapsis.setting.support.param.ADGroupVO;
import com.hirain.phm.synapsis.setting.support.param.AlgorithmSettingVO;
import com.hirain.phm.synapsis.setting.support.param.BoardSettingVO;
import com.hirain.phm.synapsis.setting.support.param.ECNGroupVO;
import com.hirain.phm.synapsis.setting.support.param.MVBGroupVO;
import com.hirain.phm.synapsis.setting.support.param.SettingVO;
import com.hirain.phm.synapsis.setting.support.param.StoreSettingVO;
import com.hirain.phm.synapsis.setting.support.param.TimeSettingVO;
import com.hirain.phm.synapsis.setting.support.param.VariableGroupVO;
import com.hirain.phm.synapsis.setting.support.variable.VariableGroupConverter;

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
public class SettingViewSupporterImpl implements SettingViewSupporter {

	@Autowired
	private VariableGroupConverter converter;

	@Autowired
	private BoardPropertyHandler propertyHandler;

	/**
	 * @see com.hirain.phm.synapsis.setting.support.SettingViewSupporter#settingToVO(com.hirain.phm.synapsis.setting.Setting)
	 */
	@Override
	public SettingVO settingToVO(Setting setting) {
		SettingVO vo = new SettingVO();
		vo.setName(setting.getName());
		vo.setLine(setting.getLine());
		vo.setTrain(setting.getTrain());
		TimeSetting timeSetting = setting.getTimeSetting();
		if (timeSetting != null) {
			vo.setTimeSetting(getTimeVariables(timeSetting));
		}
		StoreSetting storeSetting = setting.getStoreSetting();
		if (storeSetting != null) {
			vo.setStoreSetting(getStoreVariables(storeSetting));
		}
		List<BoardSetting> boardSettings = setting.getBoardSettings();
		if (boardSettings != null) {
			vo.setBoardSettings(getBoardSettings(boardSettings));
		}
		List<AlgorithmSetting> algorithmSettings = setting.getAlgorithmSettings();
		if (algorithmSettings != null) {
			vo.setAlgorithmSettings(getAlgorithmSettings(algorithmSettings));
		}
		return vo;
	}

	/**
	 * @param timeSetting
	 * @return
	 */
	private TimeSettingVO getTimeVariables(TimeSetting timeSetting) {
		TimeSettingVO vo = new TimeSettingVO();
		vo.setType(timeSetting.getType());

		VariableGroup timeVariables = timeSetting.getTimeVariables();
		if (timeVariables != null) {
			VariableGroupVO frontGroup = converter.convertToVO(timeVariables);
			if (frontGroup instanceof MVBGroupVO) {
				vo.setMvbGroup((MVBGroupVO) frontGroup);
			} else if (frontGroup instanceof ECNGroupVO) {
				vo.setEcnGroup((ECNGroupVO) frontGroup);
			}
		}

		return vo;
	}

	/**
	 * @param storeSetting
	 * @param storeVariables
	 * @return
	 */
	private StoreSettingVO getStoreVariables(StoreSetting storeSetting) {
		StoreSettingVO vo = new StoreSettingVO();
		vo.setRawSpace(storeSetting.getRawSpace());
		vo.setRawStrategy(storeSetting.getRawStrategy());
		vo.setResultSpace(storeSetting.getResultSpace());
		vo.setResultStrategy(storeSetting.getResultStrategy());
		ArrayList<MVBGroupVO> mvbGroups = new ArrayList<>();
		ArrayList<ADGroupVO> adGroups = new ArrayList<>();
		ArrayList<ECNGroupVO> ecnGroups = new ArrayList<>();
		convert(storeSetting.getStoreVariables(), mvbGroups, adGroups, ecnGroups);
		vo.setMvbGroups(mvbGroups);
		vo.setEcnGroups(ecnGroups);
		return vo;
	}

	/**
	 * @param boardSettings
	 * @return
	 */
	private List<BoardSettingVO> getBoardSettings(List<BoardSetting> boardSettings) {
		List<BoardSettingVO> settings = new ArrayList<>();
		for (BoardSetting boardSetting : boardSettings) {
			BoardSettingVO vo = new BoardSettingVO();
			vo.setSlotId(boardSetting.getSlotId());
			vo.setEnable(boardSetting.getEnable());
			vo.setType(boardSetting.getType());
			propertyHandler.fillVOProperty(boardSetting, vo);
			settings.add(vo);
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
			frontSetting.setVideoIp(algorithmSetting.getVideoIp());
			frontSetting.setVideoUrl(algorithmSetting.getVideoUrl());
			settings.add(frontSetting);
			ArrayList<MVBGroupVO> mvbGroups = new ArrayList<>();
			ArrayList<ADGroupVO> adGroups = new ArrayList<>();
			ArrayList<ECNGroupVO> ecnGroups = new ArrayList<>();
			convert(algorithmSetting.getVariableGroups(), mvbGroups, adGroups, ecnGroups);
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
		for (VariableGroup group : groups) {
			VariableGroupVO frontGroup = converter.convertToVO(group);
			if (frontGroup instanceof MVBGroupVO) {
				mvbGroups.add((MVBGroupVO) frontGroup);
			} else if (frontGroup instanceof ECNGroupVO) {
				ecnGroups.add((ECNGroupVO) frontGroup);
			}
		}
	}

	/**
	 * @see com.hirain.phm.synapsis.setting.support.SettingViewSupporter#parseBoardSettingVO(java.util.List)
	 */
	@Override
	public List<BoardSetting> parseBoardSettingVO(List<BoardSettingVO> boardVOList) {
		List<BoardSetting> boardSettings = new ArrayList<>();
		for (BoardSettingVO vo : boardVOList) {
			BoardSetting setting = new BoardSetting();
			setting.setEnable(vo.getEnable());
			setting.setSlotId(vo.getSlotId());
			setting.setType(vo.getType());
			BoardProperty property = propertyHandler.generateProperty(vo);
			setting.setProperty(property);
			boardSettings.add(setting);
		}
		return boardSettings;
	}

	/**
	 * @see com.hirain.phm.synapsis.setting.support.SettingViewSupporter#parseAlgorithmSettingVO(java.util.List)
	 */
	@Override
	public List<AlgorithmSetting> parseAlgorithmSettingVO(List<AlgorithmSettingVO> algorithmVOList) {
		List<AlgorithmSetting> algorithmSettings = new ArrayList<>();
		if (algorithmVOList.isEmpty()) {
			return algorithmSettings;
		}
		int index = 1;
		for (AlgorithmSettingVO vo : algorithmVOList) {
			AlgorithmSetting algorithmSetting = new AlgorithmSetting();
			algorithmSetting.setCode(index++);
			algorithmSetting.setEnable(vo.getEnable());
			algorithmSetting.setFilename(vo.getFilename());
			algorithmSetting.setFileOriginalName(vo.getFileOriginalName());
			algorithmSetting.setName(vo.getName());
			algorithmSetting.setSlotId(vo.getSlotId());
			algorithmSetting.setSubsystemId(vo.getSubsystemId());
			algorithmSetting.setType(vo.getType());
			algorithmSetting.setVideoIp(vo.getVideoIp());
			algorithmSetting.setVideoUrl(vo.getVideoUrl());
			algorithmSetting.setVariableGroups(new ArrayList<>());

			algorithmSetting.getVariableGroups().addAll(getMVBGroups(vo.getMvbGroups()));
			algorithmSetting.getVariableGroups().addAll(getECNGroups(vo.getEcnGroups()));
			algorithmSettings.add(algorithmSetting);
		}
		return algorithmSettings;
	}

	/**
	 * @see com.hirain.phm.synapsis.setting.support.SettingViewSupporter#parseStoreSettingVO(com.hirain.phm.synapsis.setting.support.param.StoreSettingVO)
	 */
	@Override
	public StoreSetting parseStoreSettingVO(StoreSettingVO vo) {
		StoreSetting setting = new StoreSetting();
		setting.setRawSpace(vo.getRawSpace());
		setting.setRawStrategy(vo.getRawStrategy());
		setting.setResultSpace(vo.getResultSpace());
		setting.setResultStrategy(vo.getResultStrategy());
		List<VariableGroup> groups = new ArrayList<>();
		groups.addAll(getMVBGroups(vo.getMvbGroups()));
		groups.addAll(getECNGroups(vo.getEcnGroups()));
		setting.setStoreVariables(groups);
		return setting;
	}

	/**
	 * @see com.hirain.phm.synapsis.setting.support.SettingViewSupporter#parseTimeSettingVO(com.hirain.phm.synapsis.setting.support.param.TimeSettingVO)
	 */
	@Override
	public TimeSetting parseTimeSettingVO(TimeSettingVO vo) {
		TimeSetting setting = new TimeSetting();
		setting.setType(vo.getType());
		if (vo.getEcnGroup() != null && !vo.getEcnGroup().getVariables().isEmpty()) {
			setting.setTimeVariables(converter.convertToDomain(vo.getEcnGroup(), VariableType.ECN));
		} else if (vo.getMvbGroup() != null && !vo.getMvbGroup().getVariables().isEmpty()) {
			setting.setTimeVariables(converter.convertToDomain(vo.getMvbGroup(), VariableType.MVB));
		}
		return setting;
	}

	/**
	 * @param mvbGroups
	 * @return
	 */
	private List<VariableGroup> getMVBGroups(List<MVBGroupVO> mvbGroups) {
		List<VariableGroup> groups = new ArrayList<>();
		if (mvbGroups != null && !mvbGroups.isEmpty()) {
			for (MVBGroupVO frontMVBGroup : mvbGroups) {
				if (!frontMVBGroup.getVariables().isEmpty()) {
					VariableGroup mvbVariableGroup = converter.convertToDomain(frontMVBGroup, VariableType.MVB);
					groups.add(mvbVariableGroup);
				}
			}
		}
		return groups;
	}

	/**
	 * @param ecnGroups
	 * @return
	 */
	private List<VariableGroup> getECNGroups(List<ECNGroupVO> ecnGroups) {
		List<VariableGroup> groups = new ArrayList<>();
		if (ecnGroups != null && !ecnGroups.isEmpty()) {
			for (ECNGroupVO frontECNGroup : ecnGroups) {
				if (!frontECNGroup.getVariables().isEmpty()) {
					VariableGroup ecnVariableGroup = converter.convertToDomain(frontECNGroup, VariableType.ECN);
					groups.add(ecnVariableGroup);
				}
			}
		}
		return groups;
	}
}
