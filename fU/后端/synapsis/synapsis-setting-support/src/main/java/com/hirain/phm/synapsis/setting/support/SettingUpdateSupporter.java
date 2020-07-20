/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting.support;

import java.io.IOException;
import java.util.List;

import com.hirain.phm.synapsis.setting.AlgorithmSetting;
import com.hirain.phm.synapsis.setting.BoardSetting;
import com.hirain.phm.synapsis.setting.Setting;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 9, 2020 5:13:50 PM
 * @Description
 *              <p>
 *              配置更新的准备工作
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 9, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public interface SettingUpdateSupporter {

	/**
	 * @param setting
	 */
	void deleteResources(Setting setting);

	/**
	 * 处理板卡的资源文件
	 * 
	 * @param settingName
	 * @param boards
	 * @throws IOException
	 */
	void handleBoardResources(String settingName, List<BoardSetting> boards) throws Exception;

	/**
	 * 处理算法的资源文件
	 * 
	 * @param settingName
	 * @param algorithms
	 * @throws IOException
	 */
	void handleAlgorithmResources(String settingName, List<AlgorithmSetting> algorithms) throws IOException;

	/**
	 * @param oldname
	 * @param newname
	 */
	void createOrRename(String oldname, String newname);

	/**
	 * @param setting
	 * @return
	 */
	String getECNProtocolFilepath(Setting setting);

	/**
	 * @param boardSetting
	 * @return
	 */
	String getFilename(BoardSetting boardSetting);
}
