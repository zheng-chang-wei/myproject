/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.bigdata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created 2019年6月11日 上午10:09:26
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年6月11日 jianwen.xin@hirain.com 1.0 create file
 */
@Component
public class HadoopStartConfiguration implements ApplicationRunner {

	@Autowired
	private HdfsProperties props;

	/**
	 * @see org.springframework.boot.ApplicationRunner#run(org.springframework.boot.ApplicationArguments)
	 */
	@Override
	public void run(ApplicationArguments args) throws Exception {
		System.setProperty("HADOOP_USER_NAME", props.getUsername());
		System.out.println(System.getProperty("HADOOP_USER_NAME"));
	}

}
