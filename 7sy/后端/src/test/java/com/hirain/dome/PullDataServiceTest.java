package com.hirain.dome;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hirain.qsy.shaft.common.model.DataRequest;
import com.hirain.qsy.shaft.service.PullDataService;

public class PullDataServiceTest extends BaseTest {

	@Autowired
	PullDataService pullDataService;

	@Test
	public void handleHistoryData() {
		DataRequest dataRequest = new DataRequest();
		dataRequest.setTrainType("CDD5B1");
		dataRequest.setTrainNum("5105");
		dataRequest.setStartTime("2020-04-17 00:00:00");
		dataRequest.setEndTime("2020-04-18 00:00:00");
		try {
			pullDataService.handleHistoryData(dataRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
