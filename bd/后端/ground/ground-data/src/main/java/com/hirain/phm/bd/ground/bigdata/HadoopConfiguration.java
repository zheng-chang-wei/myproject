/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.bigdata;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年6月5日 下午3:11:59
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年6月5日 jianwen.xin@hirain.com 1.0 create file
 */
@Configuration
public class HadoopConfiguration {

	@Value("${hdfs.defaultFS}")
	private String defaultHdfsUri;

	@Bean
	public HadoopService getService() {
		org.apache.hadoop.conf.Configuration configuration = new org.apache.hadoop.conf.Configuration();
		configuration.set("fs.defaultFS", defaultHdfsUri);
		return new HadoopService(configuration);
	}
}
