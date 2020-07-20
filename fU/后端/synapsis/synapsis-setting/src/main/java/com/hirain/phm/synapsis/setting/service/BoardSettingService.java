/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting.service;

import java.util.List;

import com.hirain.phm.synapsis.setting.BoardSetting;
import com.hirain.phm.synapsis.setting.common.IService;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 5, 2019 11:24:40 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 5, 2019 jianwen.xin@hirain.com 1.0 create file
 */
public interface BoardSettingService extends IService<BoardSetting> {

	void saveList(int settingId, List<BoardSetting> boardSettings);

	/**
	 * @param boardSetting
	 */
	void update(BoardSetting boardSetting);

	/**
	 * @param settingId
	 */
	void deleteBySettingId(int settingId);

	/**
	 * @param settingId
	 * @return
	 */
	List<BoardSetting> selectBySettingId(Integer settingId);
}
