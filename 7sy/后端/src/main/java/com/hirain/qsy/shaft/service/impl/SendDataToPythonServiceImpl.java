package com.hirain.qsy.shaft.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hirain.qsy.shaft.common.util.JsonUtils;
import com.hirain.qsy.shaft.common.util.PostUtil;
import com.hirain.qsy.shaft.model.ExceptionData;
import com.hirain.qsy.shaft.service.SendDataToPythonService;

@Service("sendDataToPythonService")
public class SendDataToPythonServiceImpl implements SendDataToPythonService {

	@Override
	public List<ExceptionData> postData(String url, String params, List<Date> acquisitionTimeList, List<String> primaryKeyList) {
		String sendPostRequest = PostUtil.sendPostRequest(url, params);
		return JsonUtils.jsonToObject(sendPostRequest, acquisitionTimeList, primaryKeyList);
	}

}
