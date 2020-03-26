package com.hirain.phm.bd.ground.statistics.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirain.phm.bd.ground.common.model.ResponseBo;
import com.hirain.phm.bd.ground.maintenance.param.FaultStatisticsRequestParm;
import com.hirain.phm.bd.ground.maintenance.service.DoorTypeService;
import com.hirain.phm.bd.ground.maintenance.service.EffectService;
import com.hirain.phm.bd.ground.maintenance.service.FaultModeService;
import com.hirain.phm.bd.ground.maintenance.service.FaultStageService;
import com.hirain.phm.bd.ground.statistics.service.BDStatisticsService;

/**
 * @Version 1.0
 * @Author changwei.zheng@hirain.com
 * @Created 2020年2月13日 下午2:42:43
 * @Description
 *              <p>
 *              博得统计Controller
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2020年2月13日 changwei.zheng@hirain.com 1.0 create file
 */
@RestController
@RequestMapping("/statistics")
public class BDStatisticsController {

	@Autowired
	private BDStatisticsService bdStatisticsService;

	@Autowired
	DoorTypeService doorTypeService;

	@Autowired
	EffectService effectService;

	@Autowired
	FaultModeService faultModeService;

	@Autowired
	FaultStageService faultStageService;

	@GetMapping("/listDoorType")
	public ResponseBo listDoorType() {
		return ResponseBo.ok(doorTypeService.selectAll());
	}

	@GetMapping("/listEffect")
	public ResponseBo listEffect() {
		return ResponseBo.ok(effectService.selectAll());
	}

	@GetMapping("/listFaultMode")
	public ResponseBo listFaultMode() {
		return ResponseBo.ok(faultModeService.selectAll());
	}

	@GetMapping("/listFaultStage")
	public ResponseBo listFaultStage() {
		return ResponseBo.ok(faultStageService.selectAll());
	}

	@GetMapping("/countFaultByProjectName")
	public ResponseBo countFaultByProjectName(String projectName) {
		return ResponseBo.ok(bdStatisticsService.statisticsFaultCountByProjectName(projectName));
	}

	@GetMapping("/countSubhealthByProjectName")
	public ResponseBo countUnhealthByProjectName(String projectName) {
		return ResponseBo.ok(bdStatisticsService.statisticsSubhealthCountByProjectName(projectName));
	}

	@PostMapping("/countProjectFaultByParms")
	public ResponseBo countProjectFaultByParms(@RequestBody FaultStatisticsRequestParm parm) throws Exception {
		return ResponseBo.ok(bdStatisticsService.countProjectFaultByParms(parm));
	}

	@PostMapping("/countPartsFaultByParms")
	public ResponseBo countPartsFaultByParms(@RequestBody FaultStatisticsRequestParm parm) {
		return ResponseBo.ok(bdStatisticsService.countPartsFaultByParms(parm));
	}

	@PostMapping("/countFaultNameByParms")
	public ResponseBo countFaultNameByParms(@RequestBody FaultStatisticsRequestParm parm) throws Exception {
		return ResponseBo.ok(bdStatisticsService.countFaultNameByParms(parm));
	}
}
