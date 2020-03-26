/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting.support;

import com.hirain.phm.synapsis.setting.Setting;
import com.hirain.phm.synapsis.setting.support.param.SettingVO;

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
	 * 前端对象转后端对象
	 * 
	 * @param settingVO
	 * @return
	 */
	Setting voToSetting(SettingVO settingVO);

	/**
	 * 自动分配组播地址、端口
	 */
	void assignMulticastIP(Setting setting);

	/**
	 * 将临时文件夹中的资源文件移动到setting配置对应的资源文件夹下
	 */
	void moveFromTempToSetting(Setting setting) throws Exception;

	/**
	 * @param setting
	 */
	void deleteResources(Setting setting);
}
