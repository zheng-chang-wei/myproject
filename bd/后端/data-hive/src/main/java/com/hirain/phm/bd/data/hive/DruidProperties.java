/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.data.hive;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import lombok.Data;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Mar 11, 2020 10:05:43 AM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Mar 11, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Service
@Data
@ConfigurationProperties("hive.druid")
public class DruidProperties {

	private String url;// jdbc:hive2://192.168.0.130:10000/default

	private String user;

	private String password;

	private String driverClassName;// org.apache.hive.jdbc.HiveDriver

	private int initialSeize = 1;

	private int minIdle = 3;

	private int maxActive = 20;

	private int maxWait = 60000;

	private int timeBetweenEvictionRunsMillis = 60000;

	private int minEvictableIdleTimeMillis = 30000;

	private String validationQuery = "select 1";

	private boolean testWhilteIdle = true;

	private boolean testOnBorrow = false;

	private boolean testOnReturn = false;

	private boolean poolPreparedStatements = true;

	private int maxPoolSize = 20;

}
