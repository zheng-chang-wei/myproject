/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.train.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hirain.phm.bd.ground.common.service.BaseService;
import com.hirain.phm.bd.ground.train.domain.Project;
import com.hirain.phm.bd.ground.train.service.ProjectService;

import tk.mybatis.mapper.entity.Example;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年11月8日 下午3:56:30
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年11月8日 jianwen.xin@hirain.com 1.0 create file
 */
@Service
public class ProjectServiceImpl extends BaseService<Project> implements ProjectService {

	/**
	 * @see com.hirain.phm.bd.ground.train.service.ProjectService#selectByName(java.lang.String)
	 */
	@Override
	public Project selectByName(String project) {
		Example example = new Example(Project.class);
		example.createCriteria().andCondition("name=", project);
		List<Project> list = selectByExample(example);
		return list.isEmpty() ? null : list.get(0);
	}

}
