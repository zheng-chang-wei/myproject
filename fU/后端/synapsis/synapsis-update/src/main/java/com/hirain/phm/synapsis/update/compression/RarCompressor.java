/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.update.compression;

import java.io.File;

import org.springframework.stereotype.Component;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec Jul 8, 2020 3:07:42 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jul 8, 2020 zepei.tao@hirain.com 1.0 create file
 */
@Component(".rar")
public class RarCompressor implements Compressor {

	/**
	 * @see com.hirain.phm.synapsis.update.compression.Compressor#deCompress(java.io.File, java.lang.String)
	 */
	@Override
	public void deCompress(File sourceFile, String destPath) throws Exception {
		// final File dstDiretory = sourceFile.getParentFile();
		// if (!dstDiretory.exists()) {// 目标目录不存在时，创建该文件夹
		// dstDiretory.mkdirs();
		// }
		// Archive a = null;
		// try {
		// a = new Archive(sourceFile);
		// if (a != null) {
		// FileHeader fh = a.nextFileHeader();
		// while (fh != null) {
		// final String fileName = fh.getFileNameW().isEmpty() ? fh.getFileNameString() : fh.getFileNameW();
		// if (fh.isDirectory()) {
		// final File fol = new File(dstDiretory.getAbsolutePath() + File.separator + fileName);
		// fol.mkdirs();
		// } else {
		// final File out = new File(dstDiretory.getAbsolutePath() + File.separator + fileName.trim());
		//
		// try {
		// if (!out.exists()) {
		// if (!out.getParentFile().exists()) {
		// out.getParentFile().mkdirs();
		// }
		// out.createNewFile();
		// }
		// final FileOutputStream os = new FileOutputStream(out);
		// a.extractFile(fh, os);
		// os.close();
		// } catch (final Exception ex) {
		// ex.printStackTrace();
		// }
		// }
		// fh = a.nextFileHeader();
		// }
		// a.close();
		// }
		// } catch (final Exception e) {
		// throw new SynapsisException("解压rar文件失败");
		// }
	}

}
