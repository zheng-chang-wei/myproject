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

	/**
	 * 原始数据存储策略
	 */
	private Integer rawStrategy;

	/**
	 * 原始数据存储空间占比
	 */
	private Integer rawSpace;

	/**
	 * 分析数据存储策略
	 */
	private Integer resultStrategy;

	/**
	 * 分析数据存储空间占比
	 */
	private Integer resultSpace;

	/**
	 * 时间来源，true：总线，false：系统
	 */
	private Boolean timeOn;

	private List<BoardSettingVO> boardSettings;

	private List<AlgorithmSettingVO> algorithmSettings;

	private StoreVariablesVO storeVariables;

	private TimeVariablesVO timeVariables;

}
