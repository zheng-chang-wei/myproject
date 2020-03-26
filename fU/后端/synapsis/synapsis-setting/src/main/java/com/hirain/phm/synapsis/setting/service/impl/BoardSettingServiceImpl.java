/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirain.phm.synapsis.setting.BoardSetting;
import com.hirain.phm.synapsis.setting.Setting;
import com.hirain.phm.synapsis.setting.VariableGroup;
import com.hirain.phm.synapsis.setting.dao.BoardSettingMapper;
import com.hirain.phm.synapsis.setting.dao.SettingMapper;
import com.hirain.phm.synapsis.setting.db.BoardSettingQuery;
import com.hirain.phm.synapsis.setting.service.BoardSettingService;
import com.hirain.phm.synapsis.setting.service.VariableGroupService;

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
public class BoardSettingServiceImpl implements BoardSettingQuery, BoardSettingService {

	@Autowired
	private BoardSettingMapper mapper;

	@Autowired
	private VariableGroupService groupService;

	@Autowired
	private SettingMapper settingMapper;

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
	 * @see com.hirain.phm.synapsis.setting.db.BoardSettingQuery#getVariables(int)
	 */
	@Override
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
			mapper.insertGenerateKey(boardSetting);
		}
		for (BoardSetting boardSetting : boardSettings) {
			groupService.saveBoardVariables(boardSetting.getId(), boardSetting.getVariableGroups());
			if (boardSetting.getGroup() != null) {
				groupService.insertVariables(Arrays.asList(boardSetting.getGroup()));
				boardSetting.setFilename(String.valueOf(boardSetting.getGroup().getId()));
				mapper.updateByKey(boardSetting);
			}
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
	 * @see com.hirain.phm.synapsis.setting.service.BoardSettingService#delete(int)
	 */
	@Override
	public void delete(int settingId) {
		List<BoardSetting> boards = mapper.selectSetting(settingId);
		for (BoardSetting board : boards) {
			groupService.deleteBoardVariables(board.getId(), board.getVariableGroups());
		}
		mapper.deleteBySettingId(settingId);
	}

}
