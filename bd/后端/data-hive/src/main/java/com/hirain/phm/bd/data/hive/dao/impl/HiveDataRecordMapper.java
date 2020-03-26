/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.data.hive.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.hirain.phm.bd.data.hive.HiveProperties;
import com.hirain.phm.bd.data.hive.dao.DataRecordMapper;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Mar 5, 2020 7:07:00 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Mar 5, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Service
public class HiveDataRecordMapper implements DataRecordMapper {

	@Autowired
	@Qualifier("hiveTemplate")
	private JdbcTemplate template;

	@Autowired
	private HiveProperties properties;

	@Override
	public void createTable(String tableName) {
		StringBuffer sb = new StringBuffer();
		sb.append("create table if not exists ");
		sb.append(tableName);
		sb.append("(");
		sb.append("project STRING,");
		sb.append("train STRING,");
		sb.append("carriageId INT,");
		sb.append("doorId INT,");
		sb.append("timestamp timestamp,");
		sb.append("data STRING,");
		sb.append("state INT");
		sb.append(")");
		sb.append("partitioned by (date string) ");
		sb.append("row format delimited fields terminated by '" + properties.getSeperator() + "' lines terminated by '\n' ");
		sb.append("stored as textfile");
		template.execute(sb.toString());
	}

	/**
	 * @see com.hirain.phm.bd.data.hive.dao.DataRecordMapper#loadHdfsFile(java.lang.String,
	 *      java.lang.String, String)
	 */
	@Override
	public void loadHdfsFile(String tableName, String filepath, String partition) {
		StringBuffer sb = new StringBuffer();
		sb.append("load data inpath '").append(filepath).append("' into table ").append(tableName);
		sb.append(" partition(date=").append(partition).append(")");
		template.execute(sb.toString());
	}

}
