/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.transform.partition;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hirain.phm.bd.common.partition.IPartitioner;
import com.hirain.phm.bd.common.partition.RoundPartitioner;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年4月25日 下午1:44:42
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年4月25日 jianwen.xin@hirain.com 1.0 create file
 */
@Configuration
public class PartitionConfiguration {

	@Bean
	public IPartitioner getPartitioner() {
		return new RoundPartitioner();
	}
}
