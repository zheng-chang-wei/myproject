package com.hirain.phm.bd.ground.train.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirain.phm.bd.common.serialize.JsonUtil;
import com.hirain.phm.bd.ground.common.exception.SafeRunner;
import com.hirain.phm.bd.ground.common.model.ResponseBo;
import com.hirain.phm.bd.ground.train.param.TrainParamHeader;
import com.hirain.phm.bd.ground.train.service.ProjectService;
import com.hirain.phm.bd.ground.train.service.TrainService;
import com.hirain.phm.bode.core.ITrain;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/bode/train")
@Slf4j
public class TrainInfoController {

	@Autowired
	private TrainService trainService;

	@Autowired
	private ProjectService projectService;

	@GetMapping("/projects")
	public ResponseBo getAllsProjects() {
		return SafeRunner.getResponse(() -> ResponseBo.ok(projectService.selectAll()), "获取项目失败");
	}

	@GetMapping("/project/trains")
	public ResponseBo getTrainsByProject(String project) {
		return SafeRunner.getResponse(() -> ResponseBo.ok(trainService.selectByProject(project)), "获取车辆信息失败");
	}

	@GetMapping("/getTrainConfigInfo")
	public ResponseBo getTrainConfigInfo(TrainParamHeader trainParams) {
		try {
			ITrain trainConfigInfo = trainService.getTrainConfigInfo(trainParams.getProject(), trainParams.getTrainNo());
			if (trainConfigInfo == null) {
				return ResponseBo.error("列车配置信息不存在");
			} else {
				return ResponseBo.ok(JsonUtil.toString(trainConfigInfo));
			}
		} catch (Exception e) {
			log.error("获取车辆配置信息失败", e);
			return ResponseBo.error("列车配置信息异常");
		}
	}

	@GetMapping("/dashboard")
	public ResponseBo countTrain() {
		return SafeRunner.run(() -> ResponseBo.ok(trainService.countTrain()), ResponseBo.error("查询失败"));
	}

	@GetMapping("/getTrainDoorCount")
	public ResponseBo getTrainDoorCount(String projectName) {
		try {
			return ResponseBo.ok(trainService.getTrainDoorCount(projectName));
		} catch (Exception e) {
			log.error("获取车辆信息失败", e);
			return ResponseBo.error("获取车辆信息失败");
		}
	}
}
