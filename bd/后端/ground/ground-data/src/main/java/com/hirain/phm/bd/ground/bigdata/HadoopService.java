/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.bigdata;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FsStatus;
import org.apache.hadoop.fs.Path;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年6月5日 上午11:50:32
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年6月5日 jianwen.xin@hirain.com 1.0 create file
 */
public class HadoopService {

	private Configuration conf = null;

	/**
	 * @param conf
	 * @param defaultHdfsUri
	 */
	public HadoopService(Configuration conf) {
		this.conf = conf;
	}

	private FileSystem getFileSystem() throws IOException {
		return FileSystem.get(conf);
	}

	public FsStatus getStatus() {
		FileSystem system = null;
		try {
			system = getFileSystem();
			return system.getStatus();
		} catch (Exception e) {
			return null;
		} finally {
		}
	}

	public long getCapacity(String file) {
		FileSystem fs = null;
		try {
			Path path = new Path(file);
			fs = getFileSystem();
			boolean exist = fs.exists(path);
			if (!exist) {
				return -1;
			}
			return fs.getContentSummary(path).getLength() / 1024 / 1024;
		} catch (Throwable e) {
			e.printStackTrace();
			return -1;
		} finally {
		}
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
	public void close(FileSystem fileSystem) {
		if (fileSystem != null) {
			try {
				fileSystem.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
