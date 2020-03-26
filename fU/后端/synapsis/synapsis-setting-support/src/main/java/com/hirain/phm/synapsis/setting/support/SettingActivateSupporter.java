/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting.support;

import com.hirain.phm.synapsis.setting.Setting;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec 5, 2019 2:44:32 PM
 * @Description
 *              <p>
 *              激活配置的准备工作
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 5, 2019 zepei.tao@hirain.com 1.0 create file
 */
public interface SettingActivateSupporter {

	/**
	 * 生成配置文件，并将setting资源文件夹中的文件复制到相应板卡配置路径下
	 */
	void acivate(Setting setting) throws Exception;

}
