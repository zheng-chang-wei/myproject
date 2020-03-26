/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.data.hive.file;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Mar 11, 2020 1:56:10 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Mar 11, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Configuration
@Component
public class FileWriterFactory {

	public FileWriter create(String tableName) {
		return get(tableName);
	}

	@Bean("filewriter")
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public FileWriterImpl get(String tableName) {
		return new FileWriterImpl(tableName);
	}
}
