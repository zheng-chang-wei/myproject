/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.fault.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirain.phm.bd.ground.common.model.ResultBean;
import com.hirain.phm.bd.ground.dictionary.service.FaultRepairService;
import com.hirain.phm.bd.ground.dictionary.service.FaultSolutionService;
import com.hirain.phm.bd.ground.fault.domain.FaultInfo;
import com.hirain.phm.bd.ground.fault.param.FaultInfoDTO;
import com.hirain.phm.bd.ground.fault.service.FaultInfoService;
import com.hirain.phm.bd.ground.train.controller.ProjectGateWay;
import com.hirain.phm.bd.ground.train.domain.Project;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Apr 8, 2020 5:56:28 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Apr 8, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@RestController
@RequestMapping("/dictionary/fault")
public class FaultInfoController {

	@Autowired
	private FaultInfoService infoService;

	@Autowired
	private FaultRepairService frService;

	@Autowired
	private FaultSolutionService fsService;

	@Autowired
	private ProjectGateWay gw;

	@GetMapping("/all")
	public ResultBean<List<FaultInfoDTO>> list(String project) {
		List<FaultInfo> infos = infoService.selectAllWithDetail(project);
		List<FaultInfoDTO> vos = infos.stream().map(FaultInfoDTO::valueOf).collect(Collectors.toList());
		return new ResultBean<>(vos);
	}

	@PostMapping("/insert")
	@Transactional
	public ResultBean<String> create(@RequestBody FaultInfoDTO vo) {
		Project project = gw.selectProjectByName(vo.getProject());
		FaultInfo info = FaultInfoDTO.parse(vo);
		info.setProjectId(project.getId());
		boolean duplicate = infoService.checkDuplicate(info);
		if (duplicate) {
			return ResultBean.error("项点名称或代码重复");
		}
		infoService.insertFaultInfo(info);
		frService.update(info.getId(), info.getRepairList());
		fsService.update(info.getId(), info.getSolutionList());
		return new ResultBean<>("添加成功");
	}

	@PostMapping("/update")
	@Transactional
	public ResultBean<String> update(@RequestBody FaultInfoDTO vo) {
		Project project = gw.selectProjectByName(vo.getProject());
		FaultInfo info = FaultInfoDTO.parse(vo);
		info.setProjectId(project.getId());
		boolean duplicate = infoService.checkDuplicate(info);
		if (duplicate) {
			return ResultBean.error("项点名称或代码重复");
		}
		infoService.updateNotNull(info);
		frService.update(info.getId(), info.getRepairList());
		fsService.update(info.getId(), info.getSolutionList());
		return new ResultBean<>("更新成功");
	}

	@PostMapping("/delete")
	@Transactional
	public ResultBean<String> delete(Integer infoId) {
		frService.deleteByInfoId(infoId);
		fsService.deleteByInfoId(infoId);
		FaultInfo info = new FaultInfo();
		info.setId(infoId);
		infoService.delete(info);
		return new ResultBean<>("删除成功");
	}
}
