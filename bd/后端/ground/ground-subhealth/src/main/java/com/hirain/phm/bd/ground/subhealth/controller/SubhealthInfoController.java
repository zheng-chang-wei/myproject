/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.subhealth.controller;

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
import com.hirain.phm.bd.ground.dictionary.service.SubhealthRepairService;
import com.hirain.phm.bd.ground.dictionary.service.SubhealthSolutionService;
import com.hirain.phm.bd.ground.subhealth.domain.SubhealthInfo;
import com.hirain.phm.bd.ground.subhealth.param.SubhealthInfoDTO;
import com.hirain.phm.bd.ground.subhealth.service.SubhealthInfoService;
import com.hirain.phm.bd.ground.train.controller.ProjectGateWay;
import com.hirain.phm.bd.ground.train.domain.Project;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Apr 9, 2020 2:41:49 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Apr 9, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@RestController
@RequestMapping("/dictionary/subhealth")
public class SubhealthInfoController {

	@Autowired
	private SubhealthInfoService infoService;

	@Autowired
	private SubhealthRepairService srService;

	@Autowired
	private SubhealthSolutionService ssService;

	@Autowired
	private ProjectGateWay gw;

	@GetMapping("/all")
	public ResultBean<List<SubhealthInfoDTO>> list(String project) {
		List<SubhealthInfo> infos = infoService.selectAllWithDetail(project);
		List<SubhealthInfoDTO> list = infos.stream().map(SubhealthInfoDTO::valueOf).collect(Collectors.toList());
		return new ResultBean<>(list);
	}

	@PostMapping("/insert")
	@Transactional
	public ResultBean<String> insert(@RequestBody SubhealthInfoDTO dto) {
		Project project = gw.selectProjectByName(dto.getProject());
		SubhealthInfo info = SubhealthInfoDTO.parse(dto);
		info.setProjectId(project.getId());
		boolean duplicate = infoService.chechDuplicate(info);
		if (duplicate) {
			return ResultBean.error("项点名称或代码重复");
		}
		infoService.save(info);
		srService.update(info.getId(), info.getRepairList());
		ssService.update(info.getId(), info.getSolutionList());
		return new ResultBean<>("添加成功");
	}

	@PostMapping("/update")
	@Transactional
	public ResultBean<String> update(@RequestBody SubhealthInfoDTO dto) {
		Project project = gw.selectProjectByName(dto.getProject());
		SubhealthInfo info = SubhealthInfoDTO.parse(dto);
		info.setProjectId(project.getId());
		boolean duplicate = infoService.chechDuplicate(info);
		if (duplicate) {
			return ResultBean.error("项点名称或代码重复");
		}
		infoService.updateNotNull(info);
		srService.update(info.getId(), info.getRepairList());
		ssService.update(info.getId(), info.getSolutionList());
		return new ResultBean<>("修改成功");
	}

	@PostMapping("/delete")
	@Transactional
	public ResultBean<String> delete(Integer infoId) {
		srService.deleteByInfoId(infoId);
		ssService.deleteByInfoId(infoId);
		SubhealthInfo info = new SubhealthInfo();
		info.setId(infoId);
		infoService.delete(info);
		return new ResultBean<>("删除成功");
	}
}
