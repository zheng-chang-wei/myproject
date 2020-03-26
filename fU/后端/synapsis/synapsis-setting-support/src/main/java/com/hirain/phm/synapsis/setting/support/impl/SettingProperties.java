/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting.support.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 14, 2019 3:45:46 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 14, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Data
@Component
public class SettingProperties {

	@Value("${setting.file.root}")
	private String rootDirectory;// 配置生效时的文件根目录

	@Value("${setting.file.resource}")
	private String resourceDirectory;// 配置保存时的资源文件根目录

	@Value("${setting.file.temporary}")
	private String temporaryDirectory;// 存放资源文件的临时目录

	@Value("${setting.file.name.all}")
	private String allSettingFileName;

	@Value("${setting.file.name.board}")
	private String boardSettingFileName;

	@Value("${setting.file.name.algorithm}")
	private String algorithmSettingFileName;
}
