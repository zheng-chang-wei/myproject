/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting.support.param;

import lombok.Data;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec 8, 2019 6:01:29 PM
 * @Description
 *              <p>
 *              前端传过来的配置对应的BoardSetting
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 8, 2019 zepei.tao@hirain.com 1.0 create file
 */
@Data
public class BoardSettingVO {

	/**
	 * 板卡使能
	 */
	private Boolean enable;

	/**
	 * 前面板IP
	 */
	private String[] ips;

	/**
	 * 卡槽号
	 */
	private Integer slotId;

	/**
	 * 板卡类型
	 */
	private String type;

	/**
	 * 数据流文件路径 (mvb、ecn卡有值)
	 */
	private String filename;

	private String originalName;

	/**
	 * 信任线，mvb卡有值，0：both 1:line_A 2:line_B
	 */
	private Integer trustLine;

	/**
	 * 模式 mvb卡有值：emd ; esd
	 */
	private String mode;

}
