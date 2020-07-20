/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.transform.filter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jul 2, 2020 3:41:56 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jul 2, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Service
public class ProjectFilterImpl implements ProjectFilter {

	@Autowired
	private FilterProperties props;

	/**
	 * @see com.hirain.phm.bd.transform.filter.ProjectFilter#filter(java.lang.String)
	 */
	@Override
	public boolean filter(String project) {
		List<String> projects = props.getProjects();
		if (projects.size() == 0) {
			return true;
		}
		if (projects.size() == 1 && projects.get(0).equals("all")) {
			return true;
		}
		if (projects.contains(project)) {
			return true;
		}
		return false;
	}

}
