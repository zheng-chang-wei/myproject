/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting.support.param;

import java.util.List;

import lombok.Data;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec 8, 2019 6:02:19 PM
 * @Description
 *              <p>
 *              前端传过来的配置对应的FrontAlgorithmSetting
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 8, 2019 zepei.tao@hirain.com 1.0 create file
 */
@Data
public class AlgorithmSettingVO {

	/**
	 * 算法名称
	 */
	private String name;

	/**
	 * 卡槽号
	 */
	private Integer slotId;

	/**
	 * 算法编号
	 */
	private Integer code;

	/**
	 * 脚本类型
	 * 0:C 1:C++ 2:Python
	 */
	private Integer type;

	private Boolean enable;

	/**
	 * 算法脚本路径
	 */
	private String filename;

	/**
	 * 算法文件的原始名称
	 */
	private String fileOriginalName;

	/**
	 * 分系统ID
	 */
	private Integer subsystemId;

	private String videoIp;

	private String videoUrl;

	private List<MVBGroupVO> mvbGroups;

	private List<ECNGroupVO> ecnGroups;

	private List<ADGroupVO> adGroups;
}
