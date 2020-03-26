/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.data.hive;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Mar 5, 2020 6:52:35 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Mar 5, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Configuration
public class HiveDruidConfig {

	@Value("${hive.url}")
	private String url;// jdbc:hive2://192.168.0.130:10000/default

	@Value("${hive.user}")
	private String user;

	@Value("${hive.password}")
	private String password;

	@Value("${hive.driver}")
	private String driverClassName;// org.apache.hive.jdbc.HiveDriver

	@Autowired
	private DruidProperties props;

	@Bean
	public DataSource dataSource() {
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setUrl(url);
		dataSource.setUsername(user);
		dataSource.setPassword(password);
		dataSource.setDriverClassName(driverClassName);

		dataSource.setInitialSize(props.getInitialSeize());
		dataSource.setMinIdle(props.getMinIdle());
		dataSource.setMaxActive(props.getMaxActive());
		dataSource.setMaxWait(props.getMaxWait());
		dataSource.setTimeBetweenEvictionRunsMillis(props.getTimeBetweenEvictionRunsMillis());
		dataSource.setMinEvictableIdleTimeMillis(props.getMinEvictableIdleTimeMillis());
		dataSource.setValidationQuery(props.getValidationQuery());
		dataSource.setTestWhileIdle(props.isTestWhilteIdle());
		dataSource.setTestOnBorrow(props.isTestOnBorrow());
		dataSource.setTestOnReturn(props.isTestOnReturn());
		dataSource.setPoolPreparedStatements(props.isPoolPreparedStatements());
		dataSource.setMaxPoolPreparedStatementPerConnectionSize(props.getMaxPoolSize());
		return dataSource;
	}

	@Bean(name = "hiveTemplate")
	public JdbcTemplate hiveDruidTemplate() {
		return new JdbcTemplate(dataSource());
	}
}
