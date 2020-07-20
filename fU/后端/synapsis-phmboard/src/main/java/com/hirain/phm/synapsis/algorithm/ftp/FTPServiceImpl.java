/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.algorithm.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Jun 13, 2019 3:45:54 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jun 13, 2019 zepei.tao@hirain.com 1.0 create file
 */
public class FTPServiceImpl implements FTPService {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	private FTPClient ftpClient;

	@Autowired
	private FtpProperties property;

	@Override
	public boolean connectFtpServer() {
		ftpClient = new FTPClient();
		ftpClient.setConnectTimeout(property.getTimeout());// 设置连接超时时间
		ftpClient.setControlEncoding(property.getEncoding());// 设置ftp字符集
		ftpClient.enterLocalPassiveMode();// 设置被动模式，文件传输端口设置
		try {
			ftpClient.connect(property.getHostname(), property.getPort());
			ftpClient.login(property.getUsername(), property.getPassword());
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);// 设置文件传输模式为二进制，可以保证传输的内容不会被改变
			int replyCode = ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(replyCode)) {
				log.error("connect ftp {} {} failed", property.getHostname(), property.getPort());
				ftpClient.disconnect();
				return false;
			}
			log.info("replyCode==========={}", replyCode);
		} catch (IOException e) {
			e.printStackTrace();
			log.error("connect fail ------->>>{}", e.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * @see com.hirain.ecn.analyzer.service.FTPService#closeFTP(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean closeFTP() {
		try {
			if (ftpClient != null && ftpClient.isConnected()) {
				ftpClient.logout();
				ftpClient.disconnect();
			}
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	/**
	 * @see ecnftp.FTPService#upload(java.io.File)
	 */
	@Override
	public boolean upload(File file) {
		boolean result = false;
		try {
			if (ftpClient != null && ftpClient.isConnected()) {
				if (ftpClient.changeWorkingDirectory(property.getDestPath())) {
					if (file.isDirectory()) {
						ftpClient.makeDirectory(property.getDestPath() + File.separator + file.getName());
						ftpClient.changeWorkingDirectory(property.getDestPath() + File.separator + file.getName());
						for (File datFile : file.listFiles()) {
							FileInputStream stream = new FileInputStream(datFile);
							result = ftpClient.storeFile(datFile.getName(), stream);
							stream.close();
						}
					} else {
						FileInputStream stream = new FileInputStream(file);
						result = ftpClient.storeFile(file.getName(), stream);
						stream.close();
					}
					log.info("upload file---------->{}", result);
				}
			}
		} catch (Exception e) {
			log.error("upload file fail------->>>{}" + e.getMessage());
		}
		return result;
	}

	// @Override
	// public FTPFile[] listFiles(String remote, FilenameFilter filter) throws IOException {
	// return ftpClient.listFiles(remote, file -> filter.accept(null, file.getName()));
	// }

	// @Override
	// public boolean downloadFile(String remoteDir, String localDir, String filename) throws Exception {
	// OutputStream outputStream = null;
	// try {
	// if (ftpClient == null) {
	// return false;
	// }
	// ftpClient.changeWorkingDirectory(remoteDir);
	// outputStream = new FileOutputStream(localDir + File.separator + filename);
	// return ftpClient.retrieveFile(filename, outputStream);
	// } catch (Exception e) {
	// log.error(e.getMessage(), e);
	// return false;
	// } finally {
	// if (outputStream != null) {
	// outputStream.close();
	// }
	// }
	// }

	// @Override
	// public boolean download(String remoteDir, String localDirectoryPath, String suffix) {
	// if (ftpClient == null) {
	// return false;
	// }
	// OutputStream outputStream = null;
	// try {
	// ftpClient.changeWorkingDirectory(remoteDir);
	// FTPFile[] ftpFiles = ftpClient.listFiles(remoteDir);
	// File localDirectory = new File(localDirectoryPath);
	// if (!localDirectory.exists()) {
	// localDirectory.mkdirs();
	// }
	// boolean flag = false;
	// boolean downloadSuccess = false;
	// // 遍历当前目录下的文件，并下载后缀符合要求的文件
	// for (FTPFile ftpFile : ftpFiles) {
	// String fileName = ftpFile.getName();
	// if (fileName.endsWith(suffix)) {
	// flag = true;
	// outputStream = new FileOutputStream(localDirectoryPath + System.getProperty("file.separator") + fileName);// 创建文件输出流
	// downloadSuccess = ftpClient.retrieveFile(fileName, outputStream); // 下载文件
	// if (!downloadSuccess) {
	// log.error("download file 【{ }】 fail", fileName);
	// return false;
	// }
	// }
	// }
	// if (!flag) {
	// log.error("directory：{}下没有后缀 {} 的文件", remoteDir, suffix);
	// return false;
	// }
	// log.info("download file success");
	// ftpClient.logout();
	// } catch (IOException e) {
	// log.error("download file from 【{}】 fail ------->>>{}", remoteDir, e.getCause());
	// return false;
	// } finally {
	// if (outputStream != null) {
	// try {
	// outputStream.close();
	// } catch (IOException e) {
	// log.error("outputStream close fail ------->>>{}", e.getCause());
	// }
	// }
	// }
	// return true;
	// }

	// @Override
	// public boolean deleteFile(String pathname) {
	// boolean result = false;
	// try {
	// result = ftpClient.deleteFile(pathname);
	// } catch (IOException e) {
	// return false;
	// }
	// return result;
	// }

	// @Override
	// public boolean delete(String directory) {
	// try {
	// ftpClient.changeWorkingDirectory(directory);
	// FTPFile[] ftpFiles = ftpClient.listFiles(directory);
	// for (FTPFile file : ftpFiles) {
	// ftpClient.dele(file.getName());
	// }
	// ftpClient.logout();
	// } catch (IOException e) {
	// return false;
	// }
	// return true;
	// }

}
