/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting.support.impl;

import static com.hirain.phm.synapsis.setting.support.utils.SupportUtils.getFilePrefix;

import java.io.File;
import java.io.IOException;
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
import com.hirain.phm.synapsis.setting.support.SettingActivateSupporter;
import com.hirain.phm.synapsis.setting.support.extend.SupportExtenderManager;
import com.hirain.phm.synapsis.setting.support.utils.SupportUtils;

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
	private SettingProperties properties;

	@Autowired(required = false)
	private FileGenerator generator;

	@Autowired
	private SupportExtenderManager manager;

	/**
	 * @see com.hirain.phm.synapsis.SettingActivateSupporter.setting.SettingGenetor#acivate(com.hirain.phm.synapsis.setting.domain.Setting)
	 */
	@Override
	public void acivate(Setting setting) throws Exception {
		clearRootFolder();
		generateSettingFile(setting);
		generateBoardSettingFile(setting);
		generateAlgorithmSettingFile(setting);
		copyFromSettingToCurrent(setting);

		manager.activate(setting.getId());
	}

	private void generateSettingFile(Setting setting) throws Exception {
		String filePath = properties.getRootDirectory() + File.separator + properties.getAllSettingFileName();
		generator.generate(filePath, setting);
	}

	private void generateBoardSettingFile(Setting setting) throws Exception {
		List<BoardSetting> boardSettings = setting.getBoardSettings();
		for (BoardSetting boardSetting : boardSettings) {
			boardSetting.setRetryIp(setting.getRetryIp());
			String filePath = properties.getRootDirectory() + File.separator + String.valueOf(boardSetting.getSlotId()) + "_" + boardSetting.getType()
					+ File.separator + properties.getBoardSettingFileName();
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
			for (BoardSetting boardSetting : setting.getBoardSettings()) {
				if (boardSetting.getSlotId() == slotId) {
					String filePath = properties.getRootDirectory() + File.separator + String.valueOf(slotId) + "_" + boardSetting.getType()
							+ File.separator + properties.getAlgorithmSettingFileName();
					phmAlgorithmSetting.setRetryIp(setting.getRetryIp());
					generator.generate(filePath, phmAlgorithmSetting);
					break;
				}
			}
		}
	}

	/**
	 * 
	 */
	private void clearRootFolder() {
		File folder = new File(properties.getRootDirectory());
		File[] files = folder.listFiles();
		if (files != null) {
			for (File file : files) {
				FileUtils.deleteQuietly(file);
			}
		}
	}

	/**
	 * @param setting
	 * @throws IOException
	 */
	public void copyFromSettingToCurrent(Setting setting) throws IOException {
		Map<Integer, BoardSetting> map = new HashMap<>();
		for (BoardSetting boardSetting : setting.getBoardSettings()) {
			map.put(boardSetting.getSlotId(), boardSetting);
		}

		String settingName = setting.getName();
		File root = FileUtils.getFile(properties.getResourceDirectory());
		File resourceFolder = FileUtils.getFile(root, settingName);// setting(该方法参数)在Root路径下对应的资源文件夹
		File[] listFiles = resourceFolder.listFiles((dir, name) -> {
			return !SupportUtils.isExcelFile(name);
		});
		if (listFiles != null) {
			for (File file : listFiles) {
				for (BoardSetting boardSetting : setting.getBoardSettings()) {
					if (boardSetting.getFilename() == null) {
						continue;
					}
					if (getFilePrefix(file.getName()).equals(getFilePrefix(boardSetting.getFilename()))) {
						FileUtils.copyFileToDirectory(file, getBoardSettingDir(boardSetting), true);
						break;
					}
				}
				for (AlgorithmSetting algorithmSetting : setting.getAlgorithmSettings()) {
					if (getFilePrefix(file.getName()).equals(getFilePrefix(algorithmSetting.getFilename()))) {
						FileUtils.copyFileToDirectory(file, getBoardSettingDir(map.get(algorithmSetting.getSlotId())), true);
						break;
					}
				}
			}
		}
	}

	/**
	 * 根据boardSetting得到对应的板卡配置文件夹
	 */
	private File getBoardSettingDir(BoardSetting boardSetting) {
		String str = String.valueOf(boardSetting.getSlotId()) + "_" + boardSetting.getType();
		File folder = FileUtils.getFile(properties.getRootDirectory());
		File[] listFiles = folder.listFiles((f) -> {
			return f.isDirectory();
		});
		for (File file : listFiles) {
			if (file.getName().equals(str)) {
				return file;
			}
		}
		return null;
	}

}
