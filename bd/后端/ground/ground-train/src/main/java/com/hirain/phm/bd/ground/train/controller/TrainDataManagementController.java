/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
/**
 * @Version 1.0   
 * @Author weijia.kong@hirain.com
 * @Created Jun 6, 2019 10:48:14 AM
 * @Description
 * <p>
 * @Modification
 * <p>Date         Author      Version     Description  
 * <p>Jun 6, 2019     weijia.kong@hirain.com            1.0        create file 
 */
package com.hirain.phm.bd.ground.train.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirain.phm.bd.ground.common.annotation.Log;
import com.hirain.phm.bd.ground.common.model.ResponseBo;
import com.hirain.phm.bd.ground.common.page.PageService;
import com.hirain.phm.bd.ground.common.page.QueryRequest;
import com.hirain.phm.bd.ground.train.param.DeleteDataParam;
import com.hirain.phm.bd.ground.train.param.TrainParamHeader;
import com.hirain.phm.bd.ground.train.service.TrainStorageInfoService;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@Api(value = "Train Data Management Controller", tags = { "车载数据管理操作接口" })
@RestController
@RequestMapping(value = "train")
@Slf4j
public class TrainDataManagementController {

	@Autowired
	private TrainStorageInfoService service;

	@Autowired
	PageService pageService;

	@GetMapping("/query")
	public ResponseBo query(QueryRequest request, TrainParamHeader trainParam) {
		try {
			return ResponseBo.ok(pageService.selectByPageNumSize(request, () -> service.findByTrainParam(trainParam)));
		} catch (Exception e) {
			log.error("车载数据存储状态查询失败", e);
			return ResponseBo.error("车载数据存储状态查询失败");
		}
	}

	@PostMapping("/delete")
	@Log("车载端数据管理删除车辆数据")
	public ResponseBo delete(@RequestBody DeleteDataParam param) {
		System.out.println(param);
		try {
			return ResponseBo.ok(service.deleteTrainData(param));
		} catch (Exception e) {
			log.error("数据删除失败", e);
			return ResponseBo.error("数据删除失败");
		}
	}
}
