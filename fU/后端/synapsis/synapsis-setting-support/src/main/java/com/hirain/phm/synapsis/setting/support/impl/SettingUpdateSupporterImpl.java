/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting.support.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirain.phm.synapsis.constant.BoardType;
import com.hirain.phm.synapsis.protocol.ParseResult;
import com.hirain.phm.synapsis.protocol.impl.ProtocolServiceImpl;
import com.hirain.phm.synapsis.setting.ADVariable;
import com.hirain.phm.synapsis.setting.AlgorithmSetting;
import com.hirain.phm.synapsis.setting.BoardSetting;
import com.hirain.phm.synapsis.setting.Setting;
import com.hirain.phm.synapsis.setting.Variable.VariableType;
import com.hirain.phm.synapsis.setting.VariableGroup;
import com.hirain.phm.synapsis.setting.support.MulticastAssigner;
import com.hirain.phm.synapsis.setting.support.SettingUpdateSupporter;
import com.hirain.phm.synapsis.setting.support.param.ADGroupVO;
import com.hirain.phm.synapsis.setting.support.param.ADVariableVO;
import com.hirain.phm.synapsis.setting.support.param.AlgorithmSettingVO;
import com.hirain.phm.synapsis.setting.support.param.BoardSettingVO;
import com.hirain.phm.synapsis.setting.support.param.ECNGroupVO;
import com.hirain.phm.synapsis.setting.support.param.MVBGroupVO;
import com.hirain.phm.synapsis.setting.support.param.SettingVO;
import com.hirain.phm.synapsis.setting.support.param.StoreVariablesVO;
import com.hirain.phm.synapsis.setting.support.param.TimeVariablesVO;
import com.hirain.phm.synapsis.setting.support.utils.SupportUtils;
import com.hirain.phm.synapsis.setting.support.variable.VariableGroupConverter;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 9, 2020 5:14:25 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 9, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Service
public class SettingUpdateSupporterImpl implements SettingUpdateSupporter {

	@Autowired
	private VariableGroupConverter converter;

	@Autowired
	private MulticastAssigner assigner;

	@Autowired
	private ProtocolServiceImpl protocolParseService;

	@Autowired
	private SettingProperties properties;

	@Override
	public Setting voToSetting(SettingVO frontSetting) {
		Setting setting = new Setting();
		setting.setName(frontSetting.getName());
		setting.setLine(frontSetting.getLine());
		setting.setTrain(frontSetting.getTrain());
		setting.setRawSpace(frontSetting.getRawSpace());
		setting.setRawStrategy(frontSetting.getRawStrategy());
		setting.setResultSpace(frontSetting.getResultSpace());
		setting.setResultStrategy(frontSetting.getResultStrategy());
		setting.setTimeOn(frontSetting.getTimeOn());
		if (setting.getTimeOn()) {
			setting.setTimeVariables(getTimeVariable(frontSetting));
		}
		setting.setStoreVariables(getStoreVariables(frontSetting));
		setting.setAlgorithmSettings(getAlgorithmSettings(frontSetting));
		setting.setBoardSettings(getBoardSettings(frontSetting, collectVariableGroup(setting.getAlgorithmSettings())));
		return setting;
	}

	private VariableGroup getTimeVariable(SettingVO frontSetting) {
		TimeVariablesVO timeVariables = frontSetting.getTimeVariables();
		if (timeVariables.getEcnGroup() != null && !timeVariables.getEcnGroup().getVariables().isEmpty()) {
			return converter.convertToDomain(timeVariables.getEcnGroup(), VariableType.ECN);
		} else if (timeVariables.getMvbGroup() != null && !timeVariables.getMvbGroup().getVariables().isEmpty()) {
			return converter.convertToDomain(timeVariables.getMvbGroup(), VariableType.MVB);
		}
		return null;
	}

	private List<VariableGroup> getStoreVariables(SettingVO frontSetting) {
		List<VariableGroup> storeVariableGroups = new ArrayList<>();
		StoreVariablesVO storeVariables = frontSetting.getStoreVariables();
		storeVariableGroups.addAll(getMVBGroups(storeVariables.getMvbGroups()));
		storeVariableGroups.addAll(getECNGroups(storeVariables.getEcnGroups()));
		storeVariableGroups.addAll(getADGroups(storeVariables.getAdGroups()));
		return storeVariableGroups;
	}

