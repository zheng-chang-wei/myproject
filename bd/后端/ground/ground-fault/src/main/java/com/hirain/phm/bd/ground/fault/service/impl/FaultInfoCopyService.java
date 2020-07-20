/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.fault.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirain.phm.bd.ground.dictionary.DictionaryCopyService;
import com.hirain.phm.bd.ground.dictionary.service.FaultRepairService;
import com.hirain.phm.bd.ground.dictionary.service.FaultSolutionService;
import com.hirain.phm.bd.ground.fault.domain.FaultInfo;
import com.hirain.phm.bd.ground.fault.service.FaultInfoService;
import com.hirain.phm.bd.ground.train.controller.ProjectGateWay;
import com.hirain.phm.bd.ground.train.domain.Project;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Apr 16, 2020 11:40:27 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Apr 16, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Service
public class FaultInfoCopyService implements DictionaryCopyService {

	@Autowired
	private FaultInfoService infoService;

	@Autowired
	private ProjectGateWay projectGW;

	@Autowired
	private FaultRepairService frService;

	@Autowired
	private FaultSolutionService fsService;

	/**
	 * @see com.hirain.phm.bd.ground.dictionary.DictionaryCopyService#copy(java.lang.String, java.lang.String)
	 */
	@Override
	public void copy(String srcProject, String destProject) {
		List<FaultInfo> list = infoService.selectAllWithDetail(srcProject);
		Project project = projectGW.selectProjectByName(destProject);
		list.forEach(f -> {
			f.setProjectId(project.getId());
			f.setId(null);
		});
		infoService.insertList(list);
		list.forEach(f -> {
			frService.update(f.getId(), f.getRepairList());
			fsService.update(f.getId(), f.getSolutionList());
		});
	}

}
