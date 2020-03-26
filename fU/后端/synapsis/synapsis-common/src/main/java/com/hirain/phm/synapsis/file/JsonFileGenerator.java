/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.hirain.phm.synapsis.util.JsonUtil;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 2, 2019 6:52:30 PM
 * @Description
 *              <p>
 *              生成JSON文件
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 2, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Component
@Profile("json")
public class JsonFileGenerator implements FileGenerator {

	private String SUFFIX_JSON = ".json";

	/**
	 * @see com.hirain.phm.synapsis.file.FileGenerator#generate(java.lang.String, java.lang.Object)
	 */
	@Override
	public void generate(String filepath, Object object) throws Exception {
		filepath = filepath + SUFFIX_JSON;
		String jsonString = JsonUtil.formatJson(JSON.toJSONString(object));
		writeStringToFile(filepath, jsonString);
	}

	private void writeStringToFile(String path, String string) throws Exception {
		File file = new File(path);
		if (!file.exists()) {
			final File parentFile = file.getParentFile();
			if (!parentFile.exists()) {
				parentFile.mkdirs();
			}
			file.createNewFile();
		}
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(file);
			out.write(string.getBytes());
		} catch (FileNotFoundException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					throw e;
				}
			}
		}
	}

}
