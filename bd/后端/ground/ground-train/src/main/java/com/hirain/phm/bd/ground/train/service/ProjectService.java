/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.train.service;

import com.hirain.phm.bd.ground.common.service.IService;
import com.hirain.phm.bd.ground.train.domain.Project;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年11月8日 下午3:55:48
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年11月8日 jianwen.xin@hirain.com 1.0 create file
 */
public interface ProjectService extends IService<Project> {

	/**
	 * @param string
	 */
	Project selectByName(String string);

}
