/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.algorithm.parse;

import java.io.File;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.hirain.phm.synapsis.setting.PHMAlgorithmSetting;
import com.hirain.phm.synapsis.util.JaxbUtil;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec Dec 16, 2019 3:04:31 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 16, 2019     zepei.tao@hirain.com    1.0   create file   
 */
@Component
@Profile("xml")
public class XmlFileParser implements AlgorithmSettingFileParser<PHMAlgorithmSetting> {

	/** 
	 * @see com.hirain.phm.synapsis.algorithm.parse.AlgorithmSettingFileParser#parse(java.io.File)
	 */
	@Override
	public PHMAlgorithmSetting parse(File file) throws Exception {
		PHMAlgorithmSetting object = (PHMAlgorithmSetting) JaxbUtil.unmarshaller(PHMAlgorithmSetting.class, file.getAbsolutePath());
		return object;
	}

}
