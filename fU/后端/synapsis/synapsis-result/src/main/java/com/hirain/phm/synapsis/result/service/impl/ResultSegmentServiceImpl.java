/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.result.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.hirain.phm.synapsis.result.domain.ResultSegmentContainer;
import com.hirain.phm.synapsis.result.service.ResultSegmentService;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 21, 2020 9:41:11 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 21, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Service
public class ResultSegmentServiceImpl implements ResultSegmentService {

	private ResultSegmentContainer header;

	private ResultSegmentContainer data;

	/**
	 * @see com.hirain.phm.synapsis.result.service.ResultSegmentService#getHeaderSegments()
	 */
	@Override
	public ResultSegmentContainer getHeaderSegments() {
		if (header == null) {
			load();
		}
		return header;
	}

	/**
	 * @throws IOException
	 */
	private void load() {
		try {
			// File file = ResourceUtils.getFile("classpath:result-segment.txt");
			// String json = FileUtils.readFileToString(file);
			String json = readFileByLines("result-segment.txt");
			List<ResultSegmentContainer> containers = JSONObject.parseArray(json, ResultSegmentContainer.class);
			header = containers.get(0);
			data = containers.get(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see com.hirain.phm.synapsis.result.service.ResultSegmentService#getDataSegments()
	 */
	@Override
	public ResultSegmentContainer getDataSegments() {
		if (data == null) {
			load();
		}
		return data;
	}

	private String readFileByLines(String filePath) throws Exception {
		StringBuffer str = new StringBuffer();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(findClassLoader().getResourceAsStream(filePath), "UTF-8"));
			String tempString = null;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				str = str.append(tempString);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
				}
			}
		}
		return str.toString();
	}

	/**
	 * 配合api读取linux环境下jar包中的文件路径
	 * 
	 * @return
	 */
	private ClassLoader findClassLoader() {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		if (loader == null) {
			loader = ClassLoader.getSystemClassLoader();
		}
		return loader;
	}
}
