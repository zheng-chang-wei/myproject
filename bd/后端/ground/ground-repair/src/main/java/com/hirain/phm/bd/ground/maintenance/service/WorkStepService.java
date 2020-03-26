/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.maintenance.service;

import java.util.List;

import com.hirain.phm.bd.ground.common.service.IService;
import com.hirain.phm.bd.ground.maintenance.domain.WorkStep;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年5月29日 下午6:56:39
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年5月29日 jianwen.xin@hirain.com 1.0 create file
 */
public interface WorkStepService extends IService<WorkStep> {

	/**
	 * @param id
	 * @return
	 */
	WorkStep addCreateStep(Long id);

	/**
	 * @param id
	 * @param type
	 * @param seq
	 * @return
	 */
	WorkStep addWorkStep(Long id, String type, int seq);

	/**
	 * @param content
	 */
	void updateWorkStep(Long stepId, String result, Object content, Long userId);

	/**
	 * @param sheetId
	 * @param type
	 * @return
	 */
	WorkStep findLastSameType(Long sheetId, String type);

	List<WorkStep> findAllSteps(Long sheetId);

}
