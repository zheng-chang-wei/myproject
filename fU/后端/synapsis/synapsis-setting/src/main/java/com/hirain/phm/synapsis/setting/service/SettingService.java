/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.setting.service;

import java.util.List;

import com.hirain.phm.synapsis.setting.Setting;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 3, 2019 6:25:58 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 3, 2019 jianwen.xin@hirain.com 1.0 create file
 */
public interface SettingService {

	/**
	 * @return
	 */
	List<Setting> selectAll();

	/**
	 * @param id
	 * @return
	 */
	Setting selectById(int id);

	/**
	 * @return
	 */
	Setting selectCurrent();

	/**
	 * @param id
	 */
	Setting selectWithDetail(int id);

	/**
	 * 
	 */
	Setting save(Setting setting);

	/**
	 * @param setting
	 */
	void update(Setting setting);

	/**
	 * @param settingId
	 */
	void delete(int settingId);

	/**
	 * @param string
	 */
	Setting selectByName(String string);

	/**
	 * 
	 */
	void deleteOldest();

	/**
	 * @param id
	 */
	void changeCurrent(Integer id);

	/**
	 * @param setting
	 */
	void saveOrUpdate(Setting setting);

}
