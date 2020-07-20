package com.hirain.phm.bd.data.impala;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import com.alibaba.druid.pool.DruidDataSource;

import lombok.Data;

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
@ConfigurationProperties("impala")
@Data
public class ImpalaDruidConfig {

	private String url;// jdbc:impala://ip_address:21050/default;AuthMech=0;SSL=0

	private String user;

	private String password;

	private String driverClassName;// com.cloudera.impala.jdbc41.Driver

	@Bean(name = "impalaDataSource")
	public DataSource dataSource() {
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setUrl(url);
		dataSource.setUsername(user);
		dataSource.setPassword(password);
		dataSource.setDriverClassName(driverClassName);
		return dataSource;
	}

	@Bean(name = "impalaTemplate")
	public JdbcTemplate hiveDruidTemplate() {
		return new JdbcTemplate(dataSource());
	}
}
