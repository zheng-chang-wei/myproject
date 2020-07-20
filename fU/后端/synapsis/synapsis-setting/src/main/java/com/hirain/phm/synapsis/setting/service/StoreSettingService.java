/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting.service;

import com.hirain.phm.synapsis.setting.StoreSetting;
import com.hirain.phm.synapsis.setting.common.IService;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Apr 21, 2020 5:12:37 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Apr 21, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public interface StoreSettingService extends IService<StoreSetting> {

	/**
	 * @param settingId
	 */
	void deleteBySettingId(Integer settingId);

}
