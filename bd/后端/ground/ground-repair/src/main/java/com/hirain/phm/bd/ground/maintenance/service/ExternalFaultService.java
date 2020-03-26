/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.maintenance.service;

import com.hirain.phm.bd.ground.common.service.IService;
import com.hirain.phm.bd.ground.maintenance.domain.ExternalFault;
import com.hirain.phm.bd.ground.maintenance.domain.WorkDetail;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年7月16日 下午4:56:00
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年7月16日 jianwen.xin@hirain.com 1.0 create file
 */
public interface ExternalFaultService extends IService<ExternalFault> {

	ExternalFault addFault(WorkDetail detail);
}
