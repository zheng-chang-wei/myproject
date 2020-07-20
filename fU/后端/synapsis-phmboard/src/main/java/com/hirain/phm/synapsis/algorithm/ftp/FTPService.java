/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.algorithm.ftp;

import java.io.File;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Jun 13, 2019 3:45:36 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jun 13, 2019 zepei.tao@hirain.com 1.0 create file
 */
public interface FTPService {

	/**
	 * 连接并登陆FTP
	 */
	boolean connectFtpServer();

	/**
	 * 关闭FTP连接
	 */
	boolean closeFTP();

	/**
	 * @return
	 */
	boolean upload(File file);

	// /**
	// * 删除pathname路径对应的文件
	// */
	// boolean deleteFile(String pathname);
	//
	// /**
	// * 删除指定路径（directory）下的所有文件
	// */
	// boolean delete(String directory);
	//
	// /**
	// * 文件下载
	// *
	// * @param remoteDir
	// * ftp上文件所在文件夹
	// * @param localDirectoryPath
	// * 本地存放路径
	// * @param suffix
	// * 文件后缀名
	// */
	// boolean download(String remoteDir, String localDirectoryPath, String suffix);
	//
	// /**
	// * @param remote
	// * @param filter
	// * @return
	// * @throws IOException
	// */
	// FTPFile[] listFiles(String remote, FilenameFilter filter) throws IOException;
	//
	// /**
	// * @param remoteDir
	// * @param localDir
	// * @param filename
	// * @return
	// * @throws Exception
	// */
	// boolean downloadFile(String remoteDir, String localDir, String filename) throws Exception;
}
