/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.data.hive;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
public class DruidProperties {

	@Value("${hive.druid.initial-size:1}")
	private int initialSeize;

	@Value("${hive.druid.min-idle:3}")
	private int minIdle;

	@Value("${hive.druid.max-active:20}")
	private int maxActive;

	@Value("${hive.druid.max-wait:60000}")
	private int maxWait;

	@Value("${hive.druid.time-between-eviction-runs-millis:60000}")
	private int timeBetweenEvictionRunsMillis;

	@Value("${hive.druid.min-evictable-idle-time-mills:30000}")
	private int minEvictableIdleTimeMillis;

	@Value("${hive.druid.validation-query:select 1}")
	private String validationQuery = "select 1";

	@Value("${hive.druid.test-while-idle:true}")
	private boolean testWhilteIdle;

	@Value("${hive.druid.test-on-borrow:false}")
	private boolean testOnBorrow;

	@Value("${hive.druid.test-on-return:false}")
	private boolean testOnReturn;

	@Value("${hive.druid.pool-prepared-stats:true}")
	private boolean poolPreparedStatements;

	@Value("${hive.druid.max-pool-size:20}")
	private int maxPoolSize = 20;

	public int getInitialSeize() {
		return initialSeize;
	}

	public int getMinIdle() {
		return minIdle;
	}

	public int getMaxActive() {
		return maxActive;
	}

	public int getMaxWait() {
		return maxWait;
	}

	public int getTimeBetweenEvictionRunsMillis() {
		return timeBetweenEvictionRunsMillis;
	}

	public int getMinEvictableIdleTimeMillis() {
		return minEvictableIdleTimeMillis;
	}

	public String getValidationQuery() {
		return validationQuery;
	}

	public boolean isTestWhilteIdle() {
		return testWhilteIdle;
	}

	public boolean isTestOnBorrow() {
		return testOnBorrow;
	}

	public boolean isTestOnReturn() {
		return testOnReturn;
	}

	public boolean isPoolPreparedStatements() {
		return poolPreparedStatements;
	}

	public int getMaxPoolSize() {
		return maxPoolSize;
	}
}
