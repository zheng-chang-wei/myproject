/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting.support.param;

import java.util.List;

import lombok.Data;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec 8, 2019 5:53:39 PM
 * @Description
 *              <p>
 *              前端传过来配置json对应的配置对象
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 8, 2019 zepei.tao@hirain.com 1.0 create file
 */
@Data
public class SettingVO {

	private Integer id;

	/**
	 * 配置名称
	 */
	private String name;

	/**
	 * 线路名称
	 */
	private String line;

	/**
	 * 车辆号
	 */
	private String train;

	private List<BoardSettingVO> boardSettings;

	private List<AlgorithmSettingVO> algorithmSettings;

	private StoreSettingVO storeSetting;

	private TimeSettingVO timeSetting;

}
