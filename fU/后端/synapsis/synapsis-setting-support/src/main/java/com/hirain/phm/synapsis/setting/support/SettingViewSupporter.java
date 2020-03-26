/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting.support;

import java.util.List;

import com.hirain.phm.synapsis.setting.Setting;
import com.hirain.phm.synapsis.setting.VariableGroup;
import com.hirain.phm.synapsis.setting.support.param.SettingVO;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 12, 2019 3:28:09 PM
 * @Description
 *              <p>
 *              后端配置对象转前端配置对象，配置展示的准备工作
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 12, 2019 jianwen.xin@hirain.com 1.0 create file
 */
public interface SettingViewSupporter {

	/**
	 * @param setting
	 */
	SettingVO settingToVO(Setting setting);

	/**
	 * 从配置的数据流文件里提取变量列表
	 * 
	 * @param setting
	 * @return
	 */
	List<VariableGroup> getVariableGroupsFromProtocol(Setting setting);

}