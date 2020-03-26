/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.maintenance.service;

import java.util.List;

import com.hirain.phm.bd.ground.common.event.UnifiedFaultRecord;
import com.hirain.phm.bd.ground.common.service.IService;
import com.hirain.phm.bd.ground.maintenance.domain.WorkDetail;
import com.hirain.phm.bd.ground.maintenance.param.CommonStatisticsResult;
import com.hirain.phm.bd.ground.maintenance.param.FaultStatisticsRequestParm;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年5月30日 上午9:12:35
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年5月30日 jianwen.xin@hirain.com 1.0 create file
 */
public interface WorkDetailService extends IService<WorkDetail> {

	/**
	 * @param fault
	 */
	WorkDetail createDetail(UnifiedFaultRecord fault);

	List<CommonStatisticsResult> countProjectFaultByParms(FaultStatisticsRequestParm parm);

	List<CommonStatisticsResult> countPartsFaultByParms(FaultStatisticsRequestParm parm);

	List<CommonStatisticsResult> countFaultNameByParms(FaultStatisticsRequestParm parm);

	List<CommonStatisticsResult> getTopFiveProjectFaultByParms(FaultStatisticsRequestParm parm);
}
