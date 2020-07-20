/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.subhealth.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirain.phm.bd.ground.dictionary.DictionaryCopyService;
import com.hirain.phm.bd.ground.dictionary.service.SubhealthRepairService;
import com.hirain.phm.bd.ground.dictionary.service.SubhealthSolutionService;
import com.hirain.phm.bd.ground.subhealth.domain.SubhealthInfo;
import com.hirain.phm.bd.ground.subhealth.service.SubhealthInfoService;
import com.hirain.phm.bd.ground.train.controller.ProjectGateWay;
import com.hirain.phm.bd.ground.train.domain.Project;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Apr 16, 2020 12:00:05 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Apr 16, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Service
public class SubhealthInfoCopyService implements DictionaryCopyService {

	@Autowired
	private SubhealthInfoService infoService;

	@Autowired
	private ProjectGateWay projectGW;

	@Autowired
	private SubhealthRepairService srService;

	@Autowired
	private SubhealthSolutionService ssService;

	/**
	 * @see com.hirain.phm.bd.ground.dictionary.DictionaryCopyService#copy(java.lang.String, java.lang.String)
	 */
	@Override
	public void copy(String srcProject, String destProject) {
		List<SubhealthInfo> list = infoService.selectAllWithDetail(srcProject);
		Project project = projectGW.selectProjectByName(destProject);
		list.forEach(f -> {
			f.setProjectId(project.getId());
			f.setId(null);
		});
		infoService.insertList(list);
		list.forEach(f -> {
			srService.update(f.getId(), f.getRepairList());
			ssService.update(f.getId(), f.getSolutionList());
		});
	}

}
