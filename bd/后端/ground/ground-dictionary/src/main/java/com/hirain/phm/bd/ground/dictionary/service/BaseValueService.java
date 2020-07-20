/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.dictionary.service;

import java.util.List;

import com.hirain.phm.bd.ground.common.service.IService;
import com.hirain.phm.bd.ground.dictionary.domain.BaseValue;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Apr 15, 2020 10:24:33 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Apr 15, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public interface BaseValueService extends IService<BaseValue> {

	/**
	 * @param projectId
	 * @return
	 */
	List<BaseValue> list(Integer projectId);

	List<String> getBaseValue(int projectId, String variable);
}
