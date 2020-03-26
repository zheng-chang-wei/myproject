/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.result.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

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
			File file = ResourceUtils.getFile("classpath:result-segment.txt");
			String json = FileUtils.readFileToString(file);
			List<ResultSegmentContainer> containers = JSONObject.parseArray(json, ResultSegmentContainer.class);
			header = containers.get(0);
			data = containers.get(1);
		} catch (IOException e) {
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
}
