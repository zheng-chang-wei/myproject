/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.algorithm.setting;

import java.io.File;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;

import com.hirain.phm.synapsis.algorithm.BaseTest;
import com.hirain.phm.synapsis.setting.PHMAlgorithmSetting;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec Dec 16, 2019 3:29:33 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 16, 2019     zepei.tao@hirain.com    1.0   create file   
 */
public class SettingTest extends BaseTest {

	@Autowired
	private SettingService settingService;

	@Test
	public void testParseAlgorithmSetting() throws Exception {
		File file = ResourceUtils.getFile("classpath:algorithmsetting.xml");
		PHMAlgorithmSetting parseAlgorithmSetting = settingService.parseAlgorithmSetting(file);
		System.err.println(parseAlgorithmSetting);
	}

}
