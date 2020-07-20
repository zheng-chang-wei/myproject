/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.update.compression;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZFile;
import org.springframework.stereotype.Component;

import com.hirain.phm.synapsis.exception.SynapsisException;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec Jul 8, 2020 3:08:00 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jul 8, 2020 zepei.tao@hirain.com 1.0 create file
 */
@Component(".7z")
public class Z7Compressor implements Compressor {

	/**
	 * @see com.hirain.phm.synapsis.update.compression.Compressor#deCompress(java.io.File, java.lang.String)
	 */
	@Override
	public void deCompress(File sourceFile, String destPath) throws Exception {

		try {
			String tarpath = sourceFile.getParentFile().getAbsolutePath();
			SevenZFile sevenZFile = new SevenZFile(sourceFile);
			SevenZArchiveEntry entry = sevenZFile.getNextEntry();
			while (entry != null) {
				if (entry.isDirectory()) {
					new File(tarpath + File.separator + entry.getName()).mkdirs();
					entry = sevenZFile.getNextEntry();
					continue;
				}
				FileOutputStream out = new FileOutputStream(tarpath + File.separator + entry.getName());
				byte[] content = new byte[(int) entry.getSize()];
				sevenZFile.read(content, 0, content.length);
				out.write(content);
				out.close();
				entry = sevenZFile.getNextEntry();
			}
			sevenZFile.close();
		} catch (Exception e) {
			throw new SynapsisException("解压7z文件失败");
		}

	}

}
