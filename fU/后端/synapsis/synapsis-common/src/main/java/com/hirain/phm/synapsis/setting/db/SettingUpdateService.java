/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting.db;

import java.util.List;

import com.hirain.phm.synapsis.setting.AlgorithmSetting;
import com.hirain.phm.synapsis.setting.BoardSetting;
import com.hirain.phm.synapsis.setting.Setting;
import com.hirain.phm.synapsis.setting.StoreSetting;
import com.hirain.phm.synapsis.setting.TimeSetting;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Apr 20, 2020 6:50:32 PM
 * @Description
 *              <p>
 *              配置保存和更新接口
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Apr 20, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public interface SettingUpdateService {

	/**
	 * @param setting
	 */
	void createOrUpdate(Setting setting);

	void updateBoardSetting(Integer settingId, List<BoardSetting> boards);

	void updateAlgorithmSetting(Integer settingId, List<AlgorithmSetting> algorithms);

	void updateTimeSetting(Integer settingId, TimeSetting time);

	void updateStoreSetting(Integer settingId, StoreSetting store);
}
