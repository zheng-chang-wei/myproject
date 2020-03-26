/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.maintenance.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.hirain.phm.bd.ground.common.service.BaseService;
import com.hirain.phm.bd.ground.maintenance.dao.WorkStepMapper;
import com.hirain.phm.bd.ground.maintenance.domain.WorkStep;
import com.hirain.phm.bd.ground.maintenance.service.WorkStepService;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年5月29日 下午6:57:19
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年5月29日 jianwen.xin@hirain.com 1.0 create file
 */
@Service
public class WorkStepServiceImpl extends BaseService<WorkStep> implements WorkStepService {

	@Autowired
	private WorkStepMapper mapper;

	/**
	 * @param sheetId
	 */
	@Override
	public WorkStep addCreateStep(Long sheetId) {
		return addWorkStep(sheetId, "创建工单", 0);
	}

	/**
	 * @param sheetId
	 * @param type
	 * @param seq
	 * @return
	 */
	@Override
	public WorkStep addWorkStep(Long sheetId, String type, int seq) {
		WorkStep step = WorkStep.newStep(sheetId, type);
		step.setSeq(seq);
		save(step);
		return step;
	}

	/**
	 */
	@Override
	public void updateWorkStep(Long stepId, String result, Object content, Long userId) {
		WorkStep last = new WorkStep();
		last.setId(stepId);
		last.setEndTime(new Date());
		last.setResult(result);
		last.setAuditorId(userId);
		if (content != null) {
			String json = JSONObject.toJSONString(content);
			last.setContent(json);
		}
		updateNotNull(last);
	}

	@Override
	public WorkStep findLastSameType(Long sheetId, String type) {
		return mapper.findLastSampleType(sheetId, type);
	}

	/**
	 * @see com.hirain.phm.bd.ground.maintenance.service.WorkStepService#findAllSteps(java.lang.Long)
	 */
	@Override
	public List<WorkStep> findAllSteps(Long sheetId) {
		return mapper.selectBySheetId(sheetId);
	}

}
