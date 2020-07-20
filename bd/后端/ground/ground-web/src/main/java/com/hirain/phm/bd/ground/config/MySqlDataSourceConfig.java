/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.ground.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.alibaba.druid.pool.DruidDataSource;

import lombok.Data;
import tk.mybatis.mapper.autoconfigure.MybatisProperties;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Mar 11, 2020 3:56:50 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Mar 11, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Configuration
@Data
@ConfigurationProperties("spring.datasource.druid")
public class MySqlDataSourceConfig {

	private String dbType;

	private String username;

	private String password;

	private String url;

	private String driverClassName;

	@Bean(name = "mysqlDataSource")
	@Primary
	public DataSource getMySqlDataSource() {
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		dataSource.setDriverClassName(driverClassName);
		return dataSource;
	}

	@Bean(name = "mysqlSqlSessionFactory")
	public SqlSessionFactory mysqlSqlSessionFactory(@Qualifier("mysqlDataSource") DataSource dataSource, MybatisProperties mybatisProperties)
			throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource);
		bean.setConfiguration(mybatisProperties.getConfiguration());
		return bean.getObject();
	}

	@Bean("mysqlSqlSessionTemplate")
	@Primary
	public SqlSessionTemplate template(@Qualifier("mysqlSqlSessionFactory") SqlSessionFactory factory) {
		return new SqlSessionTemplate(factory);
	}
}
