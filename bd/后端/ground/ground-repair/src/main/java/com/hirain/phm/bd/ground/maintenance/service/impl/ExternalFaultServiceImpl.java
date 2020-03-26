/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.maintenance.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirain.phm.bd.ground.common.service.BaseService;
import com.hirain.phm.bd.ground.maintenance.domain.ExternalFault;
import com.hirain.phm.bd.ground.maintenance.domain.FaultMode;
import com.hirain.phm.bd.ground.maintenance.domain.WorkDetail;
import com.hirain.phm.bd.ground.maintenance.service.ExternalFaultService;
import com.hirain.phm.bd.ground.maintenance.service.FaultModeService;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年7月16日 下午4:57:13
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年7月16日 jianwen.xin@hirain.com 1.0 create file
 */
@Service
public class ExternalFaultServiceImpl extends BaseService<ExternalFault> implements ExternalFaultService {

	@Autowired
	FaultModeService faultModeService;

	/**
	 * @see com.hirain.phm.bd.ground.maintenance.service.ExternalFaultService#addFault(com.hirain.phm.bd.ground.maintenance.domain.WorkDetail)
	 */
	@Override
	public ExternalFault addFault(WorkDetail detail) {
		FaultMode faultMode = faultModeService.selectByKey(detail.getModeId());
		ExternalFault fault = new ExternalFault();
		fault.setDoorId(detail.getDoorId());
		fault.setFaultMode(faultMode.getModeName());
		fault.setStatistics(false);
		fault.setTime(detail.getFaultTime());
		fault.setTrainId(detail.getTrainId());
		save(fault);
		return fault;
	}

}
