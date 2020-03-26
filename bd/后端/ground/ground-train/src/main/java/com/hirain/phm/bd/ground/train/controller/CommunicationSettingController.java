/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.train.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirain.phm.bd.ground.common.annotation.Log;
import com.hirain.phm.bd.ground.common.model.ResponseBo;
import com.hirain.phm.bd.ground.common.page.PageService;
import com.hirain.phm.bd.ground.common.page.QueryRequest;
import com.hirain.phm.bd.ground.train.communication.CommunicationService;
import com.hirain.phm.bd.ground.train.param.TrainParam;
import com.hirain.phm.bd.ground.train.service.TrainService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created May 20, 2019 3:34:24 PM
 * @Description
 *              <p>
 *              系统管理：通信设置 页面对应的controller
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               May 20, 2019 zepei.tao@hirain.com 1.0 create file
 */
@Api(value = "通信设置controller", tags = { "通信设置操作接口" })
@RestController
@RequestMapping(value = "communicationsetting")
@Slf4j
public class CommunicationSettingController {

	@Autowired
	private CommunicationService communicationService;

	@Autowired
	private TrainService trainService;

	@Autowired
	private PageService pageService;

	@GetMapping("/findTrainsByParams")
	@Transactional
	public ResponseBo findTrainsByParams(QueryRequest request, TrainParam trainParms) {
		try {
			return ResponseBo.ok(pageService.selectByPageNumSize(request, () -> trainService.findTrainsByParams(trainParms)));
		} catch (Exception e) {
			log.error(e.getMessage());
			return ResponseBo.error("系统异常");
		}
	}

	/**
	 * 注销车辆
	 */
	@PostMapping("/logOffTrains")
	@Transactional
	@Log("注销标记车辆")
	public ResponseBo logOff(@RequestBody @ApiParam(value = "TrainParam的集合") List<TrainParam> trainParms) {
		try {
			for (TrainParam trainParam : trainParms) {
				communicationService.logOff(trainParam);
			}
			return ResponseBo.ok();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ResponseBo.error("系统异常");
		}
	}

	/**
	 * 停止接收
	 */
	@PostMapping("/stopReceived")
	@Transactional
	@Log("停止接收标记车辆")
	public ResponseBo stopReceived(@RequestBody @ApiParam(value = "TrainParam的集合") List<TrainParam> trainParms) {
		try {
			for (TrainParam trainParam : trainParms) {
				communicationService.stopReceived(trainParam);
			}
			return ResponseBo.ok();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ResponseBo.error("系统异常");
		}
	}

	/**
	 * 恢复接收
	 */
	@PostMapping("/resumeReceived")
	@Transactional
	@Log("恢复接收标记车辆")
	public ResponseBo resumeReceived(@RequestBody @ApiParam(value = "TrainParam的集合") List<TrainParam> trainParms) {
		try {
			for (TrainParam trainParam : trainParms) {
				communicationService.resumeReceived(trainParam);
			}
			return ResponseBo.ok();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ResponseBo.error("系统异常");
		}
	}

}
