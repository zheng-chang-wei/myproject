/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting.support.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirain.phm.synapsis.file.FileGenerator;
import com.hirain.phm.synapsis.setting.AlgorithmSetting;
import com.hirain.phm.synapsis.setting.BoardSetting;
import com.hirain.phm.synapsis.setting.PHMAlgorithmSetting;
import com.hirain.phm.synapsis.setting.Setting;
import com.hirain.phm.synapsis.setting.StoreSetting;
import com.hirain.phm.synapsis.setting.TimeSetting;
import com.hirain.phm.synapsis.setting.VariableGroup;
import com.hirain.phm.synapsis.setting.property.BusBoardProperty;
import com.hirain.phm.synapsis.setting.support.BoardPropertyHandler;
import com.hirain.phm.synapsis.setting.support.MulticastAssigner;
import com.hirain.phm.synapsis.setting.support.SettingActivateSupporter;
import com.hirain.phm.synapsis.setting.support.extend.ExtenderSetting;
import com.hirain.phm.synapsis.setting.support.extend.SupportExtenderManager;
import com.hirain.phm.synapsis.util.SettingFileConfig;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec 5, 2019 3:26:27 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 5, 2019 zepei.tao@hirain.com 1.0 create file
 */
@Service
public class SettingActivateSupporterImpl implements SettingActivateSupporter {

	@Autowired
	private SettingFileConfig config;

	@Autowired(required = false)
	private FileGenerator generator;

	@Autowired
	private SupportExtenderManager extenderManager;

	@Autowired
	private MulticastAssigner assigner;

	@Autowired
	private BoardPropertyHandler boardPropertyHandler;

	/**
	 * @see com.hirain.phm.synapsis.SettingActivateSupporter.setting.SettingGenetor#acivate(com.hirain.phm.synapsis.setting.domain.Setting)
	 */
	@Override
	public void acivate(Setting setting) throws Exception {
		clearRootFolder();
		List<VariableGroup> groups = collectVaraibleGroups(setting);
		assignMulticastIP(groups);
		setting.setRetryIp(assigner.retryIp());
		mapGroupToBoards(groups, setting.getBoardSettings());
		modifyBoards(setting.getBoardSettings());

		generateSettingFile(setting);
		generateBoardSettingFile(setting);
		generateAlgorithmSettingFile(setting);
		copyFromResourceToSetting(setting);
		extenderManager.activate(new ExtenderSetting(setting.getId(), groups));
	}

	private void modifyBoards(List<BoardSetting> boardSettings) {
		for (BoardSetting boardSetting : boardSettings) {
			if (boardSetting.getProperty() instanceof BusBoardProperty) {
				BusBoardProperty property = (BusBoardProperty) boardSetting.getProperty();
				String filename = property.getFilename();
				property.setFilename(filename.replace(getExtension(filename), ".xml"));
			}
		}
	}

	/**
	 * 获取文件后缀名，带“.”
	 */
	private String getExtension(String filename) {
		int lastIndexOf = filename.lastIndexOf(".");
		String extension = filename.substring(lastIndexOf);
		return extension;
	}

	/**
	 * 
	 */
	private void clearRootFolder() {
		File folder = new File(config.getRoot());
		File[] files = folder.listFiles();
		if (files != null) {
			for (File file : files) {
				FileUtils.deleteQuietly(file);
			}
		}
	}

	/**
	 * @param setting
	 */
	private List<VariableGroup> collectVaraibleGroups(Setting setting) {
		List<VariableGroup> groups = new ArrayList<>();
		List<AlgorithmSetting> algorithms = setting.getAlgorithmSettings();
		algorithms.forEach(a -> {
			groups.addAll(a.getVariableGroups());
		});
		TimeSetting timeSetting = setting.getTimeSetting();
		if (timeSetting.getTimeVariables() != null) {
			groups.add(timeSetting.getTimeVariables());
		}
		StoreSetting storeSetting = setting.getStoreSetting();
		groups.addAll(storeSetting.getStoreVariables());
		groups.addAll(extenderManager.getVariableGroup(setting.getId()));
		return groups;
	}

	private void assignMulticastIP(List<VariableGroup> groups) {
		assigner.start();
		groups.forEach(g -> assignMulticast(g, assigner));
	}

