/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.train.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hirain.phm.bd.ground.train.domain.Project;
import com.hirain.phm.bd.ground.train.domain.Train;
import com.hirain.phm.bd.ground.train.service.ProjectService;
import com.hirain.phm.bd.ground.train.service.TrainService;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Nov 4, 2019 2:17:15 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Nov 4, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Component
public class TrainGateWay implements ProjectGateWay {

	@Autowired
	private TrainService trainService;

	@Autowired
	private ProjectService projectService;

	public Train findTrainById(Integer id) {
		return trainService.selectByKey(id);
	}

	public List<Train> findTrainByUserId(Long userId) {
		return trainService.findTrainParamHeaderByUserId(userId);
	}

	public List<Train> selectTrainsByCityAndLine(String city, String line) {
		return trainService.selectByCityAndLine(city, line);
	}

	/**
	 * @param project
	 * @param train
	 */
	public Train selectTrain(String project, String train) {
		return trainService.select(project, train);
	}

	public Project selectProjectById(int id) {
		return projectService.selectByKey(id);
	}

	public List<Project> selectProjects() {
		return projectService.selectAll();
	}

	/** 
	 * @see com.hirain.phm.bd.ground.train.controller.ProjectGateWay#selectProjectByName(java.lang.String)
	 */
	@Override
	public Project selectProjectByName(String project) {
		return projectService.selectByName(project);
	}
}
