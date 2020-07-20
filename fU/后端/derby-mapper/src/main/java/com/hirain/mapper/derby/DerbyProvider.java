/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.mapper.derby;

import org.apache.ibatis.mapping.MappedStatement;

import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;
import tk.mybatis.mapper.mapperhelper.SqlHelper;

/**
 * @Version 1.0
 * @Author jianwen.xin@hirain.com
 * @Created Dec 13, 2019 6:11:55 PM
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               Dec 13, 2019 jianwen.xin@hirain.com 1.0 create file
 */
public class DerbyProvider extends MapperTemplate {

	/**
	 * @param mapperClass
	 * @param mapperHelper
	 */
	public DerbyProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
		super(mapperClass, mapperHelper);
	}

	public String insertGenerateKey(MappedStatement ms) {
		final Class<?> entityClass = getEntityClass(ms);
		// 开始拼sql
		StringBuilder sql = new StringBuilder();
		sql.append(SqlHelper.insertIntoTable(entityClass, tableName(entityClass)));
		sql.append(SqlHelper.insertColumns(entityClass, true, false, false));
		sql.append(SynapsisSqlHelper.insertValuesColumns(entityClass, true, false, false));
		return sql.toString();
	}

	public String updateByKey(MappedStatement ms) {
		Class<?> entityClass = getEntityClass(ms);
		StringBuilder sql = new StringBuilder();
		sql.append(SqlHelper.updateTable(entityClass, tableName(entityClass)));
		sql.append(SynapsisSqlHelper.updateSetColumns(entityClass, null, false, false));
		sql.append(SqlHelper.wherePKColumns(entityClass, true));
		return sql.toString();
	}

	public String updateNotNullByKey(MappedStatement ms) {
		Class<?> entityClass = getEntityClass(ms);
		StringBuilder sql = new StringBuilder();
		sql.append(SqlHelper.updateTable(entityClass, tableName(entityClass)));
		sql.append(SynapsisSqlHelper.updateSetColumns(entityClass, null, true, false));
		sql.append(SqlHelper.wherePKColumns(entityClass, true));
		return sql.toString();
	}
}
