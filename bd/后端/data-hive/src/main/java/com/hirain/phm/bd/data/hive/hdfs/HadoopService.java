/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.data.hive.hdfs;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年5月22日 下午2:16:41
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年5月22日 jianwen.xin@hirain.com 1.0 create file
 */
public interface HadoopService {

	/**
	 * @param src
	 * @param dest
	 */
	boolean uploadFileToHdfs(String src, String dest);

	/**
	 * @param path
	 * @param recursive
	 * @throws Exception
	 */
	void deleteFile(String path, boolean recursive) throws Exception;
}
