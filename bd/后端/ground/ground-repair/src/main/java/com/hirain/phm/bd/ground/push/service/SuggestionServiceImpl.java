/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.push.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirain.phm.bd.ground.push.dao.PushInfoMapper;
import com.hirain.phm.bd.ground.push.dao.RepairSuggestionMapper;
import com.hirain.phm.bd.ground.push.dao.TreatmentSuggestionMapper;
import com.hirain.phm.bd.ground.push.domain.PushInfo;
import com.hirain.phm.bd.ground.push.domain.RepairSuggestion;
import com.hirain.phm.bd.ground.push.domain.TreatmentSuggestion;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Oct 21, 2019 10:28:54 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Oct 21, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Service
public class SuggestionServiceImpl implements SuggestionService {

	@Autowired
	private PushInfoMapper pushMapper;

	@Autowired
	private TreatmentSuggestionMapper treatmentMapper;

	@Autowired
	private RepairSuggestionMapper repairMapper;

	/**
	 * @see com.hirain.phm.bd.ground.push.service.SuggestionService#findPushInfo(int, int)
	 */
	@Override
	public PushInfo findPushInfo(int type, int code) {
		PushInfo info = pushMapper.findByKey(type, code);
		return info;
	}

	/**
	 * @see com.hirain.phm.bd.ground.push.service.SuggestionService#findTreatmentSuggestion(int)
	 */
	@Override
	public String findTreatmentSuggestion(Integer id) {
		if (id != null) {
			TreatmentSuggestion treatment = treatmentMapper.selectByPrimaryKey(id);
			return treatment != null ? treatment.getSuggestion() : null;
		}
		return null;
	}

	/**
	 * @see com.hirain.phm.bd.ground.push.service.SuggestionService#findRepairSuggestion(int)
	 */
	@Override
	public String findRepairSuggestion(Integer id) {
		if (id != null) {
			RepairSuggestion repair = repairMapper.selectByPrimaryKey(id);
			return repair != null ? repair.getSuggestion() : null;
		}
		return null;
	}

}
