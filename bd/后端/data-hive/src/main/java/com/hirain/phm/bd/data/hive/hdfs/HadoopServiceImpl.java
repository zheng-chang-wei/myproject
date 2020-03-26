/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.data.hive.hdfs;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import lombok.extern.slf4j.Slf4j;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年5月22日 下午2:36:16
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年5月22日 jianwen.xin@hirain.com 1.0 create file
 */
@Slf4j
public class HadoopServiceImpl implements HadoopService {

	private Configuration conf = null;

	private String defaultHdfsUri;

	/**
	 * @param conf
	 * @param defaultHdfsUri
	 */
	public HadoopServiceImpl(Configuration conf, String defaultHdfsUri) {
		this.conf = conf;
		this.defaultHdfsUri = defaultHdfsUri;
	}

	private FileSystem getFileSystem() throws IOException {
		return FileSystem.get(conf);
	}

	@Override
	public boolean uploadFileToHdfs(String src, String dest) {
		return uploadFileToHdfs(false, true, src, dest);
	}

	/**
	 * @param delSrc
	 * @param overwrite
	 * @param src
	 * @param dest
	 */
	public boolean uploadFileToHdfs(boolean delSrc, boolean overwrite, String src, String dest) {
		Path localSrcPath = new Path(src);
		Path hdfsDstPath = new Path(generateHdfsPath(dest));

		FileSystem fileSystem = null;
		try {
			fileSystem = getFileSystem();
			fileSystem.copyFromLocalFile(delSrc, overwrite, localSrcPath, hdfsDstPath);
			return true;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return false;
		} finally {
			close(fileSystem);
		}
	}

	/**
	 * @param dest
	 * @return
	 */
	private String generateHdfsPath(String dest) {
		String hdfsPath = defaultHdfsUri;
		if (dest.startsWith("/")) {
			hdfsPath += dest;
		} else {
			hdfsPath = hdfsPath + "/" + dest;
		}
		return hdfsPath;
	}

	/**
	 * @param path
	 * @param recursive
	 */
	public void deleteFile(String path, boolean recursive) throws Exception {
		FileSystem fs = getFileSystem();
		Path delpath = new Path(path);
		fs.delete(delpath, recursive);
	}

	/**
	 * @param fileSystem
	 */
	private void close(FileSystem fileSystem) {
		if (fileSystem != null) {
			try {
				fileSystem.close();
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
	}
}
