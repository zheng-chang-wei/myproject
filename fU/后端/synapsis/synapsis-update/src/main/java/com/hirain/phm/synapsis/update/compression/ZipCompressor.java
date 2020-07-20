/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.update.compression;

import java.io.File;

import org.springframework.stereotype.Component;

import com.hirain.phm.synapsis.exception.SynapsisException;

import cn.hutool.core.exceptions.UtilException;
import cn.hutool.core.util.ZipUtil;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec Jul 8, 2020 3:07:27 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jul 8, 2020 zepei.tao@hirain.com 1.0 create file
 */
@Component(".zip")
public class ZipCompressor implements Compressor {

	/**
	 * @see com.hirain.phm.synapsis.update.compression.Compressor#deCompress(java.io.File, java.lang.String)
	 */
	@Override
	public void deCompress(File sourceFile, String destPath) throws Exception {
		try {
			ZipUtil.unzip(sourceFile.getAbsolutePath(), destPath);
		} catch (UtilException e) {
			throw new SynapsisException("解压zip文件失败");
		}
	}

	/**
	 * @see com.hirain.phm.synapsis.update.compression.CompressionFactory#deCompress(java.io.File)
	 */
	// @Override
	// public void deCompress(File sourceFile) throws Exception {
	// final File dFile = sourceFile.getParentFile();
	// String destDirPath = dFile.getAbsolutePath();
	// if (!dFile.exists()) {
	// dFile.mkdirs();
	// }
	// ZipFile zipFile = null;
	// try {
	// BufferedInputStream bis = null;
	// FileOutputStream fos = null;
	// BufferedOutputStream bos = null;
	// zipFile = new ZipFile(sourceFile, CHINESE_CHARSET);
	// final Enumeration<ZipEntry> zipEntries = zipFile.getEntries();
	// File file, parentFile;
	// ZipEntry entry;
	// final byte[] cache = new byte[CACHE_SIZE];
	// while (zipEntries.hasMoreElements()) {
	// entry = zipEntries.nextElement();
	// if (entry.isDirectory()) {
	// new File(destDirPath + entry.getName()).mkdirs();
	// continue;
	// }
	// bis = new BufferedInputStream(zipFile.getInputStream(entry));
	// file = new File(destDirPath + File.separator + entry.getName());
	// parentFile = file.getParentFile();
	//
	// if (parentFile != null && !parentFile.exists()) {
	// parentFile.mkdirs();
	// }
	// fos = new FileOutputStream(file);
	// bos = new BufferedOutputStream(fos, CACHE_SIZE);
	// int readIndex = 0;
	// while ((readIndex = bis.read(cache, 0, CACHE_SIZE)) != -1) {
	// fos.write(cache, 0, readIndex);
	// }
	// bos.flush();
	// bos.close();
	// fos.close();
	// bis.close();
	// }
	// } catch (final IOException e) {
	// throw new SynapsisException("解压zip文件失败");
	// } finally {
	// try {
	// zipFile.close();
	// } catch (final IOException e) {
	// throw new SynapsisException("解压zip文件失败");
	// }
	// }
	// }

}
