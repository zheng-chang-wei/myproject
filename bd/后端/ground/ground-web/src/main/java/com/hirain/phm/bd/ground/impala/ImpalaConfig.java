/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.impala;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hirain.phm.bd.data.DataQueryService;
import com.hirain.phm.bd.data.impala.ImpalaQueryService;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Mar 11, 2020 3:31:16 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Mar 11, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Configuration
public class ImpalaConfig {

	@Bean
	public DataQueryService dataQueryService() {
		return new ImpalaQueryService();
	}
}
