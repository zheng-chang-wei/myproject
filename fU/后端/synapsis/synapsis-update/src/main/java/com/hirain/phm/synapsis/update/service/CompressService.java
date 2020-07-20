/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.update.service;

import java.io.File;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec Jul 8, 2020 3:46:13 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jul 8, 2020 zepei.tao@hirain.com 1.0 create file
 */
public interface CompressService {

	/**
	 * 解压缩更新包
	 * 
	 * @param sourceFile
	 *            要解压的文件
	 * @param destPath
	 *            目标路径
	 * @return
	 */
	boolean deCompress(File sourceFile, String destPath) throws Exception;

}
