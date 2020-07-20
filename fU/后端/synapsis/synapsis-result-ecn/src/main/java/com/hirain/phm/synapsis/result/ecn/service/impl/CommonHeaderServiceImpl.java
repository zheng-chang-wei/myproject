/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.result.ecn.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hirain.phm.synapsis.result.ecn.dao.CommonSegmentMapper;
import com.hirain.phm.synapsis.result.ecn.dao.CommonSegmentSettingMapper;
import com.hirain.phm.synapsis.result.ecn.domain.CommonSegment;
import com.hirain.phm.synapsis.result.ecn.domain.CommonSegmentSetting;
import com.hirain.phm.synapsis.result.ecn.service.CommonHeaderService;
import com.hirain.phm.synapsis.setting.ECNVariable;
import com.hirain.phm.synapsis.setting.Variable;
import com.hirain.phm.synapsis.setting.VariableGroup;
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
	@Transactional
	public void save(CommonSegmentSetting commonHeader) {
		delete(commonHeader.getSettingId());
		VariableGroup subscribeGroup = commonHeader.getSubscribeGroup();
		if (subscribeGroup != null) {
			dbService.saveVariableGroup(subscribeGroup);
			commonHeader.setGroupId(subscribeGroup.getId());
		}
		if (!(commonHeader.getSegments() == null || commonHeader.getSegments().isEmpty())) {
			settingMapper.insertGenerateKey(commonHeader);
		}
		commonHeader.getSegments().forEach(s -> {
			s.setSegmentSettingId(commonHeader.getId());
			if (s.getType().equals("bus")) {
				Variable variable = subscribeGroup.getVariables().stream().filter(v -> ((ECNVariable) v).getName().equals(s.getSource().getName()))
						.findFirst().get();
				s.setVariableId(((ECNVariable) variable).getId());
			}
			segmentMapper.insertGenerateKey(s);
		});
	}

	/**
	 * @see com.hirain.phm.synapsis.result.ecn.service.CommonHeaderService#selectBy(java.lang.Integer)
	 */
	@Override
	public CommonSegmentSetting selectBy(Integer settingId) {
		CommonSegmentSetting segmentSetting = settingMapper.selectBySettingId(settingId);
		if (segmentSetting != null) {
			Integer groupId = segmentSetting.getGroupId();
			if (groupId != null) {
				VariableGroup groups = dbService.selectVariableGroup(groupId);
				segmentSetting.setSubscribeGroup(groups);
				segmentSetting.getSegments().forEach(s -> {
					if (s.getType().equals("bus")) {
						Variable variable = groups.getVariables().stream().filter(v -> ((ECNVariable) v).getId().equals(s.getVariableId()))
								.findFirst().get();
						s.setSource((ECNVariable) variable);
					}
				});
			}
		}
		return segmentSetting;
	}

	/**
	 * @see com.hirain.phm.synapsis.result.ecn.service.CommonHeaderService#delete(int)
	 */
	@Override
	public void delete(int settingId) {
		CommonSegmentSetting setting = selectBy(settingId);
		if (setting != null) {
			settingMapper.deleteByPrimaryKey(setting.getId());
			for (CommonSegment segment : setting.getSegments()) {
				segmentMapper.deleteByPrimaryKey(segment.getId());
				dbService.delete("ECN", segment.getVariableId());
			}
		}
	}

}
