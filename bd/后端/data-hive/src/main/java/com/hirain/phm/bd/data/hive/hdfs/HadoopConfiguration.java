/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.data.hive.hdfs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Mar 11, 2020 11:55:06 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Mar 11, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Configuration
public class HadoopConfiguration {

	@Value("${store.hdfs.defaultFS:hdfs://192.168.40.31:8022}")
	private String defaultHdfsUri;

	@Bean
	public HadoopService getService() {
		org.apache.hadoop.conf.Configuration configuration = new org.apache.hadoop.conf.Configuration();
		configuration.set("fs.defaultFS", defaultHdfsUri);
		return new HadoopServiceImpl(configuration, defaultHdfsUri);
	}
}
