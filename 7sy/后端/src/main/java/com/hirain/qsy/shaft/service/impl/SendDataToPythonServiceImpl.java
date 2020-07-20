package com.hirain.qsy.shaft.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hirain.qsy.shaft.common.util.JsonUtils;
import com.hirain.qsy.shaft.model.ExceptionData;
import com.hirain.qsy.shaft.service.HttpService;
import com.hirain.qsy.shaft.service.SendDataToPythonService;

@Service("sendDataToPythonService")
public class SendDataToPythonServiceImpl implements SendDataToPythonService {

	@Value("${python.url}")
	private String url;

	@Autowired
	private HttpService httpService;

	@Override
	public List<ExceptionData> postData(String params, List<Date> acquisitionTimeList, List<String> primaryKeyList) {
		String sendPostRequest = httpService.sendPostRequest(url, params);
		return JsonUtils.jsonToObject(sendPostRequest, acquisitionTimeList, primaryKeyList);
	}

}
