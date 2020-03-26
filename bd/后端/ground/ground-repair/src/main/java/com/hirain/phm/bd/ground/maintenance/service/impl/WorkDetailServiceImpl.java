/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.maintenance.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirain.phm.bd.ground.common.event.UnifiedFaultRecord;
import com.hirain.phm.bd.ground.common.service.BaseService;
import com.hirain.phm.bd.ground.maintenance.dao.WorkDetailMapper;
import com.hirain.phm.bd.ground.maintenance.domain.WorkDetail;
import com.hirain.phm.bd.ground.maintenance.param.CommonStatisticsResult;
import com.hirain.phm.bd.ground.maintenance.param.FaultStatisticsRequestParm;
import com.hirain.phm.bd.ground.maintenance.service.WorkDetailService;
import com.hirain.phm.bd.ground.util.Utils;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年5月30日 上午9:13:30
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年5月30日 jianwen.xin@hirain.com 1.0 create file
 */
@Service
public class WorkDetailServiceImpl extends BaseService<WorkDetail> implements WorkDetailService {

	@Autowired
	WorkDetailMapper workDetailMapper;

	/**
	 * @see com.hirain.phm.bd.ground.maintenance.service.WorkDetailService#createDetail(com.hirain.phm.bd.ground.common.event.UnifiedFaultRecord)
	 */
	@Override
	public WorkDetail createDetail(UnifiedFaultRecord fault) {
		WorkDetail detail = new WorkDetail();
		detail.setFaultTime(fault.getTime());
		detail.setTrainId(fault.getTrainId());
		detail.setDoorId(Utils.getDoor(fault));
		detail.setProjectId(fault.getProjectId());
		save(detail);
		return detail;
	}

	@Override
	public List<CommonStatisticsResult> countProjectFaultByParms(FaultStatisticsRequestParm parm) {
		return workDetailMapper.countProjectFaultByParms(parm);
	}

	@Override
	public List<CommonStatisticsResult> countPartsFaultByParms(FaultStatisticsRequestParm parm) {
		return workDetailMapper.countPartsFaultByParms(parm);
	}

	@Override
	public List<CommonStatisticsResult> countFaultNameByParms(FaultStatisticsRequestParm parm) {
		return workDetailMapper.countFaultNameByParms(parm);
	}

	@Override
	public List<CommonStatisticsResult> getTopFiveProjectFaultByParms(FaultStatisticsRequestParm parm) {
		return workDetailMapper.getTopFiveProjectFaultByParms(parm);
	}

}
