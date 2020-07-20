/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.data.hive.event;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

import com.hirain.phm.bd.data.hive.dao.DataRecordMapper;
import com.hirain.phm.bd.data.hive.hdfs.HadoopService;
import com.hirain.phm.bd.data.hive.hdfs.HdfsProperties;

import lombok.extern.slf4j.Slf4j;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Mar 11, 2020 11:48:13 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Mar 11, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Configuration
@Slf4j
public class LoadEventHandler {

	@Autowired
	private DataRecordMapper mapper;

	@Autowired
	private HadoopService service;

	@Value("${store.file.root:D:\\test}")
	public String fileRoot;

	@Autowired
	private HdfsProperties hdfsProps;

	@Autowired
	private ApplicationEventPublisher publisher;

	@EventListener
	@Async
	public void listen(LoadFileEvent event) {
		try {
			TimeUnit.MINUTES.sleep(5);
		} catch (InterruptedException e1) {
			log.error(e1.getMessage(), e1);
		}
		String filepath = event.getFilepath();
		String remotePath = generateRemotePath(filepath, hdfsProps.getRoot(), fileRoot);
		boolean result = service.uploadFileToHdfs(filepath, remotePath);
		if (result) {
			log.info("file upload:{}", filepath);
			File file = new File(filepath);
			file.delete();
			String partition = getPartition(file.getName());
			mapper.loadHdfsFile(event.getTableName(), remotePath, partition);
			publisher.publishEvent(new RefreshMetadataEvent(event.getTableName(), partition));
			try {
				service.deleteFile(remotePath, true);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		} else {
			log.error("upload file:" + filepath + " to hdfs fail");
		}
	}

	private String generateRemotePath(String src, String hdfsRoot, String root) {
		String temp = src.substring(root.length());
		String dest = hdfsRoot + temp;
		return dest.replaceAll("\\\\", "/");
	}

	private String getPartition(String filename) {
		String date = filename.substring(0, 8);
		return date;
	}

}
