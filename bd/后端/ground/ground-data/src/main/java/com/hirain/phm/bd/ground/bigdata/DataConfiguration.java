/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.bigdata;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hirain.phm.bd.data.TableNameFormatter;
import com.hirain.phm.bd.data.impl.TableNameFormatterImpl;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Mar 23, 2020 11:12:30 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Mar 23, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Configuration
public class DataConfiguration {

	@Bean
	public TableNameFormatter tableName() {
		return new TableNameFormatterImpl();
	}
}
