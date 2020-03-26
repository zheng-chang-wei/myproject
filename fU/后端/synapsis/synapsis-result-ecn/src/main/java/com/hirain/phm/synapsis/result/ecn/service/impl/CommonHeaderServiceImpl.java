/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.result.ecn.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirain.phm.synapsis.result.ecn.dao.CommonSegmentMapper;
import com.hirain.phm.synapsis.result.ecn.dao.CommonSegmentSettingMapper;
import com.hirain.phm.synapsis.result.ecn.domain.CommonSegment;
import com.hirain.phm.synapsis.result.ecn.domain.CommonSegmentSetting;
import com.hirain.phm.synapsis.result.ecn.service.CommonHeaderService;
import com.hirain.phm.synapsis.setting.db.VariableDbService;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 21, 2020 3:30:33 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 21, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Service
public class CommonHeaderServiceImpl implements CommonHeaderService {

	@Autowired
	private CommonSegmentSettingMapper settingMapper;

	@Autowired
	private CommonSegmentMapper segmentMapper;

	@Autowired
	private VariableDbService dbService;

	/**
	 * @see com.hirain.phm.synapsis.result.ecn.service.CommonHeaderService#save(com.hirain.phm.synapsis.result.ecn.domain.CommonSegmentSetting)
	 */
	@Override
	public void save(CommonSegmentSetting commonHeader) {
		settingMapper.insertGenerateKey(commonHeader);
		commonHeader.getSegments().forEach(s -> {
			s.setSegmentSettingId(commonHeader.getId());
			dbService.insertVariable("ECN", s.getSource());
			s.setVariableId(s.getSource().getId());
			segmentMapper.insertGenerateKey(s);
		});
	}

	/**
	 * @see com.hirain.phm.synapsis.result.ecn.service.CommonHeaderService#selectBy(java.lang.Integer)
	 */
	@Override
	public CommonSegmentSetting selectBy(Integer settingId) {
		return settingMapper.selectBySettingId(settingId);
	}

	/**
	 * @see com.hirain.phm.synapsis.result.ecn.service.CommonHeaderService#delete(int)
	 */
	@Override
	public void delete(int settingId) {
		CommonSegmentSetting setting = selectBy(settingId);
		settingMapper.deleteByPrimaryKey(setting.getId());
		for (CommonSegment segment : setting.getSegments()) {
			segmentMapper.deleteByPrimaryKey(segment.getId());
			dbService.delete("ECN", segment.getVariableId());
		}
	}

}
