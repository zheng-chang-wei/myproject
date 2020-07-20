/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.update.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirain.phm.synapsis.update.dao.VersionInfoMapper;
import com.hirain.phm.synapsis.update.domain.VersionInfo;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec Jul 9, 2020 7:30:33 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jul 9, 2020 zepei.tao@hirain.com 1.0 create file
 */
@Service
public class VersionInfoServiceImpl implements VersionInfoService {

	@Autowired
	private VersionInfoMapper versionInfoMapper;

	/**
	 * @see com.hirain.phm.synapsis.update.service.VersionInfoService#deleteVersionInfo(java.lang.String)
	 */
	@Override
	public void deleteVersionInfo(String packageName) {
		versionInfoMapper.deleteVersionInfoByPackageName(packageName);
	}

	/**
	 * @see com.hirain.phm.synapsis.update.service.VersionInfoService#insertVersionInfo(com.hirain.phm.synapsis.update.domain.VersionInfo)
	 */
	@Override
	public void insertVersionInfo(VersionInfo versionInfo) {
		versionInfoMapper.insertGenerateKey(versionInfo);
	}

	/**
	 * @see com.hirain.phm.synapsis.update.service.VersionInfoService#updateVersionInfo(com.hirain.phm.synapsis.update.domain.VersionInfo)
	 */
	@Override
	public void updateVersionInfo(VersionInfo versionInfo) {
		versionInfoMapper.updateByKey(versionInfo);
	}

	/**
	 * @see com.hirain.phm.synapsis.update.service.VersionInfoService#listVersions()
	 */
	@Override
	public List<VersionInfo> listVersions() {
		return versionInfoMapper.listVersions();
	}

	/**
	 * @see com.hirain.phm.synapsis.update.service.VersionInfoService#selectByPackageName(java.lang.String)
	 */
	@Override
	public VersionInfo selectByPackageName(String packageName) {
		return versionInfoMapper.selectByPackageName(packageName);
	}

}
