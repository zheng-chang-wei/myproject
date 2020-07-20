package com.hirain.dome;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.hirain.qsy.shaft.common.model.DataRequest;
import com.hirain.qsy.shaft.model.InitialData;
import com.hirain.qsy.shaft.model.PythonData;
import com.hirain.qsy.shaft.service.HttpService;

public class HttpTest {

	@Autowired
	HttpService httpService;

	@Test
	public void setProperty() throws IllegalAccessException, InvocationTargetException {
		PythonData pythonData = new PythonData();
		String name = "temperatureOnPoint_11";
		List<Integer> list = new ArrayList<>();
		list.add(1);
		BeanUtils.setProperty(pythonData, name, list);
		System.out.println(pythonData);
	}

	@Test
	public void test() {
		DataRequest dataRequest = new DataRequest();
		dataRequest.setTrainType("CDD5B1");
		dataRequest.setTrainNum("5105");
		dataRequest.setStartTime("2020-04-17 00:00:00");
		dataRequest.setEndTime("2020-04-18 00:00:00");
		String jsonString = JSON.toJSONString(dataRequest);
		System.out.println(jsonString);
		String response = httpService.sendPostRequest("http://localhost:8082/getHistoryData", jsonString);
		JSON.parseObject(response, InitialData.class);
		System.out.println(response);
	}
}
