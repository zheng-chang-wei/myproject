/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting.support.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirain.phm.synapsis.constant.BoardType;
import com.hirain.phm.synapsis.setting.AlgorithmSetting;
import com.hirain.phm.synapsis.setting.BoardSetting;
import com.hirain.phm.synapsis.setting.Setting;
import com.hirain.phm.synapsis.setting.support.BoardPropertyHandler;
import com.hirain.phm.synapsis.setting.support.SettingUpdateSupporter;
import com.hirain.phm.synapsis.util.SettingFileConfig;

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
	private SettingFileConfig config;

	@Autowired
	private BoardPropertyHandler boardPropertyHandler;

	/**
	 * @throws IOException
	 * @see com.hirain.phm.synapsis.setting.support.SettingUpdateSupporter#handleBoardResources(String, java.util.List)
	 */
	@Override
	public void handleBoardResources(String settingName, List<BoardSetting> boards) throws Exception {
		File resourceFolder = new File(config.getResource() + File.separator + settingName, "boards");
		File tempFolder = new File(config.getTemp());
		List<String> filenames = new ArrayList<>();
		for (BoardSetting b : boards) {
			List<String> resource = boardPropertyHandler.handleResource(tempFolder, resourceFolder, b.getProperty(), b.getType());
			if (resource != null) {
				filenames.addAll(resource);
			}
		}
		File[] files = resourceFolder.listFiles();
		if (files != null) {
			for (File file : files) {
				if (!filenames.contains(file.getName())) {
					FileUtils.forceDelete(file);
				}
			}
		}
	}

	/**
	 * @throws IOException
	 * @see com.hirain.phm.synapsis.setting.support.SettingUpdateSupporter#handleAlgorithmResources(String, java.util.List)
	 */
	@Override
	public void handleAlgorithmResources(String settingName, List<AlgorithmSetting> algorithms) throws IOException {
		File resourceFolder = new File(config.getResource() + File.separator + settingName, "algorithms");
		File tempFolder = new File(config.getTemp());
		List<String> filenames = new ArrayList<>();
		for (AlgorithmSetting algorithmSetting : algorithms) {
			File file = new File(resourceFolder, algorithmSetting.getFilename());
			if (!file.exists()) {
				File sourceFile = new File(tempFolder, algorithmSetting.getFilename());
				FileUtils.moveFileToDirectory(sourceFile, resourceFolder, true);
			}
			filenames.add(algorithmSetting.getFilename());
		}
		File[] files = resourceFolder.listFiles();
		if (files != null) {
			for (File file : files) {
				if (!filenames.contains(file.getName())) {
					FileUtils.forceDelete(file);
				}
			}
		}
	}

	/**
	 * @see com.hirain.phm.synapsis.setting.support.SettingActivateSupporter#deleteResources(com.hirain.phm.synapsis.setting.Setting)
	 */
	@Override
	public void deleteResources(Setting setting) {
		File folder = new File(config.getResource(), setting.getName());
		if (folder.exists()) {
			FileUtils.deleteQuietly(folder);
		}
	}

	/**
	 * @see com.hirain.phm.synapsis.setting.support.SettingUpdateSupporter#createOrRename(java.lang.String, java.lang.String)
	 */
	@Override
	public void createOrRename(String oldname, String newname) {
		if (oldname != null && newname != null && oldname.equals(newname)) {
			return;
		}
		if (oldname == null) {
			File folder = new File(config.getResource(), newname);
			folder.mkdirs();
		} else {
			File oldFolder = new File(config.getResource(), oldname);
			File newFolder = new File(config.getResource(), newname);
			oldFolder.renameTo(newFolder);
		}
	}

	@Override
	public String getECNProtocolFilepath(Setting setting) {
		for (BoardSetting boardSetting : setting.getBoardSettings()) {
			if (boardSetting.getType().equals(BoardType.ECN.name())) {
				return boardPropertyHandler.getProtocolFile(boardSetting.getProperty());
			}
		}
		return null;
	}

	/**
	 * @see com.hirain.phm.synapsis.setting.support.SettingUpdateSupporter#getFilename(com.hirain.phm.synapsis.setting.BoardSetting)
	 */
	@Override
	public String getFilename(BoardSetting boardSetting) {
		return boardPropertyHandler.getProtocolFile(boardSetting.getProperty());
	}

}
