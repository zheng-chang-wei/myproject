package com.hirain.qsy.shaft.service.impl;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hirain.qsy.shaft.service.HttpService;

@Service
public class HttpServiceImpl implements HttpService {

	@Override
	public String sendPostRequest(String url, String params) {
		RestTemplate client = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		// MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		HttpEntity<String> formEntity = new HttpEntity<String>(params, headers);

		String response = client.postForObject(url, formEntity, String.class);

		return response;

	}

}
