/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting.support;

import java.util.List;

import com.hirain.phm.synapsis.setting.AlgorithmSetting;
import com.hirain.phm.synapsis.setting.BoardSetting;
import com.hirain.phm.synapsis.setting.Setting;
import com.hirain.phm.synapsis.setting.StoreSetting;
import com.hirain.phm.synapsis.setting.TimeSetting;
import com.hirain.phm.synapsis.setting.support.param.AlgorithmSettingVO;
import com.hirain.phm.synapsis.setting.support.param.BoardSettingVO;
import com.hirain.phm.synapsis.setting.support.param.SettingVO;
import com.hirain.phm.synapsis.setting.support.param.StoreSettingVO;
import com.hirain.phm.synapsis.setting.support.param.TimeSettingVO;

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
	 * @param boardVOList
	 * @return
	 */
	List<BoardSetting> parseBoardSettingVO(List<BoardSettingVO> boardVOList);

	/**
	 * @param algorithmVOList
	 * @return
	 */
	List<AlgorithmSetting> parseAlgorithmSettingVO(List<AlgorithmSettingVO> algorithmVOList);

	/**
	 * @param settingVO
	 */
	StoreSetting parseStoreSettingVO(StoreSettingVO settingVO);

	/**
	 * @param timeVO
	 */
	TimeSetting parseTimeSettingVO(TimeSettingVO timeVO);

}