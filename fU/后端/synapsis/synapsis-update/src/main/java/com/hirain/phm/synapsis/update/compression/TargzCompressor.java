/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.update.compression;

import java.io.File;

import org.springframework.stereotype.Component;

import com.hirain.phm.synapsis.exception.SynapsisException;

/**
 * @Version 1.0
 * @Author zepei.tao@hirain.com
 * @Created Dec Jul 8, 2020 7:31:24 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Jul 8, 2020 zepei.tao@hirain.com 1.0 create file
 */
@Component(".tar.gz")
public class TargzCompressor implements Compressor {

	protected static Runtime rt = Runtime.getRuntime();

	private String tar_cmd1 = "tar";

	private String tar_cmd2 = "-zxvf";

	private String tar_cmd3 = "-C";

	/**
	 * @see com.hirain.phm.synapsis.update.compression.Compressor#deCompress(java.io.File, java.lang.String)
	 */
	@Override
	public void deCompress(File sourceFile, String destPath) throws Exception {
		String sourcePath = sourceFile.getAbsolutePath();
		String[] arguments = new String[] { tar_cmd1, tar_cmd2, sourcePath, tar_cmd3, destPath };
		Process process = null;
		try {
			process = rt.exec(arguments);
		} catch (Exception e) {
			if (process != null) {
				process.destroy();
			}
			throw new SynapsisException("解压tar文件失败");
		} finally {
			if (process != null) {
				process.destroy();
			}
		}

	}

}
