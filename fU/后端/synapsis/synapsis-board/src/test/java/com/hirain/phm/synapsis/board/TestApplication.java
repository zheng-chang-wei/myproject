/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.synapsis.board;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.hirain.phm.synapsis.setting.db.BoardSettingQuery;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 21, 2019 4:49:19 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 21, 2019 jianwen.xin@hirain.com 1.0 create file
 */
@SpringBootApplication(scanBasePackages = "com.hirain.phm.synapsis")
public class TestApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestApplication.class, args);
	}

	@Bean
	public BoardSettingQuery query() {
		return slotId -> null;
	}
}