	/**
	 * @param variableGroup
	 * @param assigner
	 */
	private void assignMulticast(VariableGroup variableGroup, MulticastAssigner assigner) {
		variableGroup.setConsumptionId(assigner.nextConsumption());
		variableGroup.setMulticastIp(assigner.nextIp());
		variableGroup.setMulticastPort(assigner.nextPort());
	}

	/**
	 * @param groups
	 * @param boardSettings
	 */
	private void mapGroupToBoards(List<VariableGroup> groups, List<BoardSetting> boardSettings) {
		Map<Integer, List<VariableGroup>> map = new HashMap<>();
		groups.forEach(g -> {
			List<VariableGroup> list = map.get(g.getSlotId());
			if (list == null) {
				list = new ArrayList<>();
				map.put(g.getSlotId(), list);
			}
			list.add(g);
		});
		boardSettings.forEach(b -> {
			List<VariableGroup> list = map.get(b.getSlotId());
			if (list != null) {
				b.setVariableGroups(list);
			}
		});
	}

	private void generateSettingFile(Setting setting) throws Exception {
		String filePath = config.getRoot() + File.separator + config.getName().getAll();
		generator.generate(filePath, setting);
	}

	private void generateBoardSettingFile(Setting setting) throws Exception {
		List<BoardSetting> boardSettings = setting.getBoardSettings();
		for (BoardSetting boardSetting : boardSettings) {
			boardSetting.setRetryIp(setting.getRetryIp());
			String filePath = config.getRoot() + File.separator + String.valueOf(boardSetting.getSlotId()) + "_" + boardSetting.getType()
					+ File.separator + config.getName().getBoard();
			generator.generate(filePath, boardSetting);
		}
	}

	private void generateAlgorithmSettingFile(Setting setting) throws Exception {
		List<AlgorithmSetting> algorithmSettings = setting.getAlgorithmSettings();
		Map<Integer, PHMAlgorithmSetting> algorithmMap = new HashMap<>();
		for (AlgorithmSetting algorithmSetting : algorithmSettings) {
			Integer slotId = algorithmSetting.getSlotId();
			if (algorithmMap.get(slotId) == null) {
				List<AlgorithmSetting> list = Stream.of(algorithmSetting).collect(Collectors.toList());
				algorithmMap.put(slotId, new PHMAlgorithmSetting(list, setting.getRetryIp()));
			} else {
				algorithmMap.get(slotId).getAlgorithmSettings().add(algorithmSetting);
			}
		}
		for (PHMAlgorithmSetting phmAlgorithmSetting : algorithmMap.values()) {// 这里的一个phmAlgorithmSetting对应一个板卡上的所有算法
			Integer slotId = phmAlgorithmSetting.getAlgorithmSettings().get(0).getSlotId();
			phmAlgorithmSetting.setRetryIp(setting.getRetryIp());
			for (BoardSetting boardSetting : setting.getBoardSettings()) {
				if (boardSetting.getSlotId() == slotId) {
					String filePath = config.getRoot() + File.separator + String.valueOf(slotId) + "_" + boardSetting.getType() + File.separator
							+ config.getName().getAlgorithm();
					generator.generate(filePath, phmAlgorithmSetting);
					break;
				}
			}
		}
	}

	/**
	 * @param setting
	 * @throws IOException
	 */
	private void copyFromResourceToSetting(Setting setting) throws IOException {
		File boardFolder = new File(config.getResource() + File.separator + setting.getName(), "boards");
		Map<Integer, File> boardFolderMap = new HashMap<>();
		for (BoardSetting boardSetting : setting.getBoardSettings()) {
			String folderName = String.valueOf(boardSetting.getSlotId()) + "_" + boardSetting.getType();
			File destFolder = new File(config.getRoot(), folderName);
			destFolder.mkdirs();
			boardPropertyHandler.copyFiles(boardFolder, destFolder, boardSetting.getProperty());
			boardFolderMap.put(boardSetting.getSlotId(), destFolder);
		}
		File algorithmFolder = new File(config.getResource() + File.separator + setting.getName(), "algorithms");
		for (AlgorithmSetting algorithmSetting : setting.getAlgorithmSettings()) {
			File file = new File(algorithmFolder, algorithmSetting.getFilename());
			File destFolder = boardFolderMap.get(algorithmSetting.getSlotId());
			FileUtils.copyFileToDirectory(file, destFolder, true);
		}
	}

}
