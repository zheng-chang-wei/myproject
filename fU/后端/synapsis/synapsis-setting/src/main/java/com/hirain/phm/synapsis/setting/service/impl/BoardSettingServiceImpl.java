/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.hirain.phm.synapsis.setting.BoardSetting;
import com.hirain.phm.synapsis.setting.Setting;
import com.hirain.phm.synapsis.setting.VariableGroup;
import com.hirain.phm.synapsis.setting.common.BaseService;
import com.hirain.phm.synapsis.setting.dao.BoardSettingMapper;
import com.hirain.phm.synapsis.setting.dao.SettingMapper;
import com.hirain.phm.synapsis.setting.db.BoardSettingQuery;
import com.hirain.phm.synapsis.setting.property.BoardProperty;
import com.hirain.phm.synapsis.setting.service.BoardSettingService;
import com.hirain.phm.synapsis.setting.service.VariableGroupService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 3, 2019 5:51:41 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 3, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@Service
public class BoardSettingServiceImpl extends BaseService<BoardSetting> implements BoardSettingQuery, BoardSettingService {

	@Autowired
	private BoardSettingMapper mapper;

	@Autowired
	private VariableGroupService groupService;

	@Autowired
	private SettingMapper settingMapper;

	@Override
	public List<BoardSetting> selectBySettingId(Integer settingId) {
		Example example = new Example(BoardSetting.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("settingId", settingId);
		List<BoardSetting> list = mapper.selectByExample(example);
		list.forEach(b -> {
			BoardProperty property = JSONObject.parseObject(b.getContent(), BoardProperty.class);
			b.setProperty(property);
		});
		return list;
	}

	/**
	 * @see com.hirain.phm.synapsis.setting.db.BoardSettingQuery#getBoardSetting(int)
	 */
	@Override
	public BoardSetting getBoardSetting(int slotId) {
		Setting current = settingMapper.selectCurrent();
		if (current != null) {
			BoardSetting setting = mapper.selectBySlotId(current.getId(), slotId);
			return setting;
		}
		return null;
	}

	/**
	 * @deprecated 可考虑移除
	 */
	@Deprecated
	public List<VariableGroup> getVariables(int boardId) {
		List<VariableGroup> variables = groupService.selectByBoard(boardId);
		return variables;
	}

	/**
	 * @see com.hirain.phm.synapsis.setting.service.BoardSettingService#saveList(int, java.util.List)
	 */
	@Override
	public void saveList(int settingId, List<BoardSetting> boardSettings) {
		for (BoardSetting boardSetting : boardSettings) {
			boardSetting.setSettingId(settingId);
			String content = JSONObject.toJSONString(boardSetting.getProperty());
			boardSetting.setContent(content);
			mapper.insertGenerateKey(boardSetting);
		}
	}

	/**
	 * @see com.hirain.phm.synapsis.setting.service.BoardSettingService#update(com.hirain.phm.synapsis.setting.BoardSetting)
	 */
	@Override
	public void update(BoardSetting boardSetting) {
		mapper.updateByPrimaryKey(boardSetting);
	}

	/**
	 * @see com.hirain.phm.synapsis.setting.service.BoardSettingService#deleteBySettingId(int)
	 */
	@Override
	public void deleteBySettingId(int settingId) {
		Example example = new Example(BoardSetting.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("settingId", settingId);
		deleteByExample(example);
	}

}
