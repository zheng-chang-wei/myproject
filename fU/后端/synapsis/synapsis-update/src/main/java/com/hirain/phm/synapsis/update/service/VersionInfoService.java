/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.update.service;

import java.util.List;

import com.hirain.phm.synapsis.update.domain.VersionInfo;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec Jul 9, 2020 7:29:42 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jul 9, 2020 zepei.tao@hirain.com 1.0 create file
 */
public interface VersionInfoService {

	/**
	 * 删除数据库中对应packageName的版本信息数据项
	 * 
	 * @param packageName
	 */
	void deleteVersionInfo(String packageName);

	/**
	 * 将新的版本信息插入到数据库中
	 * 
	 * @param versionInfo
	 */
	void insertVersionInfo(VersionInfo versionInfo);

	/**
	 * 更新数据库中的版本信息内容
	 * 
	 * @param versionInfo
	 */
	void updateVersionInfo(VersionInfo versionInfo);

	/**
	 * 查询出所有的版本信息
	 * 
	 * @return
	 */
	List<VersionInfo> listVersions();

	VersionInfo selectByPackageName(String packageName);
}
