/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.maintenance.service;

import java.util.List;

import com.hirain.phm.bd.ground.common.service.IService;
import com.hirain.phm.bd.ground.maintenance.domain.ConfigVariable;

/**
 * @Version 1.0
 * @Author changwei.zheng@hirain.com
 * @Created 2020年2月17日 上午10:46:09
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2020年2月17日 changwei.zheng@hirain.com 1.0 create file
 */
public interface ConfigVariableService extends IService<ConfigVariable> {

	List<ConfigVariable> listVariable(Long id);

	boolean isRepeat(ConfigVariable configVariable);

}
