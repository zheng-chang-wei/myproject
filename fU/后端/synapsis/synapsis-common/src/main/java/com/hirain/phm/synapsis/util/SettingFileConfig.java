/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jul 10, 2020 3:36:39 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jul 10, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Component
@ConfigurationProperties("setting.file")
@Data
public class SettingFileConfig {

	private String root;// 配置生效时的文件根目录

	private String resource;// 配置保存时的资源文件根目录

	private String temp;

	private Name name = new Name();

	@Data
	public static class Name {

		private String all;

		private String algorithm;

		private String board;
	}
}
