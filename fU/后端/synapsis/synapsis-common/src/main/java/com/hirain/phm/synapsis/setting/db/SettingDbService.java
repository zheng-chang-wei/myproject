/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting.db;

import java.util.List;

import com.hirain.phm.synapsis.setting.Setting;
import com.hirain.phm.synapsis.setting.Subsystem;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Jan 17, 2020 3:35:08 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jan 17, 2020 jianwen.xin@hirain.com 1.0 create file
 */
public interface SettingDbService {

	/**
	 * @return
	 */
	Setting selectCurrent();

	/**
	 * @param setting
	 */
	void saveOrUpdate(Setting setting);

	/**
	 * @param settingId
	 * @return
	 */
	Setting selectWithDetail(int settingId);

	/**
	 * @param id
	 */
	void changeCurrent(Integer id);

	/**
	 * @param settingId
	 * @return
	 */
	Setting selectById(int settingId);

	/**
	 * @param settingId
	 */
	void delete(int settingId);

	/**
	 * @return
	 */
	List<Subsystem> selectAllSubsystems();

	/**
	 * @return
	 */
	List<Setting> selectAll();

}
