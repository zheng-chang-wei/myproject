/*******************************************************************************
 * Copyright (c) 2020, 2020 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.phm.bd.data.impala.dao.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.hirain.phm.bd.data.bean.DataParam;
import com.hirain.phm.bd.data.bean.DataRecord;
import com.hirain.phm.bd.data.impala.dao.DataMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Mar 6, 2020 1:56:14 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Mar 6, 2020 jianwen.xin@hirain.com 1.0 create file
 */
@Service
@Slf4j
public class ImpalaDataMapper implements DataMapper {

	@Autowired
	@Qualifier("impalaTemplate")
	private JdbcTemplate template;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

	@Override
	public void refresh(String tableName, String partition) {
		String sql = "refresh " + tableName + " partition(`date`='" + partition + "')";
		log.info(sql);
		template.execute(sql);
	}

	@Override
	public int count(String tableName, DataParam param) {
		StringBuffer sb = new StringBuffer();
		sb.append("select count(project) ");
		sb.append("from ").append(tableName).append(" ");
		sb.append("where `date`= ? ");
		log.info(sb.toString());
		Integer count = template.queryForObject(sb.toString(), Integer.class, getFormatDate(param.getDate()));
		return count;
	}

	@Override
	public List<DataRecord> select(String tableName, DataParam param) {
		StringBuffer sb = new StringBuffer();
		sb.append("select project,train,carriageId,doorId,`timestamp`,`data`,state ");
		sb.append("from ").append(tableName).append(" ");
		sb.append("where `date`= ? ");
		sb.append("order by `timestamp` desc ");
		sb.append("limit ? offset ?");
		log.info(sb.toString());
		List<DataRecord> list = template.query(sb.toString(), new Object[] { getFormatDate(param.getDate()), param.getLimit(), param.getOffset() },
				(RowMapper<DataRecord>) (rs, rowNum) -> {
					DataRecord record = new DataRecord();
					record.setProject(rs.getString("project"));
					record.setTrain(rs.getString("train"));
					record.setCarriageId(rs.getInt("carriageId"));
					record.setDoorId(rs.getInt("doorId"));
					Timestamp timestamp = rs.getTimestamp("timestamp");
					if (timestamp != null) {
						record.setTimestamp(new Date(timestamp.getTime()));
					}
					record.setData(rs.getString("data"));
					record.setState(rs.getInt("state"));
					return record;
				});
		return list;
	}

	/**
	 * @param date
	 * @return
	 */
	private Object getFormatDate(Date date) {
		return sdf.format(date);
	}

	/**
	 * @see com.hirain.phm.bd.data.impala.dao.DataMapper#selectPartition(java.lang.String)
	 */
	@Override
	public List<String> selectPartition(String tableName) {
		String sql = "show partitions " + tableName;
		log.info(sql);
		List<String> partitions = template.query(sql, (RowMapper<String>) (rs, rowNum) -> {
			return rs.getString("date");
		});
		for (int i = 0; i < partitions.size(); i++) {
			if (partitions.get(i).equals("Total")) {
				partitions.remove(i);
				break;
			}
		}
		return partitions;
	}

	/**
	 * @see com.hirain.phm.bd.data.impala.dao.DataMapper#delete(java.lang.String, java.lang.String)
	 */
	@Override
	public void delete(String tableName, String date) {
		String sql = "alter table " + tableName + " drop partition(`date`<= '" + date + "' )";
		System.out.println(sql);
		template.execute(sql);
	}
}
