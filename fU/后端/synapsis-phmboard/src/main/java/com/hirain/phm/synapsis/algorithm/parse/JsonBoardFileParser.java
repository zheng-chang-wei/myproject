/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.algorithm.parse;

import java.io.File;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.hirain.phm.synapsis.setting.BoardSetting;
import com.hirain.phm.synapsis.util.JsonUtil;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec Dec 16, 2019 3:03:38 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 16, 2019 zepei.tao@hirain.com 1.0 create file
 */
@Component
@Profile("json")
public class JsonBoardFileParser implements BoardSettingFileParser<BoardSetting> {

	/**
	 * @see com.hirain.phm.synapsis.algorithm.parse.BoardSettingFileParser#parse(java.io.File)
	 */
	@Override
	public BoardSetting parse(File file) throws Exception {
		String jsonResult = JsonUtil.readJSONStringFromFile(file);
		return JSON.parseObject(jsonResult, BoardSetting.class);
	}

}
