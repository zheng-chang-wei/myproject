/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting.db;

import com.hirain.phm.synapsis.setting.AlgorithmSetting;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 3, 2019 2:13:03 PM
 * @Description
 *              <p>
 *              查询算法相关配置
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 3, 2019 jianwen.xin@hirain.com 1.0 create file
 */
public interface AlgorithmSettingQuery {

	AlgorithmSetting getAlgorithmSetting(int code);

	AlgorithmSetting getAlgorithmSetting(int settingId, int code);

}
