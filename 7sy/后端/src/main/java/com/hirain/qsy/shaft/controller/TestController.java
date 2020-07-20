package com.hirain.qsy.shaft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirain.qsy.shaft.common.model.DataRequest;
import com.hirain.qsy.shaft.common.model.ResponseBo;
import com.hirain.qsy.shaft.service.PullDataService;

@RestController
public class TestController {

	@Autowired
	PullDataService pullDataService;

	@GetMapping("/pullAndSave")
	public ResponseBo test(DataRequest dataRequest) throws Exception {
		pullDataService.handleHistoryData(dataRequest);
		return ResponseBo.ok();
	}

	@GetMapping("/pull")
	public ResponseBo testPullData(DataRequest dataRequest) throws Exception {
		return ResponseBo.ok(pullDataService.pullData(dataRequest));
	}
}