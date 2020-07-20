/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.update.service;

import java.io.File;

import com.hirain.phm.synapsis.update.domain.VersionVerifyResult;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec Jul 8, 2020 3:48:17 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jul 8, 2020 zepei.tao@hirain.com 1.0 create file
 */
public interface VerifyService {

	/**
	 * 校验上传的更新压缩包内部的文件是否完整可用
	 * 
	 * @param versionPackage
	 *            上传的大压缩包
	 * @param sha1File
	 *            校验文件
	 * @return
	 */
	boolean verifyFile(File versionPackage, File sha1File);

	/**
	 * 校验版本号是否冲突
	 * 
	 * @return
	 */
	VersionVerifyResult verifyVersion(String packageName);
}