	private List<AlgorithmSetting> getAlgorithmSettings(SettingVO frontSetting) {
		List<AlgorithmSetting> algorithmSettings = new ArrayList<>();
		List<AlgorithmSettingVO> frontAlgorithmSettings = frontSetting.getAlgorithmSettings();
		if (frontAlgorithmSettings.isEmpty()) {
			return algorithmSettings;
		}
		int index = 1;
		for (AlgorithmSettingVO frontAlgorithmSetting : frontAlgorithmSettings) {
			AlgorithmSetting algorithmSetting = new AlgorithmSetting();
			algorithmSetting.setCode(index++);
			algorithmSetting.setEnable(frontAlgorithmSetting.getEnable());
			algorithmSetting.setFilename(frontAlgorithmSetting.getFilename());
			algorithmSetting.setFileOriginalName(frontAlgorithmSetting.getFileOriginalName());
			algorithmSetting.setName(frontAlgorithmSetting.getName());
			algorithmSetting.setSlotId(frontAlgorithmSetting.getSlotId());
			algorithmSetting.setSubsystemId(frontAlgorithmSetting.getSubsystemId());
			algorithmSetting.setType(frontAlgorithmSetting.getType());
			algorithmSetting.setVariableGroups(new ArrayList<>());

			algorithmSetting.getVariableGroups().addAll(getMVBGroups(frontAlgorithmSetting.getMvbGroups()));
			algorithmSetting.getVariableGroups().addAll(getECNGroups(frontAlgorithmSetting.getEcnGroups()));
			algorithmSetting.getVariableGroups().addAll(getADGroups(frontAlgorithmSetting.getAdGroups()));
			algorithmSettings.add(algorithmSetting);
		}
		return algorithmSettings;
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

	/**
	 * @param adGroups
	 * @return
	 */
	private List<VariableGroup> getADGroups(List<ADGroupVO> adGroups) {
		// AD变量每一个都是一个单独的组播组
		List<VariableGroup> list = new ArrayList<>();
		if (adGroups != null && !adGroups.isEmpty()) {
			for (ADGroupVO frontADGroup : adGroups) {
				List<VariableGroup> groups = convertFrom(frontADGroup.getVariables(), frontADGroup.getSlotId());
				list.addAll(groups);
			}
		}
		return list;
	}

	/**
	 * @param list
	 * @param slotId
	 * @return
	 */
	private List<VariableGroup> convertFrom(List<ADVariableVO> list, int slotId) {
		ADGroupVO adGroup = new ADGroupVO();
		adGroup.setSlotId(slotId);
		List<VariableGroup> groups = new ArrayList<>();
		for (ADVariableVO adVariable : list) {
			adGroup.setVariables(Arrays.asList(adVariable));
			VariableGroup group = converter.convertToDomain(adGroup, VariableType.AD);
			groups.add(group);
		}
		return groups;
	}

	/**
	 * @param algorithmSettings
	 * @return
	 */
	private List<VariableGroup> collectVariableGroup(List<AlgorithmSetting> algorithmSettings) {
		List<VariableGroup> groups = new ArrayList<>();
		for (AlgorithmSetting setting : algorithmSettings) {
			List<VariableGroup> variableGroups = setting.getVariableGroups();
			for (VariableGroup group : variableGroups) {
				groups.add(group);
			}
		}
		return groups;
	}

	private List<BoardSetting> getBoardSettings(SettingVO frontSetting, List<VariableGroup> variableGroups) {
		List<BoardSetting> boardSettings = new ArrayList<>();
		List<BoardSettingVO> frontBoardSettings = frontSetting.getBoardSettings();
		Map<Integer, BoardSetting> boardSettingMap = new HashMap<>();
		for (BoardSettingVO frontBoardSetting : frontBoardSettings) {
			BoardSetting boardSetting = new BoardSetting();
			boardSetting.setEnable(frontBoardSetting.getEnable());
			boardSetting.setFilename(frontBoardSetting.getFilename());
			boardSetting.setFileOriginalName(frontBoardSetting.getFileOriginalName());
			boardSetting.setIp(frontBoardSetting.getIp());
			boardSetting.setSlotId(frontBoardSetting.getSlotId());
			boardSetting.setType(frontBoardSetting.getType());
			boardSetting.setVariableGroups(new ArrayList<>());
			if (boardSettingMap.get(boardSetting.getSlotId()) == null) {
				boardSettingMap.put(boardSetting.getSlotId(), boardSetting);
			}
			if (frontBoardSetting.getVariables() != null) {
				ADGroupVO groupVO = new ADGroupVO();
				groupVO.setSlotId(boardSetting.getSlotId());
				groupVO.setVariables(frontBoardSetting.getVariables());
				VariableGroup group = converter.convertToDomain(groupVO, VariableType.AD);
				boardSetting.setGroup(group);
			}
			boardSettings.add(boardSetting);
		}
		for (VariableGroup group : variableGroups) {
			BoardSetting boardSetting = boardSettingMap.get(group.getSlotId());
			if (boardSetting != null) {
				boardSetting.getVariableGroups().add(group);
			}
		}
		return boardSettings;
	}

	/**
	 * @see com.hirain.phm.synapsis.setting.support.SettingActivateSupporter#assignMulticastIP(com.hirain.phm.synapsis.setting.Setting)
	 */
	@Override
	public void assignMulticastIP(Setting setting) {
		assigner.start();
		VariableGroup timeVariables = setting.getTimeVariables();
		Map<String, VariableGroup> groupCache = new HashMap<>();
		if (timeVariables != null) {
			assignMulticast(timeVariables, assigner, groupCache);
		}
		List<VariableGroup> storeVariableGroups = setting.getStoreVariables();
		for (VariableGroup storeVariableGroup : storeVariableGroups) {
			assignMulticast(storeVariableGroup, assigner, groupCache);
		}
		List<AlgorithmSetting> algorithmSettings = setting.getAlgorithmSettings();
		for (AlgorithmSetting algorithmSetting : algorithmSettings) {
			for (VariableGroup variableGroup : algorithmSetting.getVariableGroups()) {
				assignMulticast(variableGroup, assigner, groupCache);
			}
		}
		setting.setRetryIp(assigner.retryIp());
	}

	/**
	 * @param variableGroup
	 * @param assigner
	 * @param groupCache
	 */
	private void assignMulticast(VariableGroup variableGroup, MulticastAssigner assigner, Map<String, VariableGroup> groupCache) {
		VariableGroup group = getVariableCache(variableGroup, groupCache);
		if (group != null) {
			variableGroup.setConsumptionId(group.getConsumptionId());
			variableGroup.setMulticastIp(group.getMulticastIp());
			variableGroup.setMulticastPort(group.getMulticastPort());
		} else {
			variableGroup.setConsumptionId(assigner.nextConsumption());
			variableGroup.setMulticastIp(assigner.nextIp());
			variableGroup.setMulticastPort(assigner.nextPort());
		}
	}

	/**
	 * @param variableGroup
	 * @param groupCache
	 */
	private VariableGroup getVariableCache(VariableGroup variableGroup, Map<String, VariableGroup> groupCache) {
		if (variableGroup.getType().equals("AD")) {
			ADVariable variable = (ADVariable) variableGroup.getVariables().get(0);
			String key = variableGroup.getSlotId() + "-" + variable.getChnId();
			VariableGroup group = groupCache.get(key);
			if (group == null) {
				groupCache.put(key, variableGroup);
			}
			return group;
		} else {
			return null;
		}
	}

	/**
	 * @see com.hirain.phm.synapsis.SettingActivateSupporter.setting.SettingGenetor#moveFromTempToSetting(com.hirain.phm.synapsis.setting.domain.Setting)
	 */
	@Override
	public void moveFromTempToSetting(Setting setting) throws Exception {
		File root = FileUtils.getFile(properties.getResourceDirectory());
		if (!root.exists()) {
			root.mkdirs();
		}
		File temporary = FileUtils.getFile(properties.getTemporaryDirectory());
		if (!temporary.exists()) {
			temporary.mkdirs();
		}
		File settingFolder = FileUtils.getFile(root, setting.getName());// 每个大配置文件对应的目录
		if (!settingFolder.exists()) {
			settingFolder.mkdirs();
		}

		List<String> fileNames = new ArrayList<>();
		for (BoardSetting boardSetting : setting.getBoardSettings()) {
			String filename = boardSetting.getFilename();
			if (filename == null) {
				continue;
			}
			fileNames.add(SupportUtils.getFilePrefix(filename));
			for (File resourceFile : temporary.listFiles()) {
				if (resourceFile.getName().equals(filename)) {
					convertExcel2Xml(boardSetting, resourceFile, settingFolder);// 把Excel数据流文件转换成xml数据流文件,并将其写入到配置对应的资源文件夹下
					FileUtils.moveFileToDirectory(resourceFile, settingFolder, true);// 将Excel文件移动到配置对应的资源文件夹下
					break;
				}
			}
		}
		for (AlgorithmSetting algorithmSetting : setting.getAlgorithmSettings()) {
			String filename = algorithmSetting.getFilename();
			fileNames.add(SupportUtils.getFilePrefix(filename));
			for (File resourceFile : temporary.listFiles()) {
				if (resourceFile.getName().equals(filename)) {
					FileUtils.moveFileToDirectory(resourceFile, settingFolder, true);// 把算法文件移动到配置对应的资源文件夹下
					break;
				}
			}
		}
		for (File file : settingFolder.listFiles()) {// 将配置资源文件夹中的无效文件删除
			if (!fileNames.contains(SupportUtils.getFilePrefix(file.getName()))) {
				FileUtils.forceDelete(file);
			}
		}
	}

	/**
	 * 把excel数据流转换成xml数据流文件
	 */
	private void convertExcel2Xml(BoardSetting boardSetting, File resourceFile, File settingFolder) throws Exception {
		if (!SupportUtils.isExcelFile(resourceFile.getName())) {
			return;
		}
		String filePath = settingFolder.getAbsolutePath() + File.separator + SupportUtils.getFilePrefix(resourceFile.getName()) + ".xml";
		BoardType boardType = BoardType.valueOf(boardSetting.getType());
		ParseResult result = protocolParseService.parse(boardType.getType(), resourceFile.getAbsolutePath());
		protocolParseService.toFile(boardType.getType(), filePath, result.getData());
	}

	/**
	 * @see com.hirain.phm.synapsis.setting.support.SettingActivateSupporter#deleteResources(com.hirain.phm.synapsis.setting.Setting)
	 */
	@Override
	public void deleteResources(Setting setting) {
		File folder = new File(properties.getResourceDirectory(), setting.getName());
		if (folder.exists()) {
			FileUtils.deleteQuietly(folder);
		}
	}

}
