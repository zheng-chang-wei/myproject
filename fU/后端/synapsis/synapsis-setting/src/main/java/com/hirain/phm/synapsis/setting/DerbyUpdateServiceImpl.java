/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hirain.phm.synapsis.setting.db.SettingUpdateService;
import com.hirain.phm.synapsis.setting.service.AlgorithmSettingService;
import com.hirain.phm.synapsis.setting.service.BoardSettingService;
import com.hirain.phm.synapsis.setting.service.SettingService;
import com.hirain.phm.synapsis.setting.service.StoreSettingService;
import com.hirain.phm.synapsis.setting.service.TimeSettingService;
import com.hirain.phm.synapsis.setting.service.VariableGroupService;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Apr 21, 2020 4:23:17 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Apr 21, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Service
public class DerbyUpdateServiceImpl implements SettingUpdateService {

	@Autowired
	private SettingService settingService;

	@Autowired
	private BoardSettingService boardSettingService;

	@Autowired
	private AlgorithmSettingService algorithmSettingService;

	@Autowired
	private StoreSettingService storeSettingService;

	@Autowired
	private VariableGroupService groupService;

	@Autowired
	private TimeSettingService timeSettingService;

	/**
	 * @see com.hirain.phm.synapsis.setting.db.SettingUpdateService#createOrUpdate(com.hirain.phm.synapsis.setting.Setting)
	 */
	@Override
	public void createOrUpdate(Setting setting) {
		if (setting.getId() == null) {
			settingService.save(setting);
		} else {
			settingService.updateNotNull(setting);
		}
	}

	/**
	 * @see com.hirain.phm.synapsis.setting.db.SettingUpdateService#updateBoardSetting(java.lang.Integer, java.util.List)
	 */
	@Override
	public void updateBoardSetting(Integer settingId, List<BoardSetting> boards) {
		boardSettingService.deleteBySettingId(settingId);
		boardSettingService.saveList(settingId, boards);
	}

	/**
	 * @see com.hirain.phm.synapsis.setting.db.SettingUpdateService#updateAlgorithmSetting(java.lang.Integer, java.util.List)
	 */
	@Override
	public void updateAlgorithmSetting(Integer settingId, List<AlgorithmSetting> algorithms) {
		algorithmSettingService.deleteBySettingId(settingId);
		algorithmSettingService.saveList(settingId, algorithms);
	}

	/**
	 * @see com.hirain.phm.synapsis.setting.db.SettingUpdateService#updateTimeSetting(java.lang.Integer, com.hirain.phm.synapsis.setting.TimeSetting)
	 */
	@Override
	public void updateTimeSetting(Integer settingId, TimeSetting time) {
		timeSettingService.deleteBySettingId(settingId);
		groupService.deleteTimeVariables(settingId);

		if (time.getTimeVariables() != null) {
			groupService.insertTimeGroup(settingId, time.getTimeVariables());
			time.setGroupId(time.getTimeVariables().getId());
		}
		time.setSettingId(settingId);
		timeSettingService.insertNotNull(time);
	}

	/**
	 * @see com.hirain.phm.synapsis.setting.db.SettingUpdateService#updateStoreSetting(java.lang.Integer,
	 *      com.hirain.phm.synapsis.setting.StoreSetting)
	 */
	@Override
	@Transactional
	public void updateStoreSetting(Integer settingId, StoreSetting store) {
		storeSettingService.deleteBySettingId(settingId);
		groupService.deleteStoreVariables(settingId);

		store.setSettingId(settingId);
		storeSettingService.insert(store);
		groupService.insertStoreGroups(settingId, store.getStoreVariables());
	}

}
