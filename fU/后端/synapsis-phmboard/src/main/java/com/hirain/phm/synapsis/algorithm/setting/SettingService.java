/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.algorithm.setting;

import java.io.File;

import com.hirain.phm.synapsis.setting.BoardSetting;
import com.hirain.phm.synapsis.setting.PHMAlgorithmSetting;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec Dec 16, 2019 10:07:55 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 16, 2019 zepei.tao@hirain.com 1.0 create file
 */
public interface SettingService {

	/**
	 * 解析算法配置文件
	 */
	PHMAlgorithmSetting parseAlgorithmSetting(File file) throws Exception;

	/**
	 * 解析板卡配置文件
	 */
	BoardSetting parseBoardSetting(File file) throws Exception;

	/**
	 * 根据算法配置文件，存储其中的变量配置文件，以便与存储的算法原始数据文件相匹配，供前端使用
	 */
	void storageVariableSetting();

	File getDataHeaderFile();

}
