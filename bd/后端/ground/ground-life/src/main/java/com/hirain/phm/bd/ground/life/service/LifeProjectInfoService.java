/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.life.service;

import java.util.List;

import com.hirain.phm.bd.ground.common.service.IService;
import com.hirain.phm.bd.ground.life.domain.LifeProjectInfo;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created May 24, 2019 10:29:06 AM
 * @Description
 *              <p>
 *              处理t_life_lineinfo表的service
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               May 24, 2019 zepei.tao@hirain.com 1.0 create file
 */
public interface LifeProjectInfoService extends IService<LifeProjectInfo> {

	/**
	 * 通过lineID从t_life_projectinfo表中找到对应数据的集合
	 */
	List<LifeProjectInfo> findLifeInfos(Integer projectId);

}
