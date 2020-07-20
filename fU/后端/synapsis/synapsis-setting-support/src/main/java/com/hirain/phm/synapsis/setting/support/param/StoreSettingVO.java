/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting.support.param;

import java.util.List;

import lombok.Data;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec 9, 2019 11:18:43 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 9, 2019 zepei.tao@hirain.com 1.0 create file
 */
@Data
public class StoreSettingVO {

	private Integer settingId;

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

	private List<MVBGroupVO> mvbGroups;

	private List<ECNGroupVO> ecnGroups;

	private List<ADGroupVO> adGroups;
}
